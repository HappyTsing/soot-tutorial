package com.wang;

import soot.Unit;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.BackwardFlowAnalysis;
import soot.toolkits.scalar.FlowSet;

/**
 * 数据流分析
 */
public class DataFlowAnalysis extends BackwardFlowAnalysis<Object,Object> {

    public DataFlowAnalysis(DirectedGraph g){
        super(g);
    }
    public void doAnalysis(){
        doAnalysis();
    }

    @Override
    protected void flowThrough(Object in, Object node, Object out) {
        FlowSet inSet = (FlowSet)in,
                outSet = (FlowSet)in;
        Unit u = (Unit)node;
        kill(inSet, u, outSet);
        gen(outSet, u);
    }
    private void kill(FlowSet inSet, Unit u, FlowSet outSet){ /* 用户自定义实现 */ }
    private void gen(FlowSet outSet, Unit u) { /* 用户自定义实现 */ }

    @Override
    protected Object newInitialFlow() {
        return new ArraySparseSet();
    }

    @Override
    protected void merge(Object in1, Object in2, Object out) {
        FlowSet inSet1 = (FlowSet)in1,
                inSet2 = (FlowSet)in2,
                outSet = (FlowSet)out;
        inSet1.intersection(inSet2, outSet);
    }

    @Override
    protected void copy(Object source, Object dest) {
        FlowSet srcSet = (FlowSet)source,
                destSet = (FlowSet)dest;
        srcSet.copy(destSet);
    }
}
