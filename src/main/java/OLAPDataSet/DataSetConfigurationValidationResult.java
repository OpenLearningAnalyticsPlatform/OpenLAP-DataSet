package OLAPDataSet;

/**
 * This object encapsulates the validaiton operation of a configuration of a DataSet
 */
public class DataSetConfigurationValidationResult {
    boolean isValid;
    String validationMessage;

    public DataSetConfigurationValidationResult(boolean isValid, String validationMessage) {
        this.isValid = isValid;
        this.validationMessage = validationMessage;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }
}
