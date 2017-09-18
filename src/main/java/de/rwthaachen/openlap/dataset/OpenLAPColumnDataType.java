package de.rwthaachen.openlap.dataset;

/**
 * Types that can be used on OpenLAPColumnConfigData.
 * Ideally, they should correspond to DataBase primitive types
 */
//public enum OpenLAPColumnDataType {
//    BYTE,
//    SHORT,
//    STRING,
//    INTEGER,
//    BOOLEAN,
//    LONG,
//    FLOAT,
//    LOCAL_DATE_TIME,
//    CHAR
//}
public enum OpenLAPColumnDataType {
    Text,
    Numeric,
    TrueFalse
}