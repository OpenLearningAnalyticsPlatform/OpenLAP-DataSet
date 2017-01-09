package de.rwthaachen.openlap.dataset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/**
 * Mapping between OpenLAPColumnConfigData. It uses an array of OLAPPortMappings that represents all the Output
 * and Input Ports. Is important to note that "OutputPort" denotes a column configuration of the SOURCE macro component,
 * whereas the "InputPort" denotes the column configuration of the RECEIVING macro component.
 * It is name so because the SENDING macro component "outputs" data to the RECEIVING macro component "inputs".
 */
public class OpenLAPPortConfig {
    private ArrayList<OpenLAPPortMapping> mapping;

    /**
     * Empty constructor
     */
    public OpenLAPPortConfig() {
        this.mapping = new ArrayList<OpenLAPPortMapping>();
    }

    /**
     * Standard constructor
     * @param mapping An Array of OLAPPortMappings
     */
    public OpenLAPPortConfig(ArrayList<OpenLAPPortMapping> mapping) {
        this.mapping = mapping;
    }

    /**
     * @return The Array of OLAPPortMappings
     */
    public ArrayList<OpenLAPPortMapping> getMapping() {
        return mapping;
    }

    /**
     * This method returns all the OpenLAPColumnConfigData that are outputs on the mapping.
     * @return An Array of OpenLAPColumnConfigData that corresponds to all the outputs of the OpenLAPPortConfig
     */
    @JsonIgnore
    public ArrayList<OpenLAPColumnConfigData> getOutputColumnConfigurationData()
    {
        return getConfigData(OLAPConfigDataSides.OUTPUTDATA);
    }

    /**
     * This method returns all the OpenLAPColumnConfigData that are inputs on the mapping.
     * @return An Array of OpenLAPColumnConfigData that corresponds to all the inputs of the OpenLAPPortConfig
     */
    @JsonIgnore
    public ArrayList<OpenLAPColumnConfigData> getInputColumnConfigurationData()
    {
        return getConfigData(OLAPConfigDataSides.INPUTDATA);
    }

    /**
     * Gets the input or the output side of the array of OpenLAPPortMapping
     * @param side OLAPConfigDataSides.OUTPUTDATA or OLAPConfigDataSides.INPUTDATA to define which side to get
     * @return OpenLAPPortMapping arrazy with either the output configurations or the input configurations
     */
    private ArrayList<OpenLAPColumnConfigData> getConfigData(OLAPConfigDataSides side) {
        ArrayList<OpenLAPColumnConfigData> result = new ArrayList<OpenLAPColumnConfigData>();

        for (OpenLAPPortMapping mappingEntry : this.mapping)
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
            return "OpenLAPPortConfig{" +
                    "mapping=" + mapping +
                    '}';
        }
    }

    /**
     * Possible directions of data of the configuration.
     */
    private enum OLAPConfigDataSides
    {
        OUTPUTDATA,
        INPUTDATA
    }
}
