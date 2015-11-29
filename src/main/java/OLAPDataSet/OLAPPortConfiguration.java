package OLAPDataSet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Mapping between OLAPColumnConfigurationData. It uses an array of OLAPPortMappings that represents all the Output
 * and Input Ports. Is important to note that "OutputPort" denotes a column configuration of the SOURCE macro component,
 * whereas the "InputPort" denotes the column configuration of the RECEIVING macro component.
 * It is name so because the SENDING macro component "outputs" data to the RECEIVING macro component "inputs".
 */
public class OLAPPortConfiguration {
    private ArrayList<OLAPPortMapping> mapping;

    public OLAPPortConfiguration() {
        this.mapping = new ArrayList<OLAPPortMapping>();
    }

    public OLAPPortConfiguration(ArrayList<OLAPPortMapping> mapping) {
        this.mapping = mapping;
    }

    public ArrayList<OLAPPortMapping> getMapping() {
        return mapping;
    }

    @JsonIgnore
    public ArrayList<OLAPColumnConfigurationData> getOutputColumnConfigurationData()
    {
        return getConfigData(OLAPConfigDataSides.OUTPUTDATA);
    }

    @JsonIgnore
    public ArrayList<OLAPColumnConfigurationData> getInputColumnConfigurationData()
    {
        return getConfigData(OLAPConfigDataSides.INPUTDATA);
    }

    /**
     * Gets the input or the output side of the array of OLAPPortMapping
     * @param side OLAPConfigDataSides.OUTPUTDATA or OLAPConfigDataSides.INPUTDATA to define which side to get
     * @return OLAPPortMapping arrazy with either the output configurations or the input configurations
     */
    private ArrayList<OLAPColumnConfigurationData> getConfigData(OLAPConfigDataSides side) {
        ArrayList<OLAPColumnConfigurationData> result = new ArrayList<OLAPColumnConfigurationData>();

        for (OLAPPortMapping mappingEntry : this.mapping)
        {
            // OutputData required
            if (side == OLAPConfigDataSides.OUTPUTDATA)
            {
                result.add(mappingEntry.getOutputPort());
            }
            else result.add(mappingEntry.getInputPort());
        }

        return result;
    }

    /**
     * ToString method attempts to use the json representation of the object.
     * @return JSCON representation of the object
     */
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "OLAPPortConfiguration{" +
                    "mapping=" + mapping +
                    '}';
        }
    }

    private enum OLAPConfigDataSides
    {
        OUTPUTDATA,
        INPUTDATA
    }
}
