# Description

本项目用于记录学习soot的诸多疑惑，亦为后来者提供一定的代码模板。

# Usage

## Shell
1. Prepare

```shell
wget -P soot https://repo1.maven.org/maven2/org/soot-oss/soot/4.3.0/soot-4.3.0-jar-with-dependencies.jar
javac [TestClass]
```

Java 8 需要自己编译类，才能运行 soot
2. Run

```shell
java [javaOptions] soot.Main [sootOptions] classname
java [javaOptions] soot.Main [sootOptions] -process-dir dirname
```
- `javaOptions`：java 可以接收的选项，例如通过 `-cp $SOOT_HOME` 来指定 soot 的 jar 包目录.
- `sootOptions`：soot 可以接收的选项，通过 `java [javaOptions] soot.Main -h` 获取选项列表
- `classname`：soot 要分析的类，亦可以通过 -process-dir 分析指定目录下所有类

## Java API

- `firstRun.java`：使用java来模拟上述的shell执行
- `analysis,java`：从scene到unit的soot分析方法调用
- `DataFlowAnalysis`：数据流分析
- `.*Transformer`：用于向soot执行的指定phase添加一个subphase

> ### Reference
> - [Soot Options](https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/options/soot_options.htm)
