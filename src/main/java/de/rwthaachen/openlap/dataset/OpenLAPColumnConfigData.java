package de.rwthaachen.openlap.dataset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class contains the configuration data of a OpenLAPDataColumn, it is encapsulated for easy comparison of
 * configurations and for serialization separated from the data.
 */
public class OpenLAPColumnConfigData {
    private final OpenLAPColumnDataType type;
    private String id;
    private boolean required;
    private String title;
    private String description;

    /**
     * Constructor for serialization purposes
     */
    public OpenLAPColumnConfigData() {
        this.type = null;
    }

    /**
     * Constructor with data, id and type
     * @param id ID of the OpenLAPDataColumn
     * @param type An OLAPColumDataType that describes the type of data of this OpenLAPDataColumn
     * @param required Specifies if the OpenLAPDataColumn is required or not
     */
    public OpenLAPColumnConfigData(String id, OpenLAPColumnDataType type, boolean required) {
        this.setRequired(required);
        this.type = type;
        this.setId(id);
    }

    
    public OpenLAPColumnConfigData(String id, OpenLAPColumnDataType type, boolean required, String title, String description) {
        this.setRequired(required);
        this.type = type;
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
    }

    /**
     * @return Type of the OpenLAPDataColumn
     */
    public OpenLAPColumnDataType getType() {
        return type;
    }

    /**
     * @return ID of the OpenLAPDataColumn
     */
    public String getId() {
        return id;
    }

    /**
     * @param id ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return true if OpenLAPDataColumn is required, otherwise false
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @param required set the OpenLAPDataColumn to required or not
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Validates the correspondence of type and ID of another OpenLAPColumnConfigData
     * @param openLAPColumnConfigData OpenLAPColumnConfigData to be compared with
     * @return true if the compared OpenLAPColumnConfigData Type and ID corresponds to the current
     * OpenLAPColumnConfigData
     */
    public boolean validateConfigurationDataCorrespondence(OpenLAPColumnConfigData openLAPColumnConfigData) {
        return openLAPColumnConfigData.getType().equals(this.getType())
                && (openLAPColumnConfigData.getId().equals(this.getId()));
    }

    /**
     * Validate the correspondance of the type of another OpenLAPColumnConfigData
     * @param outputPortConfigData OpenLAPColumnConfigData to be compared with this OpenLAPColumnConfigData
     * @return true if the type is the same and the other OpenLAPColumnConfigData id is not empty or null
     */
    public boolean validateConfigurationDataTypeFromOutputPort(OpenLAPColumnConfigData outputPortConfigData)
    {
        return outputPortConfigData.getType().equals(this.getType())
                && (outputPortConfigData.getId() != null && !outputPortConfigData.getId().isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpenLAPColumnConfigData)) return false;

        OpenLAPColumnConfigData that = (OpenLAPColumnConfigData) o;

        // if (isRequired() && isRequired() != that.isRequired()) return false;
        if (getType() != that.getType()) return false;
        return getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        int result = getType().hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + (isRequired() ? 1 : 0);
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }

    /**
     * ToString method attempts to use the json representation of the object.
     * @return JSON representation of the object
     */
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "OpenLAPColumnConfigData{" +
                    "type=" + type +
                    ", id='" + id + '\'' +
                    ", required=" + required +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
