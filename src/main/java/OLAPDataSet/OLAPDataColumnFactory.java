package OLAPDataSet;

import java.time.LocalDateTime;

/**
 * Created by lechip on 27/10/15.
 */
public class OLAPDataColumnFactory {

    public static final OLAPDataColumn getOLAPDataColumnOfType(String id, OLAPColumnDataType type)
    {
        switch (type)
        {
            case BYTE:
                return new OLAPDataColumn<Byte>(id, OLAPColumnDataType.BYTE);
            case SHORT:
                return new OLAPDataColumn<Short>(id, OLAPColumnDataType.SHORT);
            case STRING:
                return new OLAPDataColumn<String>(id, OLAPColumnDataType.STRING);
            case INTEGER:
                return new OLAPDataColumn<Integer>(id, OLAPColumnDataType.INTEGER);
            case BOOLEAN:
                return new OLAPDataColumn<Boolean>(id, OLAPColumnDataType.BOOLEAN);
            case LONG:
                return new OLAPDataColumn<Long>(id, OLAPColumnDataType.LONG);
            case FLOAT:
                return new OLAPDataColumn<Float>(id, OLAPColumnDataType.FLOAT);
            case LOCAL_DATE_TIME:
                return new OLAPDataColumn<LocalDateTime>(id, OLAPColumnDataType.LOCAL_DATE_TIME);
            case CHAR:
                return new OLAPDataColumn<Character>(id, OLAPColumnDataType.CHAR);
            default:
                return null;
        }
    }
}
