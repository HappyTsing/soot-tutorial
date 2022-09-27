package com.wang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soot.*;
import soot.options.Options;

import java.util.Map;

public class intraTransformer extends BodyTransformer {
    private static final Logger logger = LoggerFactory.getLogger(intraTransformer.class);

    @Override
    protected void internalTransform(Body body, String s, Map<String, String> map) {
        logger.info("This is intraTransformer, I print Method Name Here: {}",body.getMethod().getName()); //输出下程序方法的名字
    }
}