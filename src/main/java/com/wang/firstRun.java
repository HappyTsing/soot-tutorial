package com.wang;

/**
 * 模仿shell执行soot。
 * 值得注意的是：
 *            1. sootArgs中，如果shell命令中，options中是在整个的，比如 -cp classpath，则在sootArgs中也需要在同一行；
 *                           如果是不同的options，则需要换行，比如 -pp 和 -allow-phantom-refs需要换行
 *            2. windows中使用正斜杠\ 以及 分号;
 *               linux中使用反斜杠/ 以及 冒号:
 */
public class firstRun{
    // for linux should be "src/test/java
    static String ClassPath = "src\\test\\java";
    static String PocessDir = "src\\test\\java";

    public static void main(String[] args) {
//         run_files();
        run_dir();
    }

    public static void run_files(){
        String[] sootArgs = {
                /**
                 * Input Options
                 * @-cp: 设置soot的classpath
                 * @-pp: 自动从 JAVA_HOME 中寻找java默认依赖
                 * @-allow-phantom-refs: 忽略找不到的依赖
                 * @-no-bodies-for-excluded: 不为excluded的类生成body
                */
                "-cp", ClassPath,
                "-pp",
                "-allow-phantom-refs",
                "-no-bodies-for-excluded",

                /**
                 * Output Options
                 * @-f J: 设置输出格式为Jimple
                */
                "-f", "J",
                "com.wang.Foo",
                "com.wang.HelloWorld"
        };
        soot.Main.main(sootArgs);
    }

    public static void run_dir(){
        String[] sootArgs = {
                "-cp", ClassPath,
                "-pp",
                "-allow-phantom-refs",
                "-no-bodies-for-excluded",
                /**
                 * @-process-dir: 处理目录下的所有文件
                */
                "-f", "J",
                "-process-dir",PocessDir
        };
        soot.Main.main(sootArgs);
    }
}