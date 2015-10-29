package OLAPDataSet;

import java.util.HashMap;

/**
 * Created by lechip on 27/10/15.
 */
public class OLAPPortConfiguration {
    private HashMap <OLAPDataColumn, OLAPDataColumn> mapping;

    public OLAPPortConfiguration(HashMap<OLAPDataColumn, OLAPDataColumn> mapping) {
        this.mapping = mapping;
    }
    public HashMap<OLAPDataColumn, OLAPDataColumn> getMapping() {
        return mapping;
    }
}
