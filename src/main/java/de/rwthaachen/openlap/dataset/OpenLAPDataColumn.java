package de.rwthaachen.openlap.dataset;

import java.util.ArrayList;

/**
 * This class is a grouping of a OpenLAPColumnConfigData and the data that the OpenLAPColumnConfigData
 * describes. In conjunction, it is denominated a OpenLAPDataColumn.
 */
public class OpenLAPDataColumn<T> {

    private final OpenLAPColumnConfigData configurationData;
    private ArrayList<T> data;

    /**
     * Standard constructor, sets fields to null.
     */
    public OpenLAPDataColumn() {
        this.configurationData = null;
    }

    /**
     * Constructor that sets the OpenLAPColumnConfigData with the ID, type and required properties
     * @param id ID of the OpenLAPDataColumn
     * @param type OpenLAPColumnDataType of the data of the OpenLAPDataColumn
     * @param isRequired Specifies if the OpenLAPDataColumn is required or not
     */
    public OpenLAPDataColumn(String id, OpenLAPColumnDataType type, boolean isRequired) {
        this.configurationData = new OpenLAPColumnConfigData(id, type, isRequired);
        this.data = new ArrayList<T>();
    }

    /**
     * Default constructor that sets the column as a non required OpenLAPDataColumn
     * @param id ID of the OpenLAPDataColumn
     * @param type OpenLAPColumnDataType of the data of the OpenLAPDataColumn
     */
    public OpenLAPDataColumn(OpenLAPColumnDataType type, String id) {
        this.configurationData = new OpenLAPColumnConfigData(id, type, false);
    }

    /**
     * Constructor that sets the OpenLAPColumnConfigData with the ID, type and required properties
     * @param id ID of the OpenLAPDataColumn
     * @param type OpenLAPColumnDataType of the data of the OpenLAPDataColumn
     * @param isRequired Specifies if the OpenLAPDataColumn is required or not
     */
    public OpenLAPDataColumn(String id, OpenLAPColumnDataType type, boolean isRequired, String title, String description) {
        this.configurationData = new OpenLAPColumnConfigData(id, type, isRequired, title, description);
        this.data = new ArrayList<T>();
    }

    /**
     * Validates the correspondence of type and ID of another OpenLAPColumnConfigData
     * @param openLAPColumnConfigData OpenLAPColumnConfigData to be compared with
     * @return true if the compared OpenLAPColumnConfigData Type and ID corresponds to the current
     */
    public boolean validateConfigurationData(OpenLAPColumnConfigData openLAPColumnConfigData){
        return this.getConfigurationData().validateConfigurationDataCorrespondence(openLAPColumnConfigData);
    }

    /**
     * @return Get the data of the OpenLAPDataColumn
     */
    public ArrayList<T> getData() {
        return data;
    }

    /**
     * @param data to be set on this OpenLAPDataColumn
     */
    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    /**
     * @return The OpenLAPColumnConfigData object for this OpenLAPDataColumn
     */
    public OpenLAPColumnConfigData getConfigurationData() {
        return configurationData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpenLAPDataColumn)) return false;

        OpenLAPDataColumn<?> that = (OpenLAPDataColumn<?>) o;

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
