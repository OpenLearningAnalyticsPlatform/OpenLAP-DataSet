package OLAPDataSet;

import exceptions.OLAPDataColumnException;

import java.util.HashMap;

/**
 * Created by lechip on 27/10/15.
 */
public class OLAPDataSet {

    private HashMap<String, OLAPDataColumn> columns;

    public OLAPDataSet() {
        this.columns = new HashMap<String, OLAPDataColumn>();
    }

    public void addOLAPDataColumn(OLAPDataColumn<?> column) throws OLAPDataColumnException {
        String columnId = column.getId();
        if (columns.containsKey(columnId) || columnId.isEmpty() || columnId == null)
            throw new OLAPDataColumnException(OLAPDataColumnException.COLUMN_ALREADY_EXISTS, columnId);
        else
        {
            columns.put(columnId, column);
        }
    }

    public DataSetConfigurationValidationResult validateConfiguration(OLAPPortConfiguration configuration)
    {
        //TODO
        return new DataSetConfigurationValidationResult(false, "");
    }
}
