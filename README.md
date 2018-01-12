## mumu-morphlines 数据转化工具

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/mumuhadoop/mumu-morphlines/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/mumuhadoop/mumu-morphlines.svg?branch=master)](https://travis-ci.org/mumuhadoop/mumu-morphlines)
[![codecov](https://codecov.io/gh/mumuhadoop/mumu-morphlines/branch/master/graph/badge.svg)](https://codecov.io/gh/mumuhadoop/mumu-morphlines)

mumu-morphlines是一个kite morphlines测试程序，主要通过这个项目来了解和学习kite morphlines的使用方式和工作原理。morphlines是一款数据转换工具集，可以通过morphlines
来抽取、转换、加载(ETL)数据，列如可以抽取日志数据。同时morphlines可以配合flume、hadoop、solr来将非结构化的数据转换为结构化的数据，并且将数据保存在solr中供客户端进
行检索使用。

## kite Morphline
Kite Morphline是一个Morphline版本，将Morphline应用到除Search外的数据处理中，发布了丰富的库、工具、样例、文档。
Kite Morphline支持
- Flumeevents,
- HDFSfiles,
- SparkRDDs,
- RDBMStables
- Avroobjects

已经应用到Crunch、HBase、Impala、Pig、Hive、Sqoop等

### Morphline重要概念

- Commands are plugins to a morphline that perform tasks such as loading, parsing, transforming, or otherwise processing a single record.

- Record is an in-memory data structure of name-value pairs (Record)with optional blob attachments or POJO attachments.

### morphine工作流程

![morphine工作流程](https://raw.githubusercontent.com/mumuhadoop/mumu-morphlines/master/doc/images/morphine_process.jpg)

### morphine在hadoop家族地位

![morphine在hadoop家族地位](https://raw.githubusercontent.com/mumuhadoop/mumu-morphlines/master/doc/images/morphine_architecture.jpg)

## Morphline案列

### Morphline配置文件
```
morphlines: [
  {
    id: morphline1
    importCommands: ["org.kitesdk.**", "org.apache.solr.**"]
    commands: [
      {
        readLine {
          charset: UTF-8
        }
      }
      {
        grok {
          dictionaryFiles: [src/test/resources/grok-dictionaries]
          expressions: {
            message: """<%{POSINT:priority}>%{SYSLOGTIMESTAMP:timestamp} %{SYSLOGHOST:hostname} %{DATA:program}(?:\[%{POSINT:pid}\])?: %{GREEDYDATA:msg}"""
          }
        }
      }
      {
        convertTimestamp {
          field: timestamp
          inputFormats: ["yyyy-MM-dd'T'HH:mm:ss'Z'", "MMM d HH:mm:ss"]
          inputTimezone: America/Los_Angeles
          outputFormat: "yyyy-MM-dd HH:mm:ss"
          outputTimezone: UTC
        }
      }
      {logInfo {format: "output record: {}", args: ["@{}"]}}
    ]
  }
]
```

### Morphline测试代码
```
@Test
public void sysLogTest() {
     MorphlineContext context = new MorphlineContext.Builder().build();
     File configFile = new File(BasicMorphlineTest.class.getResource("/morphlines/syslog.conf").getPath());
     Command morphline = new Compiler().compile(configFile, null, context, null);
     Record record = new Record();
     record.put(Fields.ATTACHMENT_BODY, BasicMorphlineTest.class.getResourceAsStream("/log/syslog.log"));
     boolean process = morphline.process(record);
     System.out.println(process);
}
```

## 相关阅读

[hadoop官网文档](http://hadoop.apache.org)

[kite：A Data API for Hadoop](http://kitesdk.org/docs/current/)

[Morphlines Introduction](http://kitesdk.org/docs/1.1.0/morphlines/)

## 联系方式

以上观点纯属个人看法，如有不同，欢迎指正。

email:<babymm@aliyun.com>

github:[https://github.com/babymm](https://github.com/babymm)