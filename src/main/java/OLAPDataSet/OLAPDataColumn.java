package OLAPDataSet;

/**
 * Created by lechip on 27/10/15.
 */
public class OLAPDataColumn <T> {

    private final OLAPColumnConfigurationData configurationData;
    private T [] data;

    public OLAPDataColumn(String id, OLAPColumnDataType type, boolean isRequired) {
        this.configurationData = new OLAPColumnConfigurationData(id, type, isRequired);
    }

    public OLAPDataColumn(OLAPColumnDataType type, String id) {
        this.configurationData = new OLAPColumnConfigurationData(id, type, false);
    }

    public boolean validateConfigurationData(OLAPColumnConfigurationData olapColumnConfigurationData){
        return this.getConfigurationData().validateConfigurationDataCorrespondence(olapColumnConfigurationData);
    }

    public void setData(T[] data) {
        this.data = data;
    }

    public T[] getData() {
        return data;
    }

    public OLAPColumnConfigurationData getConfigurationData() {
        return configurationData;
    }

}
