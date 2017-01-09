package de.rwthaachen.openlap.dataset;

import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;

import java.time.LocalDateTime;

/**
 * This Factory should be used to create the OLAPDataColumns on OLAPDataSets. IT holds a method that accepts
 * an ID, a type and the setting for required or not and returns a prepared OpenLAPDataColumn.
 */
public class OpenLAPDataColumnFactory {

    /**
     * Returns a OpenLAPDataColumn with the ID, Type and required parameter.
     * @param id Desired ID of the OpenLAPDataColumn to be created
     * @param type OpenLAPColumnDataType of the OpenLAPDataColumn to be created
     * @param isRequired The setting to make the OpenLAPDataColumn to be required or not
     * @return a new OpenLAPDataColumn initialized with the given ID, Type and requirement parameter and no data
     * @throws OpenLAPDataColumnException if the entered type is not recognized.
     */
    public static final OpenLAPDataColumn createOLAPDataColumnOfType(String id, OpenLAPColumnDataType type,
                                                                     boolean isRequired)
            throws OpenLAPDataColumnException {
        switch (type)
        {
            case BYTE:
                return new OpenLAPDataColumn<Byte>(id, OpenLAPColumnDataType.BYTE, isRequired);
            case SHORT:
                return new OpenLAPDataColumn<Short>(id, OpenLAPColumnDataType.SHORT, isRequired);
            case STRING:
                return new OpenLAPDataColumn<String>(id, OpenLAPColumnDataType.STRING, isRequired);
            case INTEGER:
                return new OpenLAPDataColumn<Integer>(id, OpenLAPColumnDataType.INTEGER, isRequired);
            case BOOLEAN:
                return new OpenLAPDataColumn<Boolean>(id, OpenLAPColumnDataType.BOOLEAN, isRequired);
            case LONG:
                return new OpenLAPDataColumn<Long>(id, OpenLAPColumnDataType.LONG, isRequired);
            case FLOAT:
                return new OpenLAPDataColumn<Float>(id, OpenLAPColumnDataType.FLOAT, isRequired);
            case LOCAL_DATE_TIME:
                return new OpenLAPDataColumn<LocalDateTime>(id, OpenLAPColumnDataType.LOCAL_DATE_TIME, isRequired);
            case CHAR:
                return new OpenLAPDataColumn<Character>(id, OpenLAPColumnDataType.CHAR, isRequired);
            default:
                throw new OpenLAPDataColumnException("Data type not supported");
        }
    }

    public static final OpenLAPDataColumn createOLAPDataColumnOfType(String id, OpenLAPColumnDataType type,
                                                                     boolean isRequired, String title, String description)
            throws OpenLAPDataColumnException {
        switch (type)
        {
            case BYTE:
                return new OpenLAPDataColumn<Byte>(id, OpenLAPColumnDataType.BYTE, isRequired, title, description);
            case SHORT:
                return new OpenLAPDataColumn<Short>(id, OpenLAPColumnDataType.SHORT, isRequired, title, description);
            case STRING:
                return new OpenLAPDataColumn<String>(id, OpenLAPColumnDataType.STRING, isRequired, title, description);
            case INTEGER:
                return new OpenLAPDataColumn<Integer>(id, OpenLAPColumnDataType.INTEGER, isRequired, title, description);
            case BOOLEAN:
                return new OpenLAPDataColumn<Boolean>(id, OpenLAPColumnDataType.BOOLEAN, isRequired, title, description);
            case LONG:
                return new OpenLAPDataColumn<Long>(id, OpenLAPColumnDataType.LONG, isRequired, title, description);
            case FLOAT:
                return new OpenLAPDataColumn<Float>(id, OpenLAPColumnDataType.FLOAT, isRequired, title, description);
            case LOCAL_DATE_TIME:
                return new OpenLAPDataColumn<LocalDateTime>(id, OpenLAPColumnDataType.LOCAL_DATE_TIME, isRequired, title, description);
            case CHAR:
                return new OpenLAPDataColumn<Character>(id, OpenLAPColumnDataType.CHAR, isRequired, title, description);
            default:
                throw new OpenLAPDataColumnException("Data type not supported");
        }
    }
}
