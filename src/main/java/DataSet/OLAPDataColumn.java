package DataSet;

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
