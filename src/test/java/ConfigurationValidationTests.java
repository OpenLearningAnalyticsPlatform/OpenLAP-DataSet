import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.rwthaachen.openlap.dataset.*;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by lechip on 29/10/15.
 */
public class ConfigurationValidationTests {

    OpenLAPDataColumn stringColumn1;
    OpenLAPDataColumn stringColumn2;
    OpenLAPDataColumn intColumn1;
    OpenLAPDataColumn intColumn2;
    OpenLAPDataColumn noNameColumn;
    OpenLAPDataColumn nullColumn;
    OpenLAPDataSet dataSet1;
    OpenLAPDataSet dataSet2;
    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void beforeTest()
    {
        // Configure mapper
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.getFactory().configure(JsonParser.Feature.ALLOW_COMMENTS, true);

        try {
            stringColumn1 =
                    OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("stringColumn1", OpenLAPColumnDataType.STRING, false);
            stringColumn2 =
                    OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("stringColumn2", OpenLAPColumnDataType.STRING, false);
            intColumn1 =
                    OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("intColumn1", OpenLAPColumnDataType.INTEGER, false);
            intColumn2 =
                    OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("intColumn2", OpenLAPColumnDataType.INTEGER, false);
            noNameColumn =
                    OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("", OpenLAPColumnDataType.INTEGER, false);
            nullColumn =
                    OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType(null, OpenLAPColumnDataType.INTEGER, false);
            dataSet1 = new OpenLAPDataSet();
            dataSet2 = new OpenLAPDataSet();

        } catch (OpenLAPDataColumnException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void OpenLAPDataColumnValidationTest()
    {
        // Valid configuration
        Assert.assertTrue(stringColumn1.validateConfigurationData(stringColumn1.getConfigurationData()));
        // Nameless columns
        Assert.assertFalse(stringColumn1.validateConfigurationData(noNameColumn.getConfigurationData()));
        Assert.assertFalse(stringColumn1.validateConfigurationData(nullColumn.getConfigurationData()));
        // Not valid (different types)
        Assert.assertFalse(stringColumn1.validateConfigurationData(intColumn1.getConfigurationData()));
    }

    @Test
    public void OpenLAPDataSetConfigurationValidationTest() throws OpenLAPDataColumnException {
        System.out.println("==Test Start: OpenLAPDataSetConfigurationValidationTest==");
        OpenLAPPortConfig configuration1 = new OpenLAPPortConfig();
        OpenLAPPortConfig configuration2 = new OpenLAPPortConfig();
        OpenLAPPortConfig configuration3 = new OpenLAPPortConfig();
        OpenLAPPortConfig configuration4 = new OpenLAPPortConfig();

        dataSet1.addOpenLAPDataColumn(
                OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("intColumn1", OpenLAPColumnDataType.INTEGER,true));
        dataSet1.addOpenLAPDataColumn(
                OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("stringColumn1", OpenLAPColumnDataType.STRING,true));
        dataSet1.addOpenLAPDataColumn(
                OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("bananito", OpenLAPColumnDataType.STRING,false));

        // Configuration1 - Valid
        configuration1.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        intColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("intColumn1", OpenLAPColumnDataType.INTEGER, false).
                                                getConfigurationData()
                                )
                );
        configuration1.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        stringColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("stringColumn1", OpenLAPColumnDataType.STRING, false).
                                                getConfigurationData()
                                )
                );
        configuration1.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        stringColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("bananito", OpenLAPColumnDataType.STRING,false).
                                                getConfigurationData()
                                )
                );

        // Configuration2 - Wrong mapping
        configuration2.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        intColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("bananito", OpenLAPColumnDataType.STRING,false).
                                                getConfigurationData()
                                )
                );
        configuration2.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        intColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("intColumn1", OpenLAPColumnDataType.INTEGER,false).
                                                getConfigurationData()
                                )
                );
        configuration2.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        stringColumn2.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("stringColumn1", OpenLAPColumnDataType.STRING,false).
                                                getConfigurationData()
                                )
                );

        // Configuration 3 - Required fields not found
        configuration3.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        intColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("intColumn1", OpenLAPColumnDataType.INTEGER, false).
                                                getConfigurationData()
                                )
                );
        configuration3.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        stringColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("bananito", OpenLAPColumnDataType.STRING,false).
                                                getConfigurationData()
                                )
                );

        // Configuration 4 - Input has columns that do not exist on the dataset
        configuration4.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        intColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("intColumn1", OpenLAPColumnDataType.INTEGER, false).
                                                getConfigurationData()
                                )
                );
        configuration4.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        stringColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("stringColumn1", OpenLAPColumnDataType.STRING, false).
                                                getConfigurationData()
                                )
                );
        configuration4.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        stringColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("SomethingElse", OpenLAPColumnDataType.STRING,false).
                                                getConfigurationData()
                                )
                );


        // Valid configuration
        // Assert both status and message
        OpenLAPDataSetConfigValidationResult configurationValidationResult1 =
                dataSet1.validateConfiguration(configuration1);
        System.out.println("Configuration1: " + configurationValidationResult1.getValidationMessage());

        Assert.assertTrue("Expected true", dataSet1.validateConfiguration(configuration1).isValid());
        Assert.assertEquals(OpenLAPDataSetConfigValidationResult.VALID_CONFIGURATION,
                configurationValidationResult1.getValidationMessage());

        // Invalid configuration
        // Assert both status and message
        OpenLAPDataSetConfigValidationResult configurationValidationResult2 =
                dataSet1.validateConfiguration(configuration2);

        System.out.println("Configuration2: " + configurationValidationResult2.getValidationMessage());
        Assert.assertFalse("Expected false", dataSet1.validateConfiguration(configuration2).isValid());
        Assert.assertEquals("Port bananito expected STRING, got INTEGER instead.",
                configurationValidationResult2.getValidationMessage());

        // Insufficient arguments, not covering required fields
        // Assert both status and message
        OpenLAPDataSetConfigValidationResult configurationValidationResult3 =
                dataSet1.validateConfiguration(configuration3);
        System.out.println("Configuration3: " + configurationValidationResult3.getValidationMessage());
        Assert.assertFalse("Expected false", dataSet1.validateConfiguration(configuration3).isValid());
        Assert.assertEquals("Required columns not found\n" +
                        "Column: stringColumn1 is not found",
                configurationValidationResult3.getValidationMessage());

        // Input has fields not existent in the DataSet
        // Assert both status and message
        OpenLAPDataSetConfigValidationResult configurationValidationResult4 =
                dataSet1.validateConfiguration(configuration4);
        System.out.println("Configuration4: " + configurationValidationResult4.getValidationMessage());
        Assert.assertFalse("Expected false", dataSet1.validateConfiguration(configuration4).isValid());
        Assert.assertEquals("Columns not present on the destination DataSet\n" +
                        "Column: SomethingElse does not exist in the destination dataset",
                configurationValidationResult4.getValidationMessage());
        System.out.println("==Test Finish: OpenLAPDataSetConfigurationValidationTest==");
    }

    @Test
    public void OpenLAPConfigurationSerializationTests() throws OpenLAPDataColumnException, IOException {
        OpenLAPPortConfig configuration1 = new OpenLAPPortConfig();
        OpenLAPPortConfig configuration2;

        // Initialize configuration manually
        configuration1.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        intColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("intColumn1", OpenLAPColumnDataType.INTEGER, false).
                                                getConfigurationData()
                                )
                );
        configuration1.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        stringColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("stringColumn1", OpenLAPColumnDataType.STRING, false).
                                                getConfigurationData()
                                )
                );
        configuration1.getMapping().add
                (
                        new OpenLAPPortMapping
                                (
                                        stringColumn1.getConfigurationData(),
                                        OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType
                                                ("bananito", OpenLAPColumnDataType.STRING,false).
                                                getConfigurationData()
                                )
                );

        // Deserialize from file.
        configuration2 = mapper.
                readValue(this.getClass().getResourceAsStream("ConfigurationSample.json"),
                        OpenLAPPortConfig.class);
        // Assert
        Assert.assertEquals(mapper.writeValueAsString(configuration1), mapper.writeValueAsString(configuration2));
    }

    @Test
    public void OpenLAPDataSetSerialization() throws IOException, OpenLAPDataColumnException {

        // Setup dataset manually
        dataSet1.addOpenLAPDataColumn(
                OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("intColumn1", OpenLAPColumnDataType.INTEGER,true));
        dataSet1.addOpenLAPDataColumn(
                OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("stringColumn1", OpenLAPColumnDataType.STRING,true));
        dataSet1.addOpenLAPDataColumn(
                OpenLAPDataColumnFactory.createOpenLAPDataColumnOfType("bananito", OpenLAPColumnDataType.STRING,false));
        dataSet1.getColumns().get("intColumn1").getData().add(1);
        dataSet1.getColumns().get("stringColumn1").getData().add("value1");

        // Deserialize file
        dataSet2 = mapper.readValue(this.getClass().getResourceAsStream("DataSetSample.json"),
                OpenLAPDataSet.class);

        Assert.assertEquals(mapper.writeValueAsString(dataSet1),mapper.writeValueAsString(dataSet2));

    }

}
