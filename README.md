# Prepare
```
wget -P soot https://repo1.maven.org/maven2/org/soot-oss/soot/4.3.0/soot-4.3.0-jar-with-dependencies.jar
```
# soot learning



![pack](https://pic1.zhimg.com/v2-08dc066f2f1cea737c10f80f65069838_r.jpg)


# soot for shell
```shell
export SOOT_HOME = shell_soot/soot-4.3.0-jar-with-dependencies.jar
export SOOT_CLASS_PATH = src/test/java
# verify install
java -cp shell_soot/soot-4.3.0-jar-with-dependencies.jar  soot.Main  -h

# compile
javac src/test/java/com/wang/HelloWorld.java   

# use soot output .jimple
java -cp soot/soot-4.3.0-jar-with-dependencies.jar soot.Main -cp src/test/java -pp -f J com.wang.HelloWorld
```
- `-cp $SOOT_HOME` 指定soot的jar包目录
- `-cp $POJO_HOME` : 指定所要分析 `.class` 文件的目目录
- `-pp`: 指定soot去自动搜索java的path， 主要是rt.jar和jce.jar， soot会去$JAVA_HOME下寻找
- `-f J`: 指定输出文件类型， J就是jimple
- `com.wang.HelloWorld`: 你要分析的class的名字











> ### Reference
> https://github.com/soot-oss/soot/wiki/Introduction:-Soot-as-a-command-line-tool
> [soot 学习笔记](https://blog.csdn.net/beswkwangbo/category_2710855.html)