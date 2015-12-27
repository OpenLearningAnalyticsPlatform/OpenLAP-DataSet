# OpenLAP-DataSet

## INTRODUCTION
A modular JSON Serializable DataSet to serialize and validate data between macro components in the OpenLAP project.
The OpenLAP-DataSet is a Java module that is to be used by the Analytics Methods, Analytics Modules, Visualizer,
Analytics Engine and Web Client macro components of the OpenLAP.

## MOTIVATION

Since the macro components of the OpenLAP reside potentially in different physical hardware, and they act with relative
independence from each other, a proper Data transfer structure needed to be developed. The structure needs to be simple
enough to be easily serializable to and from JSON since it needs to be human-readable form but enable automatic parsing.
This JSON approach also enables and future proofs the structure in case the Data Base system of the OpenLAP migrates
to noSQL JSON document based Data Base systems.
The data structure also needs to make possible to send partial structures or metadata chunks to both validate types and
configurations during runtime before sending the data payload in order to provide a performance gain.

The OpenLAP-DataSet answer the need of the OpenLAP for a mechanism that can provide the following functionalities:

1. Provide Java compatible data type checking.
2. Provide a structure that could be easily serializable to and from JSON in a way that the data and metadata can be
  human readable and ease the usage and delivering of datha to and from the Web Client.
3. Provide mechanisms for supporting eventual migration to noSQL standards for databases.
4. Ease the serialization and enable partial transfer, either by chunks of data or by transmitting only header and
 validation information.


## FUNCTIONALITY AND INTERNALS

Since the OpenLAP-DataSet functions much like a typical DataBase table, the OpenLAP-DataSet is based on te concept of a
simple aggregation of data arrays with a common denominator (an ID). These data arrays can be marked as required or not,
and function much like a Data Base Column [MySQLRef](#references). The grouping of these "Columns" is the OLAPDataSet. 

A naming scheme was developed in order to ease the usage of the OpenLAP-DataSet in code while aiming to maintain less
verbose class names. All of the classes of the OpenLAP-DataSet use the prefix `OLAP`.

The `OLAPDataSet` is a grouping of `OLAPDataColumns`. Each of these Columns has two distinctive sections: A "metadata"
section, for describing the Column `type`, `required` flag and its `id`. This section is encapsulated on a class called
`OLAPColumnConfigurationData`, since it has the data required for checking configurations, which will be explained
later.
The second section is the data itself, represented as an array of the specified type.
The `type` of the items contained in the data section requires to be the same `type` as the one specified on the
metadata section of the column. The diagram below describes an overview of an `OLAPDataSet`.

<table class="image">
<caption align="bottom">
Diagram describing the OLAPDataDet, note that it is similar to how a relational database table looks like, however,
the <code>OLAPDataColumn</code> has two sections: The <code>OLAPColumnConfigurationData</code> and the data itself.
</caption>
<tr><td><img src="https://github.com/OpenLearningAnalyticsPlatform/OpenLAP-Architecture/blob/master/OpenLAP-DataSet/OpenLAP-DataSet_DataSetConcept.png" alt="OpenLAP-DataSet_DataSetConcept.png"/></td></tr>
</table>

In order to achieve this, a Factory design pattern [FactoryRef](#references) in the form of the class `OLAPDataColumnFactory`.
The Factory enables the creation of `OLAPDataColumn` objects with a special type of enumerator parameter
(`OLAPColumnDataType`)so the column's type always corresponds to it's data type.
The `OLAPColumnDataType` supports the types of the primitives of a modern relational
database such as MySQL or MicrosoftSQL. `OLAPDataColumn` objects should only be created trough the factory so the type
correspondence is enforced.

A typical JSON representation of the `OLAPDataSet` of the previous figure is shown below:

```json
{
  "columns" : {
    "column1" : {
      "configurationData" : {
        "type" : "STRING",
        "id" : "column1",
        "required" : false
      },
      "data" : [ "data1" ]
    },
    "intColumn1" : {
      "configurationData" : {
        "type" : "INTEGER",
        "id" : "intColumn1",
        "required" : true
      },
      "data" : [ 1, 2, 3, 4 ]
    },
    "stringColumn1" : {
      "configurationData" : {
        "type" : "STRING",
        "id" : "stringColumn1",
        "required" : true
      },
      "data" : [ "value1", "value2" ]
    }
  }
}
```

In order to provide the support for checking dynamically types, required fields and the presence of all the fields in
order to map items from one `OLAPDataSet` to another, a class named `OLAPPortConfiguration` is provided.
This mapping represents the link between two `OLAPDataSet`, one denominated "output" since it outputs the 
data from an OpenLAP macro component (is the source of the data) and a second denominated "input" since it represents
the consuming OpenLAP macro component of the data. Note that when making mappings, only the `required` fields of the
input `OLAPDataSet` are enforced to have an input mapping, that is, fields marked as required on the output DataSet
are not treated differently than those not required. The field is shown for completeness.
A representation of these concepts is shown in the figure.

<table class="image">
<caption align="bottom">
The <code>OLAPPortConfiguration</code> is a grouping of tuples, called <code>OLAPPortMapping</code>, between output to input <code>OLAPDataSet</code>.
Note that the <code>OLAPPortConfiguration</code> can be transmitted witouth the data. This configuration is validated by the input <code>OLAPDataSet</code>
</caption>
<tr><td><img src="https://github.com/OpenLearningAnalyticsPlatform/OpenLAP-Architecture/blob/master/OpenLAP-DataSet/OpenLAP-DataSet_Configuration.png" alt="OpenLAP-DataSet_Configuration.png"/></td></tr>
</table>

The mapping of the figure has the following JSON representation:

```json
{
  "mapping" : [ {
    "outputPort" : {
      "type" : "INTEGER",
      "id" : "outCol1",
      "required" : false
    },
    "inputPort" : {
      "type" : "INTEGER",
      "id" : "inCol3",
      "required" : true
    }
  }, {
    "outputPort" : {
      "type" : "STRING",
      "id" : "outCol2",
      "required" : true
    },
    "inputPort" : {
      "type" : "STRING",
      "id" : "inCol2",
      "required" : true
    }
  }, {
    "outputPort" : {
      "type" : "STRING",
      "id" : "outCol3",
      "required" : true
    },
    "inputPort" : {
      "type" : "STRING",
      "id" : "inCol1",
      "required" : false
    }
  } ]
}
```

Each `OLAPPortConfiguration` is an array of tuples (called `OLAPPortMapping` of the relevant metadata of columns
(specifically, tuples of `OLAPColumnConfigurationData`)
that represent the link between each of the output `OLAPDataSet` columns
to the input `OLAPDataSet` columns.
The `OLAPDataSet` also has a method to check whether a `OLAPPortConfiguration` is compatible with it or not. This method
realizes the need of the OpenLAP-DataSet to allow for dynamic checking by validating that the incoming configuration
is both type compatible and has all the required fields. I worth noting that since the `OLAPPortConfiguration` only has
the metadata section of a column (`OLAPColumnConfigurationData`) and it is relatively lightweight then 
can be sent for validation between macro components before any bulk of data is transmitted.
The result of the validation of a `OLAPPortConfiguration` is stored on a special Data Transfer Object called 
`OLAPDataSetConfigurationValidationResult` that contains a boolean flag with the result of the validation and a message
field that contains a confirmation if the validation is successful or, if not, further information on the specific
invalid fields.


## USAGE

### Importing into a project

**Step 1.** The JitPack repository must be added to the build file:

Maven:
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
Gradle:
```gradle
repositories {
    maven {
        url "https://jitpack.io"
        credentials { username authToken }
    }
}
```

**Step 2.**  The deoendency must be added:

Maven:
```xml
<dependency>
    <groupId>com.github.OpenLearningAnalyticsPlatform</groupId>
    <artifactId>OpenLAP-DataSet</artifactId>
    <version>-SNAPSHOT</version>
</dependency>
```
Gradle:
```gradle
dependencies {
	        compile 'com.github.OpenLearningAnalyticsPlatform:OpenLAP-DataSet:-SNAPSHOT'
}
```
### Using the OpenLAP-DataSet

### Creating Columns using the OLAPDataColumnFactory

In order to create a `OLAPDataSet`, as explained before, the `OLAPDataColumns` must be declared with the help of 
the `OLAPDataColumnFactory`.

```java
// Creating a OLAPDataSet
import DataSet.*;
OLAPDataSet dataSet1 = new OLAPDataSet();
dataSet1.addOLAPDataColumn(
                OLAPDataColumnFactory.createOLAPDataColumnOfType("intColumn1",OLAPColumnDataType.INTEGER,true));
        dataSet1.addOLAPDataColumn(
                OLAPDataColumnFactory.createOLAPDataColumnOfType("stringColumn1",OLAPColumnDataType.STRING,true));
        dataSet1.addOLAPDataColumn(
                OLAPDataColumnFactory.createOLAPDataColumnOfType("column1",OLAPColumnDataType.STRING,false));
```

### Validating Configurations

To validate whether a `OLAPPortConfiguration` from an output `OLAPDataSet` will be compatible with a given `OLAPDataSet`
is possible to use the `OLAPDataSet` method for it as described below.

```java
// Validating a OLAPPortConfiguration on a OLAPDataSet
OLAPDataSetConfigurationValidationResult configurationValidationResult1 =
                dataSet1.validateConfiguration(configuration1);
// Will print "Message: Valid configuration"
System.out.println("Message: " + configurationValidationResult1.getValidationMessage());
// Will pring "Validation status: true"
System.out.println("Validation status: " + dataSet1.validateConfiguration(configuration1).isValid());
```

### JSON representation

The relevant classes of the OpenLAP-DataSet can use the built-in `toString()` method to obtain a JSON representation.
The OpenLAP uses [jackson](#references) as it's main JSON serializer/deserializer.
It is also possible to use other JSON serializer/deserializers.

## REFERENCES
* [MySQLRef]: "MySQL. (2015, December 24). MySQL 5.7 Reference Manual.
Retrieved from http://dev.mysql.com/doc/refman/5.7/en/data-types.html"
* [FactoryRef]: Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994).
Design patterns: elements of reusable object-oriented software. Pearson Education.
* [jackson]: "Jackson. (2009). Jackson Project Home @github.
Retrieved from https://github.com/FasterXML/jackson"