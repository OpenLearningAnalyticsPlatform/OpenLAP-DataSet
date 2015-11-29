package OLAPDataSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class contains the configuration data of a OLAPDataColumn, it is encapsulated for easy comparison of
 * configurations and for serialization separated from the data
 */
public class OLAPColumnConfigurationData {
    private final OLAPColumnDataType type;
    private String id;
    private boolean required;

    /**
     * Constructor for serialization pruproses
     */
    public OLAPColumnConfigurationData() {
        this.type = null;
    }

    public OLAPColumnConfigurationData(String id, OLAPColumnDataType type, boolean required) {
        this.setRequired(required);
        this.type = type;
        this.setId(id);
    }

    public OLAPColumnDataType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean validateConfigurationDataCorrespondence(OLAPColumnConfigurationData olapColumnConfigurationData) {
        return olapColumnConfigurationData.getType().equals(this.getType())
                && (olapColumnConfigurationData.getId().equals(this.getId()));
    }

    public boolean validateConfigurationDataTypeFromOutputPort(OLAPColumnConfigurationData outputPortConfigData)
    {
        return outputPortConfigData.getType().equals(this.getType())
                && (outputPortConfigData.getId() != null && !outputPortConfigData.getId().isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OLAPColumnConfigurationData)) return false;

        OLAPColumnConfigurationData that = (OLAPColumnConfigurationData) o;

        // if (isRequired() && isRequired() != that.isRequired()) return false;
        if (getType() != that.getType()) return false;
        return getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        int result = getType().hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + (isRequired() ? 1 : 0);
        return result;
    }

    /**
     * ToString method attempts to use the json representation of the object.
     * @return JSCON representation of the object
     */
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "OLAPColumnConfigurationData{" +
                    "type=" + type +
                    ", id='" + id + '\'' +
                    ", required=" + required +
                    '}';
        }
    }
}
