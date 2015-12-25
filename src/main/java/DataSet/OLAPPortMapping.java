package DataSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class represents a tuple or 'mapping' entry between two ports.
 * It has a outputPort which represents an entry of the configuration of the macro component GIVING data and an
 * inputPort, which represents the entry of the configuration that is to receive the outputPort of the receiving macro
 * component.
 * In simple terms, this class represents (outputPortColumn.ConfigData -> inputPortColumn.ConfigData)
 */
public class OLAPPortMapping {
    private final OLAPColumnConfigurationData outputPort;
    private final OLAPColumnConfigurationData inputPort;

    /**
     * Constructor for serialization pruproses
     */
    public OLAPPortMapping(){
        this.outputPort = null;
        this.inputPort = null;
    }

    /**
     * Standard constructor with in- and out- port.
     * @param outputPort The OLAPColumnConfigurationData considered the output of the tuple (origin)
     * @param inputPort The OLAPColumnConfigurationData considered the input of the tuple (destination)
     */
    public OLAPPortMapping(OLAPColumnConfigurationData outputPort, OLAPColumnConfigurationData inputPort) {
        this.outputPort = outputPort;
        this.inputPort = inputPort;
    }

    /**
     * @return The output port of the tuple (origin)
     */
    public OLAPColumnConfigurationData getOutputPort() {
        return outputPort;
    }

    /**
     * @return The input port of the tuple (destination)
     */
    public OLAPColumnConfigurationData getInputPort() {
        return inputPort;
    }

    @Override
    public int hashCode() { return outputPort.hashCode() ^ inputPort.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OLAPPortMapping)) return false;
        OLAPPortMapping mapping = (OLAPPortMapping) o;
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
            return "OLAPPortMapping{" +
                    "outputPort=" + outputPort +
                    ", inputPort=" + inputPort +
                    '}';
        }
    }
}
