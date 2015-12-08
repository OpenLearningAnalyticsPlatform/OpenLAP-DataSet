package DataSet;

import exceptions.OLAPDataColumnException;

import java.time.LocalDateTime;

/**
 * Created by lechip on 27/10/15.
 */
public class OLAPDataColumnFactory {

    public static final OLAPDataColumn createOLAPDataColumnOfType(String id, OLAPColumnDataType type, boolean isRequired)
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
