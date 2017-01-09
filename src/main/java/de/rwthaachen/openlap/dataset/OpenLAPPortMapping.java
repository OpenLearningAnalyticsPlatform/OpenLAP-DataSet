package de.rwthaachen.openlap.dataset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class represents a tuple or 'mapping' entry between two ports.
 * It has a outputPort which represents an entry of the configuration of the macro component GIVING data and an
 * inputPort, which represents the entry of the configuration that is to receive the outputPort of the receiving macro
 * component.
 * In simple terms, this class represents (outputPortColumn.ConfigData -> inputPortColumn.ConfigData)
 */
public class OpenLAPPortMapping {
    private final OpenLAPColumnConfigData outputPort;
    private final OpenLAPColumnConfigData inputPort;

    /**
     * Constructor for serialization purposes
     */
    public OpenLAPPortMapping(){
        this.outputPort = null;
        this.inputPort = null;
    }

    /**
     * Standard constructor with in- and out- port.
     * @param outputPort The OpenLAPColumnConfigData considered the output of the tuple (origin)
     * @param inputPort The OpenLAPColumnConfigData considered the input of the tuple (destination)
     */
    public OpenLAPPortMapping(OpenLAPColumnConfigData outputPort, OpenLAPColumnConfigData inputPort) {
        this.outputPort = outputPort;
        this.inputPort = inputPort;
    }

    /**
     * @return The output port of the tuple (origin)
     */
    public OpenLAPColumnConfigData getOutputPort() {
        return outputPort;
    }

    /**
     * @return The input port of the tuple (destination)
     */
    public OpenLAPColumnConfigData getInputPort() {
        return inputPort;
    }

    @Override
    public int hashCode() { return outputPort.hashCode() ^ inputPort.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OpenLAPPortMapping)) return false;
        OpenLAPPortMapping mapping = (OpenLAPPortMapping) o;
        return this.outputPort.equals(mapping.getOutputPort()) &&
                this.inputPort.equals(mapping.getInputPort());
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
            return "OpenLAPPortMapping{" +
                    "outputPort=" + outputPort +
                    ", inputPort=" + inputPort +
                    '}';
        }
    }
}
