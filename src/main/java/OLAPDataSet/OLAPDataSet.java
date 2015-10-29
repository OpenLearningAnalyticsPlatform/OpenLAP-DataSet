package OLAPDataSet;

import exceptions.OLAPDataColumnException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        DataSetConfigurationValidationResult configResult = new DataSetConfigurationValidationResult();
        // Get the input as a list
        List<OLAPDataColumn> values = new ArrayList<OLAPDataColumn>(configuration.getMapping().values());

        // Check for required fields
        validatePresenceRequiredColumns(configResult, values);

        // Check for incoming fields being present
        validateInputColumnsPresent(configResult, values);

        for(OLAPDataColumn outputColumn : configuration.getMapping().keySet())
        {
            // Validate types
            OLAPDataColumn inputColumn = configuration.getMapping().get(outputColumn);
            if (!inputColumn.validate(outputColumn)){
                configResult.setIsValid(false);
                configResult.appendValidationMessage(String.format("Port %s expected %s, got %s instead.",
                        inputColumn.getId(), inputColumn.getType(), outputColumn.getType()));
            }
        }

        if(!configResult.isValid)
        {
            return configResult;
        }
        else
        {
            configResult.setValidationMessage(DataSetConfigurationValidationResult.VALID_CONFIGURATION);
            return configResult;
        }
    }

    /**
     * Utility method to get all required columns
     * @return
     */
    public List<OLAPDataColumn> getRequiredColumns()
    {
        List<OLAPDataColumn> columns = new ArrayList<OLAPDataColumn>(this.columns.values());
        List<OLAPDataColumn> requiredColumns = new ArrayList<OLAPDataColumn>();
        for (OLAPDataColumn column: columns)
        {
            if (column.isRequired()) requiredColumns.add(column);
        }
        return requiredColumns;
    }

    /**
     * Validates that all the required Columns of the DataSet are in a given list of Columns
     * @param configResult The config result object that can be modified to contain error messages
     * @param values The list to be checked if contains all the required values.
     */
    private void validatePresenceRequiredColumns(DataSetConfigurationValidationResult configResult,
                                                 List<OLAPDataColumn> values) {
        // Initialize a list of the required columns
        List<OLAPDataColumn> requiredColumns = getRequiredColumns();
        // Remove from the list all the items that are in the values
        requiredColumns.removeAll(values);
        // If there are still elements left, there are missing values.
        if (requiredColumns.size()>0){
            configResult.setIsValid(false);
            configResult.appendValidationMessage("Required columns not found");
            // Put message of every column that is not found
            for (OLAPDataColumn remainingColumn:requiredColumns)
            {
                configResult.appendValidationMessage(
                        String.format("Column: %s is required", remainingColumn.getId())
                );
            }
        }
        // Otherwise the required fields are present
        else configResult.setIsValid(true);
    }

    /**
     * Check that all the input columns are present on the actual DataSet
     * @param configResult The config result object that can be modified to contain error messages
     * @param values The list of columns to be checked if is all contained in the DataSet.
     */
    private void validateInputColumnsPresent(DataSetConfigurationValidationResult configResult,
                                             List<OLAPDataColumn> values) {
        // Get the columns present on the dataset
        List<OLAPDataColumn> dataSetColumns = new ArrayList<OLAPDataColumn>(this.columns.values());
        List<OLAPDataColumn> valuesCopy = new ArrayList<OLAPDataColumn>(values);
        // Remove from the list all the items that are in the dataSet Columns
        valuesCopy.removeAll(dataSetColumns);
        // If there are elements left, it means there is a mapping to be done.
        if(valuesCopy.size()>0)
        {
            configResult.setIsValid(false);
            configResult.appendValidationMessage("Columns not present on the destination DataSet");
            // Put message of every column that is not found
            for (OLAPDataColumn remainingColumn:valuesCopy)
            {
                configResult.appendValidationMessage(
                        String.format("Column: %s is required", remainingColumn.getId())
                );
            }
        }
        // Otherwise the required fields are present
        else configResult.setIsValid(true);
    }


}
