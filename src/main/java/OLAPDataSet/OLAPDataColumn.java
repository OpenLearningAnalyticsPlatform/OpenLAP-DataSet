package OLAPDataSet;

/**
 * Created by lechip on 27/10/15.
 */
public class OLAPDataColumn <T> {
    private final OLAPColumnDataType type;
    private String id;
    private T [] data;

    public OLAPDataColumn(String id, OLAPColumnDataType type) {
        this.id = id;
        this.type = type;
    }

    boolean validate(OLAPDataColumn<?> inPortColumn){
        // TODO
        return false;
    }


    public void setData(T[] data) {
        this.data = data;
    }

    public OLAPColumnDataType getType() {
        return type;
    }

    public T[] getData() {
        return data;
    }

    public String getId() {
        return id;
    }
}
