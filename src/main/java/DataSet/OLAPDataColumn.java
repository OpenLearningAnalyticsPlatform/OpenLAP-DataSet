package DataSet;

import java.util.ArrayList;

/**
 * This class is a grouping of a OLAPColumnConfigurationData and the data that the OLAPColumnConfigurationData
 * describes. In conjunction, it is denominated a OLAPDataColumn.
 */
public class OLAPDataColumn <T> {

    private final OLAPColumnConfigurationData configurationData;
    private ArrayList<T> data;

    /**
     * Standard constructor, sets fields to null.
     */
    public OLAPDataColumn() {
        this.configurationData = null;
    }

    /**
     * Constructor that sets the OLAPColumnConfigurationData with the ID, type and required properties
     * @param id ID of the OLAPDataColumn
     * @param type OLAPColumnDataType of the data of the OLAPDataColumn
     * @param isRequired Specifies if the OLAPDataColumn is required or not
     */
    public OLAPDataColumn(String id, OLAPColumnDataType type, boolean isRequired) {
        this.configurationData = new OLAPColumnConfigurationData(id, type, isRequired);
        this.data = new ArrayList<T>();
    }

    /**
     * Default constructor that sets the column as a non required OLAPDataColumn
     * @param id ID of the OLAPDataColumn
     * @param type OLAPColumnDataType of the data of the OLAPDataColumn
     */
    public OLAPDataColumn(OLAPColumnDataType type, String id) {
        this.configurationData = new OLAPColumnConfigurationData(id, type, false);
    }

    /**
     * Constructor that sets the OLAPColumnConfigurationData with the ID, type and required properties
     * @param id ID of the OLAPDataColumn
     * @param type OLAPColumnDataType of the data of the OLAPDataColumn
     * @param isRequired Specifies if the OLAPDataColumn is required or not
     */
    public OLAPDataColumn(String id, OLAPColumnDataType type, boolean isRequired, String title, String description) {
        this.configurationData = new OLAPColumnConfigurationData(id, type, isRequired, title, description);
        this.data = new ArrayList<T>();
    }

    /**
     * Validates the correspondence of type and ID of another OLAPColumnConfigurationData
     * @param olapColumnConfigurationData OLAPColumnConfigurationData to be compared with
     * @return true if the compared OLAPColumnConfigurationData Type and ID corresponds to the current
     */
    public boolean validateConfigurationData(OLAPColumnConfigurationData olapColumnConfigurationData){
        return this.getConfigurationData().validateConfigurationDataCorrespondence(olapColumnConfigurationData);
    }

    /**
     * @return Get the data of the OLAPDataColumn
     */
    public ArrayList<T> getData() {
        return data;
    }

    /**
     * @param data to be set on this OLAPDataColumn
     */
    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    /**
     * @return The OLAPColumnConfigurationData object for this OLAPDataColumn
     */
    public OLAPColumnConfigurationData getConfigurationData() {
        return configurationData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OLAPDataColumn)) return false;

        OLAPDataColumn<?> that = (OLAPDataColumn<?>) o;

        if (getConfigurationData() != null ? !getConfigurationData().equals(that.getConfigurationData()) : that.getConfigurationData() != null)
            return false;
        return !(getData() != null ? !getData().equals(that.getData()) : that.getData() != null);

    }

    @Override
    public int hashCode() {
        int result = getConfigurationData() != null ? getConfigurationData().hashCode() : 0;
        result = 31 * result + (getData() != null ? getData().hashCode() : 0);
        return result;
    }
}
