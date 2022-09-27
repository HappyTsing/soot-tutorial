package com.wang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soot.SceneTransformer;

import java.util.*;

/**
 * 注：数据流分析代码仅是模板，并未具体实现，因此无法运行！
 */
public class interTransformer extends SceneTransformer{

    private static final Logger logger = LoggerFactory.getLogger(interTransformer.class);

    private final DataFlowAnalysis analysis;

    public interTransformer(DataFlowAnalysis analysis) {
        this.analysis = analysis;
    }

    @Override
    protected void internalTransform(String s, Map<String, String> map) {
        analysis.doAnalysis();
        logger.info("This is internalTransform"); //输出下程序方法的名字
    }
}