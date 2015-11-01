package OLAPDataSet;

/**
 * This class contains the configuration data of a OLAPDataColumn, it is encapsulated for easy comparison of
 * configurations and for serialization separated from the data
 */
public class OLAPColumnConfigurationData {
    private final OLAPColumnDataType type;
    private String id;
    private boolean required;

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
}
