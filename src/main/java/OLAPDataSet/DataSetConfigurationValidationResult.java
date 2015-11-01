package OLAPDataSet;

/**
 * This object encapsulates the validaiton operation of a configuration of a DataSet
 */
public class DataSetConfigurationValidationResult {
    public static final String VALID_CONFIGURATION = "Valid configuration";
    boolean isValid;
    String validationMessage;

    public DataSetConfigurationValidationResult() {
        this.isValid = false;
        this.validationMessage = "";
    }

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

    public String appendValidationMessage(String message) {
      if(this.validationMessage.isEmpty() || this.validationMessage == null)
          this.validationMessage = message;
      else
          this.validationMessage = this.validationMessage + System.lineSeparator() + message;
      return this.validationMessage;
    }
}
