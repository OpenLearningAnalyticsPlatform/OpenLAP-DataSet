package OLAPDataSet;

import java.util.ArrayList;

/**
 * Created by lechip on 27/10/15.
 */
public class OLAPDataColumn <T> {

    private final OLAPColumnConfigurationData configurationData;
    private ArrayList<T> data;

    public OLAPDataColumn() {
        this.configurationData = null;
    }

    public OLAPDataColumn(String id, OLAPColumnDataType type, boolean isRequired) {
        this.configurationData = new OLAPColumnConfigurationData(id, type, isRequired);
        this.data = new ArrayList<T>();
    }

    public OLAPDataColumn(OLAPColumnDataType type, String id) {
        this.configurationData = new OLAPColumnConfigurationData(id, type, false);
    }

    public boolean validateConfigurationData(OLAPColumnConfigurationData olapColumnConfigurationData){
        return this.getConfigurationData().validateConfigurationDataCorrespondence(olapColumnConfigurationData);
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public OLAPColumnConfigurationData getConfigurationData() {
        return configurationData;
    }

}
