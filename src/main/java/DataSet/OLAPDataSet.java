package DataSet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import exceptions.OLAPDataColumnException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lechip on 27/10/15.
 */
public class OLAPDataSet {
    // Map of the columns with the String ID. The string is taken from the OLAPColumnConfigurationData of the column.
    private HashMap<String, OLAPDataColumn> columns;

    public OLAPDataSet() {
        this.columns = new HashMap<String, OLAPDataColumn>();
    }

    public HashMap<String, OLAPDataColumn> getColumns() {
        return columns;
    }

    public void addOLAPDataColumn(OLAPDataColumn<?> column) throws OLAPDataColumnException {
        String columnId = column.getConfigurationData().getId();
        if (columns.containsKey(columnId) || columnId.isEmpty() || columnId == null)
            throw new OLAPDataColumnException(OLAPDataColumnException.COLUMN_ALREADY_EXISTS, columnId);
        else
        {
            columns.put(columnId, column);
        }
    }

    public OLAPDataSetConfigurationValidationResult validateConfiguration(OLAPPortConfiguration configuration)
    {
        // Initialize object with results
        OLAPDataSetConfigurationValidationResult configResult = new OLAPDataSetConfigurationValidationResult();
        // Get the input as a list
        List<OLAPColumnConfigurationData> values =
                new ArrayList<OLAPColumnConfigurationData>(configuration.getInputColumnConfigurationData());

        // Check for required fields
        validatePresenceRequiredColumns(configResult, values);
        if(!configResult.isValid) return configResult;

        // Check for incoming fields being present
        validateInputColumnsCorrespondence(configResult, values);
        if(!configResult.isValid) return configResult;

        for(OLAPPortMapping mappingEntry: configuration.getMapping())
        {
            // Validate types
            OLAPColumnConfigurationData inputColumn = mappingEntry.getInputPort();
            if (!inputColumn.validateConfigurationDataTypeFromOutputPort(mappingEntry.getOutputPort())){
                configResult.setIsValid(false);
                configResult.appendValidationMessage(String.format("Port %s expected %s, got %s instead.",
                        inputColumn.getId(), inputColumn.getType(), mappingEntry.getOutputPort().getType()));
            }
        }

        if(!configResult.isValid) return configResult;
        else
        {
            configResult.setValidationMessage(OLAPDataSetConfigurationValidationResult.VALID_CONFIGURATION);
            return configResult;
        }
    }


    /***
     * Utility method to get all required columns
     * @param onlyRequiredColumns if true, returns only required columns
     * @return A list of the OLAPDataColumns that are required
     */
    public List<OLAPDataColumn> getColumnsAsList(boolean onlyRequiredColumns)
    {
        List<OLAPDataColumn> columns = new ArrayList<OLAPDataColumn>(this.columns.values());
        if (!onlyRequiredColumns) return columns;
        else
        {
            List<OLAPDataColumn> requiredColumns = new ArrayList<OLAPDataColumn>();
            for (OLAPDataColumn column: columns)
            {
                if (column.getConfigurationData().isRequired()) requiredColumns.add(column);
            }
            return requiredColumns;
        }
    }

    /**
     * Utility method to get all required columns configuration data
     * @param onlyRequiredColumnsConfigurationData if true, returns only required columns OLAPColumnConfigurationData
     * @return a list with the OLAPColumnConfigurationData of the required columns
     */
    public List<OLAPColumnConfigurationData> getColumnsConfigurationData(boolean onlyRequiredColumnsConfigurationData)
    {
        List<OLAPDataColumn> columns;
        if (onlyRequiredColumnsConfigurationData)
            columns = getColumnsAsList(true);
        else columns = getColumnsAsList(false);

        List<OLAPColumnConfigurationData> result = new ArrayList<OLAPColumnConfigurationData>();

        for(OLAPDataColumn column : columns)
        {
            result.add(column.getConfigurationData());
        }
        return result;
    }

    /**
     * Get a list of the OLAPColumnConfigurationData of all the columns of the Dataset
     * @return Get a list of the OLAPColumnConfigurationData of all the columns of the Dataset
     */
    @JsonIgnore
    public List<OLAPColumnConfigurationData> getColumnsConfigurationData()
    {
        return this.getColumnsConfigurationData(false);
    }



    /**
     * Validates that all the required Columns of the DataSet are in a given list of Columns
     * @param configResult The config result object that can be modified to contain error messages
     * @param values The list to be checked if contains all the required values.
     */
    private void validatePresenceRequiredColumns(OLAPDataSetConfigurationValidationResult configResult,
                                                 List<OLAPColumnConfigurationData> values) {
        // Initialize a list of the required columns
        List<OLAPColumnConfigurationData> requiredColumnConfigData = getColumnsConfigurationData(true);
        // Remove from the list all the items that are in the values
        removeMatchingColumnData(requiredColumnConfigData, values);
        // If there are still elements left, there are missing values.
        if (requiredColumnConfigData.size()>0){
            configResult.setIsValid(false);
            configResult.appendValidationMessage("Required columns not found");
            // Put message of every column that is not found
            for (OLAPColumnConfigurationData remainingColumnConfigData:requiredColumnConfigData)
            {
                configResult.appendValidationMessage(
                        String.format("Column: %s is not found", remainingColumnConfigData.getId())
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
    private void validateInputColumnsCorrespondence(OLAPDataSetConfigurationValidationResult configResult,
                                                    List<OLAPColumnConfigurationData> values) {
        // Get the columns present on the dataset
        List<OLAPColumnConfigurationData> dataSetColumns = getColumnsConfigurationData();
        List<OLAPColumnConfigurationData> valuesCopy = new ArrayList<OLAPColumnConfigurationData>(values);
        // Remove from the list all the items that are in the dataSet Columns
        removeMatchingColumnData(valuesCopy, dataSetColumns);
        // If there are elements left, it means there is a mapping to be done.
        if(valuesCopy.size()>0)
        {
            configResult.setIsValid(false);
            configResult.appendValidationMessage("Columns not present on the destination DataSet");
            // Put message of every column that is not found
            for (OLAPColumnConfigurationData remainingColumn:valuesCopy)
            {
                configResult.appendValidationMessage(
                        String.format("Column: %s does not exist in the destination dataset", remainingColumn.getId())
                );
            }
        }
        // Otherwise the required fields are present
        else configResult.setIsValid(true);
    }

    /**
     * Remove the columns of the first parameter that exists on the second parameter
     * @param original
     * @param removalList
     */
    private void removeMatchingColumnData(List<OLAPColumnConfigurationData> original,
                                          List<OLAPColumnConfigurationData> removalList) {
        //for each element of the removal list check if is valid on the original
        for (OLAPColumnConfigurationData removalListConfig: removalList)
        {
            original.removeIf(e -> (e.validateConfigurationDataCorrespondence(removalListConfig)));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OLAPDataSet)) return false;

        OLAPDataSet that = (OLAPDataSet) o;

        return !(getColumns() != null ? !getColumns().equals(that.getColumns()) : that.getColumns() != null);

    }

    @Override
    public int hashCode() {
        return getColumns() != null ? getColumns().hashCode() : 0;
    }
}
