# OpenLAP-DataSet
A modular Serializable DataSet to transport data between macro components in the OLAP project.
The DataSet is described in more detail and in te context of the Open Learning Analytics Platform in the
[OpenLAP Architecture](https://github.com/OpenLearningAnalyticsPlatform/OpenLAP-Architecture/wiki/4.5.1.-OLAP-Analytics-Methods:-Module-Views#package-olapdataset).

#IMPORTING INTO A PROJECT

**Step 1.** Add the JitPack repository to your build file:

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

**Step 2.**  Add the dependency

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
