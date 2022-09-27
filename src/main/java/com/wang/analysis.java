package com.wang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soot.*;
import soot.jimple.*;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.Sources;
import soot.options.Options;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;

import java.util.Iterator;
import java.util.List;

public class analysis {
    private static final Logger logger = LoggerFactory.getLogger(intraTransformer.class);

    public static void main(String[] args) {
        PackManager.v().getPack("jtp").add(new Transform("jtp.leki_intra",new intraTransformer()));
//        PackManager.v().getPack("wjtp").add(new Transform("wjtp.leki_inter",new interTransformer(new DataFlowAnalysis(g))));
        String ClassPath = "src\\test\\java";

        /**
         * 设置Options的两种方式：
         *      1. Options.v().setxxx()
         *      2. 直接传入
         */
        Options.v().set_no_writeout_body_releasing(true);
        String[] sootArgs = {
                /**
                 * 开启 whole program mode ，进行过程间分析
                */
                "-w",

                /**
                 * Input Options
                 * @-cp: 设置soot的classpath
                 * @-pp: 自动从 JAVA_HOME 中寻找java默认依赖
                 * @-allow-phantom-refs: 忽略找不到的依赖
                 * @-no-bodies-for-excluded: 不为excluded的类生成body
                 * @-no-writeout-body-releaseing: 不释放body?如果无法找到body，就加上吧
                 * @-keep-line-number: 保留 IR 和 source code 的位置对应关系
                */
                "-cp", ClassPath,
                "-pp",
//                "-allow-phantom-refs",
//                "-no-bodies-for-excluded",
                "-no-writeout-body-releasing",
                "-keep-line-number",

                /**
                 * Output Options
                 * @-f J: 设置输出格式为Jimple
                */
                "-f",
                "J",
                "com.wang.Foo",
                "com.wang.HelloWorld",

                /**
                 * 使用 spark 进行指针分析，加强过程间分析的精度，默认使用 CHA 生成CG，速度快，但精度不高
                */
                "-p", "cg.spark", "on"
        };

        /**
         * 执行 soot，此时会根据给定的参数运行。
         * 同时本例中，在 phase jtp 中添加了我们自己的 subphase jtp.leki_subphase，该子阶段也会执行。
         */
        soot.Main.main(sootArgs);

        /**
         * 通过单例的环境变量 Scene，得到分析的所有 Class。
         */
        Chain<SootClass> classes = Scene.v().getApplicationClasses();

        /**
         * 使用 Option -w 开启了 whole program mode 的情况下，可以通过 Scene 获取 CallGraph，即（方法）调用关系图。
         */
        CallGraph cg = Scene.v().getCallGraph();

        for(SootClass cl:classes){

            /**
             * 通过 Class，可以获取：字段Filed、方法Method、Class 的父类superClass 和 继承的接口Interface
             */
            cl.getFields();
            cl.getSuperclass();
            cl.getInterfaces();
            List<SootMethod> methods = cl.getMethods();
            logger.info("Class Name: {}",cl.getName());
            for(SootMethod m:methods) {
                /**
                 * 通过 Method，可以获取：所属类、方法签名等。
                 */
                m.getDeclaringClass();
                m.getSignature();

                logger.info("Method Nmae: {}", m.getName());

                /**
                 * Call Graph 提供了三种方法，如 edgesOutof(method) 查询来自于该方法的edge，此外还有 edgesInto(method)、edgesOutof(statement)
                 */
                Iterator<Edge> edges = cg.edgesOutOf(m);

                /**
                 * Call Graph 中每条边 edge 都包含四个元素：
                 *                                      - source method
                 *                                      - source statement (if applicable)
                 *                                      - target method
                 *                                      - the kind of edge
                 * 因此，提供了遍历 edge 中特定元素的迭代器，如 Sources 用于遍历 edge 中的 source method 元素。此外还有 Units、Targets
                 */
                Iterator ItSources = new Sources(edges);
                while (ItSources.hasNext()) {
                    SootMethod src = (SootMethod) ItSources.next();
                    logger.info("{} might be called by {}",m,src);
                }

                /**
                 * 通过 Method 生成方法体 Body，这是 Soot 最有趣的地方！
                 * 通过 Body 可以获取该方法的局部变量 local、语句 unit 等
                 */

                Body body = m.retrieveActiveBody();
                body.getLocals();

                /**
                 * 为单个方法体 Body 生成 CFG (Control Flow Graphs)，即控制流图。
                 * Soot 提供了最常用的 CFG 实现，ExceptionalUnitGraph，其继承了抽象类 UnitGraph，该抽象类是基于接口 DirectedGraph<N>
                 */
                UnitGraph cfg = new ExceptionalUnitGraph(body);

                /**
                 * 获取并遍历 body 中的所有语句，以 Jimple 为例，其对 Unit 的实现是 Stmt。即 public interface Stmt extends Unit
                 *
                 */
                for (Unit unit : body.getUnits()) {
                    Stmt stmt = (Stmt)unit;


                    /**
                     * 定义了 CFG 的 DirectedGraph<N> 接口，提供了若干个 getter 方法，如 getPredsOf(Unit) 用于获取 unit 的 predecessors，即前继结点。
                     * 此外，还可以获取：
                     *              1. entry and exit
                     *              2. successors and predecessors
                     *              3. an iterator to iterate over the graph in some undefined orde and the graphs size (number of nodes).
                     */
                    List<Unit> predecessors = cfg.getPredsOf(stmt);
                    logger.info("{} are the predecessors of {}",predecessors,stmt);

                    /**
                     * stmt 的一些示例用法
                     */
                    if(stmt.containsInvokeExpr()){
                        InvokeExpr expr = stmt.getInvokeExpr();
                        logger.info("InvokeExpr Args {}",stmt.getInvokeExpr().getArgs());
                    }
                    // Returns the Java source line number if available. Returns -1 if not.
                    logger.info("Location of source is {}",stmt.getJavaSourceStartLineNumber());

                    /**
                     * 对 AddExpr进行优化操作
                     */
                    for (ValueBox vb : stmt.getUseBoxes()) {
                        Value v = vb.getValue();
                        if(v instanceof AddExpr){
                            AddExpr ae = (AddExpr) v;
                            Value lo = ae.getOp1(), ro = ae.getOp2();
                            if (lo instanceof IntConstant && ro instanceof IntConstant) {
                                IntConstant l = (IntConstant) lo,
                                        r = (IntConstant) ro;
                                int sum = l.value + r.value;
                                vb.setValue(IntConstant.v(sum));
                            }
                        }
                    }
                }
            }
        }
    }
}
