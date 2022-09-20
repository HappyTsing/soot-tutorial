package com.wang;

import soot.PackManager;
import soot.Scene;
import soot.SceneTransformer;
import soot.Transform;

import java.util.Map;

public class interTransformer{
    public static void main(String[] args) {


        String[] sootArgs = {
                // Input Options
                "-cp", "C:\\Users\\59376\\IdeaProjects\\TestSoot\\src\\test\\java\\com\\wang",
                "-pp",
//                "-allow-phantom-refs",
//                "-no-bodies-for-excluded",

                // Output Options
                "-f", "J",
                "-process-dir","C:\\Users\\59376\\IdeaProjects\\TestSoot\\src\\test\\java\\com\\wang"
        };
        soot.Main.main(sootArgs);
    }
}