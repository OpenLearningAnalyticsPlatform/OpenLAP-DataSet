package OLAPDataSet;

/**
 * Created by lechip on 27/10/15.
 */
public class OLAPDataColumn <T> {
    private final OLAPColumnDataType type;
    private String id;
    private T [] data;
    private boolean required;

    public OLAPDataColumn(String id, OLAPColumnDataType type, boolean isRequired) {
        this.id = id;
        this.type = type;
        this.required = isRequired;
    }

    public OLAPDataColumn(OLAPColumnDataType type, String id) {
        this.type = type;
        this.id = id;
        this.required=false;
    }

    public boolean validate(OLAPDataColumn<?> inPortColumn){
        return inPortColumn.getType().equals(this.type) && !(inPortColumn.getId().isEmpty()) && inPortColumn.getId() != null;
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

    public boolean isRequired() {
        return required;
    }
}
