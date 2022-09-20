package com.wang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soot.*;
import soot.options.Options;

import java.util.Map;

public class intraTransformer {
    private static final Logger logger = LoggerFactory.getLogger(intraTransformer.class);

    public static void main(String[] args) {
        String[] sootArgs = {
                // Input Options
                "-cp", "C:\\Users\\59376\\IdeaProjects\\TestSoot\\src\\test\\java",
                "-pp",
//                "-allow-phantom-refs",
//                "-no-bodies-for-excluded",

                // Output Options
                "-f", "J",
                "com.wang.Foo"
//                "-process-dir","C:\\Users\\59376\\IdeaProjects\\TestSoot\\src\\test\\java"
        };
        soot.Main.main(sootArgs);
    }
}