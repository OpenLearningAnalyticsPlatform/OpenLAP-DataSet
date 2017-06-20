# OpenLAP-DataSet


## FUNCTIONALITY AND INTERNALS

Since the OpenLAP-DataSet functions much like a typical DataBase table, the OpenLAP-DataSet is based on te concept of a
simple aggregation of data arrays with a common denominator (an ID). These data arrays can be marked as required or not,
and function much like a Data Base Column [MySQLRef](#references). The grouping of these "Columns" is the OpenLAPDataSet.

A naming scheme was developed in order to ease the usage of the OpenLAP-DataSet in code while aiming to maintain less
verbose class names. All of the classes of the OpenLAP-DataSet use the prefix `OpenLAP`.

The `OpenLAPDataSet` is a grouping of `OpenLAPDataColumns`. Each of these Columns has two distinctive sections: A "metadata"
section, for describing the Column `type`, `required` flag and its `id`. This section is encapsulated on a class called
`OpenLAPColumnConfigurationData`, since it has the data required for checking configurations, which will be explained
later.
The second section is the data itself, represented as an array of the specified type.
The `type` of the items contained in the data section requires to be the same `type` as the one specified on the
metadata section of the column. The diagram below describes an overview of an `OpenLAPDataSet`.

<table class="image">
<caption align="bottom">
Diagram describing the OpenLAPDataDet, note that it is similar to how a relational database table looks like, however,
the <code>OpenLAPDataColumn</code> has two sections: The <code>OpenLAPColumnConfigurationData</code> and the data itself.
</caption>
<tr><td><img src="https://github.com/OpenLearningAnalyticsPlatform/OpenLAP-Architecture/blob/master/OpenLAP-DataSet/OpenLAP-DataSet_DataSetConcept.png" alt="OpenLAP-DataSet_DataSetConcept.png"/></td></tr>
</table>

In order to achieve this, a Factory design pattern [FactoryRef](#references) in the form of the class `OpenLAPDataColumnFactory`.
The Factory enables the creation of `OpenLAPDataColumn` objects with a special type of enumerator parameter
(`OpenLAPColumnDataType`)so the column's type always corresponds to it's data type.
The `OpenLAPColumnDataType` supports the types of the primitives of a modern relational
database such as MySQL or MicrosoftSQL. `OpenLAPDataColumn` objects should only be created trough the factory so the type
correspondence is enforced.

A typical JSON representation of the `OpenLAPDataSet` of the previous figure is shown below:

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
order to map items from one `OpenLAPDataSet` to another, a class named `OpenLAPPortConfiguration` is provided.
This mapping represents the link between two `OpenLAPDataSet`, one denominated "output" since it outputs the
data from an OpenLAP macro component (is the source of the data) and a second denominated "input" since it represents
the consuming OpenLAP macro component of the data. Note that when making mappings, only the `required` fields of the
input `OpenLAPDataSet` are enforced to have an input mapping, that is, fields marked as required on the output DataSet
are not treated differently than those not required. The field is shown for completeness.
A representation of these concepts is shown in the figure.

<table class="image">
<caption align="bottom">
The <code>OpenLAPPortConfiguration</code> is a grouping of tuples, called <code>OpenLAPPortMapping</code>, between output to input <code>OpenLAPDataSet</code>.
Note that the <code>OpenLAPPortConfiguration</code> can be transmitted witouth the data. This configuration is validated by the input <code>OpenLAPDataSet</code>
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

Each `OpenLAPPortConfiguration` is an array of tuples (called `OpenLAPPortMapping` of the relevant metadata of columns
(specifically, tuples of `OpenLAPColumnConfigurationData`)
that represent the link between each of the output `OpenLAPDataSet` columns
to the input `OpenLAPDataSet` columns.
The `OpenLAPDataSet` also has a method to check whether a `OpenLAPPortConfiguration` is compatible with it or not. This method
realizes the need of the OpenLAP-DataSet to allow for dynamic checking by validating that the incoming configuration
is both type compatible and has all the required fields. I worth noting that since the `OpenLAPPortConfiguration` only has
the metadata section of a column (`OpenLAPColumnConfigurationData`) and it is relatively lightweight then
can be sent for validation between macro components before any bulk of data is transmitted.
The result of the validation of a `OpenLAPPortConfiguration` is stored on a special Data Transfer Object called
`OpenLAPDataSetConfigurationValidationResult` that contains a boolean flag with the result of the validation and a message
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

### Creating Columns using the OpenLAPDataColumnFactory

In order to create a `OpenLAPDataSet`, as explained before, the `OpenLAPDataColumns` must be declared with the help of
the `OpenLAPDataColumnFactory`.

```java
// Creating a OpenLAPDataSet
import DataSet.*;
OpenLAPDataSet dataSet1 = new OpenLAPDataSet();
dataSet1.addOpenLAPDataColumn(
                OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("intColumn1",OpenLAPColumnDataType.INTEGER,true));
        dataSet1.addOpenLAPDataColumn(
                OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("stringColumn1",OpenLAPColumnDataType.STRING,true));
        dataSet1.addOpenLAPDataColumn(
                OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("column1",OpenLAPColumnDataType.STRING,false));
```

### Validating Configurations

To validate whether a `OpenLAPPortConfiguration` from an output `OpenLAPDataSet` will be compatible with a given `OpenLAPDataSet`
is possible to use the `OpenLAPDataSet` method for it as described below.

```java
// Validating a OpenLAPPortConfiguration on a OpenLAPDataSet
OpenLAPDataSetConfigurationValidationResult configurationValidationResult1 =
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
