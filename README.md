# OLAPDataSet
A modular Serializable DataSet to transport data between macro components in the OLAP project

#IMPORTING INTO A PROJECT

**Setp 1.** Add the JitPack repository to your build file:

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

**Setp 2.**  Add the dependency

Maven:
```xml
<dependency>
    <groupId>com.github.OpenLearningAnalyticsPlatform</groupId>
    <artifactId>OLAPDataSet</artifactId>
    <version>v1.0</version>
</dependency>
```
Gradle:
```gradle
dependencies {
        compile 'com.github.OpenLearningAnalyticsPlatform:OLAPDataSet:v1.0'
}
```
