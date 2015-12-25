package DataSet;

import exceptions.OLAPDataColumnException;

import java.time.LocalDateTime;

/**
 * This Factory should be used to create the OLAPDataColumns on OLAPDataSets. IT holds a method that accepts
 * an ID, a type and the setting for required or not and returns a prepared OLAPDataColumn.
 */
public class OLAPDataColumnFactory {

    /**
     * Returns a OLAPDataColumn with the ID, Type and required parameter.
     * @param id Desired ID of the OLAPDataColumn to be created
     * @param type OLAPColumnDataType of the OLAPDataColumn to be created
     * @param isRequired The setting to make the OLAPDataColumn to be required or not
     * @return a new OLAPDataColumn initialized with the given ID, Type and requirement parameter and no data
     * @throws OLAPDataColumnException if the entered type is not recognized.
     */
    public static final OLAPDataColumn createOLAPDataColumnOfType(String id, OLAPColumnDataType type,
                                                                  boolean isRequired)
            throws OLAPDataColumnException {
        switch (type)
        {
            case BYTE:
                return new OLAPDataColumn<Byte>(id, OLAPColumnDataType.BYTE, isRequired);
            case SHORT:
                return new OLAPDataColumn<Short>(id, OLAPColumnDataType.SHORT, isRequired);
            case STRING:
                return new OLAPDataColumn<String>(id, OLAPColumnDataType.STRING, isRequired);
            case INTEGER:
                return new OLAPDataColumn<Integer>(id, OLAPColumnDataType.INTEGER, isRequired);
            case BOOLEAN:
                return new OLAPDataColumn<Boolean>(id, OLAPColumnDataType.BOOLEAN, isRequired);
            case LONG:
                return new OLAPDataColumn<Long>(id, OLAPColumnDataType.LONG, isRequired);
            case FLOAT:
                return new OLAPDataColumn<Float>(id, OLAPColumnDataType.FLOAT, isRequired);
            case LOCAL_DATE_TIME:
                return new OLAPDataColumn<LocalDateTime>(id, OLAPColumnDataType.LOCAL_DATE_TIME, isRequired);
            case CHAR:
                return new OLAPDataColumn<Character>(id, OLAPColumnDataType.CHAR, isRequired);
            default:
                throw new OLAPDataColumnException("Data type not supported");
        }
    }
}
