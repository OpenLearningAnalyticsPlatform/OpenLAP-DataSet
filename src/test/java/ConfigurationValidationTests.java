import OLAPDataSet.*;
import exceptions.OLAPDataColumnException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by lechip on 29/10/15.
 */
public class ConfigurationValidationTests {

    OLAPDataColumn stringColumn1;
    OLAPDataColumn stringColumn2;
    OLAPDataColumn intColumn1;
    OLAPDataColumn intColumn2;
    OLAPDataColumn noNameColumn;
    OLAPDataColumn nullColumn;
    OLAPDataSet dataSet1;
    OLAPDataSet dataSet2;

    @Before
    public void beforeTest()
    {
        try {
            stringColumn1 =
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("stringColumn1", OLAPColumnDataType.STRING, false);
            stringColumn2 =
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("stringColumn2", OLAPColumnDataType.STRING, false);
            intColumn1 =
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("intColumn1", OLAPColumnDataType.INTEGER, false);
            intColumn2 =
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("intColumn2", OLAPColumnDataType.INTEGER, false);
            noNameColumn =
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("", OLAPColumnDataType.INTEGER, false);
            nullColumn =
                    OLAPDataColumnFactory.createOLAPDataColumnOfType(null, OLAPColumnDataType.INTEGER, false);
            dataSet1 = new OLAPDataSet();
            dataSet2 = new OLAPDataSet();

        } catch (OLAPDataColumnException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void OLAPDataColumnValidationTest()
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
    public void OLAPDataSetConfigurationValidationTest()
    {
        OLAPPortConfiguration configuration1 = new OLAPPortConfiguration();
        OLAPPortConfiguration configuration2 = new OLAPPortConfiguration();
        OLAPPortConfiguration configuration3 = new OLAPPortConfiguration();
        OLAPPortConfiguration configuration4 = new OLAPPortConfiguration();


        try {
            dataSet1.addOLAPDataColumn(
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("intColumn1",OLAPColumnDataType.INTEGER,true));
            dataSet1.addOLAPDataColumn(
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("stringColumn1",OLAPColumnDataType.STRING,true));
            dataSet1.addOLAPDataColumn(
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("bananito",OLAPColumnDataType.STRING,false));

            // Configuration1 - Valid
            configuration1.getMapping().add
                    (
                            new OLAPPortMapping
                                    (
                                            intColumn1.getConfigurationData(),
                                            OLAPDataColumnFactory.createOLAPDataColumnOfType
                                                    ("intColumn1", OLAPColumnDataType.INTEGER, false).
                                                    getConfigurationData()
                                    )
                    );
            configuration1.getMapping().add
                    (
                            new OLAPPortMapping
                                    (
                                            stringColumn1.getConfigurationData(),
                                            OLAPDataColumnFactory.createOLAPDataColumnOfType
                                                    ("stringColumn1", OLAPColumnDataType.STRING, false).
                                                    getConfigurationData()
                                    )
                    );
            configuration1.getMapping().add
                    (
                            new OLAPPortMapping
                                    (
                                            stringColumn1.getConfigurationData(),
                                            OLAPDataColumnFactory.createOLAPDataColumnOfType
                                                    ("bananito",OLAPColumnDataType.STRING,false).
                                                    getConfigurationData()
                                    )
                    );

            // Configuration2 - Wrong mapping
            configuration2.getMapping().add
                    (
                            new OLAPPortMapping
                                    (
                                            intColumn1.getConfigurationData(),
                                            OLAPDataColumnFactory.createOLAPDataColumnOfType
                                                    ("bananito",OLAPColumnDataType.STRING,false).
                                                    getConfigurationData()
                                    )
                    );
            configuration2.getMapping().add
                    (
                            new OLAPPortMapping
                                    (
                                            intColumn1.getConfigurationData(),
                                            OLAPDataColumnFactory.createOLAPDataColumnOfType
                                                    ("intColumn1",OLAPColumnDataType.INTEGER,false).
                                                    getConfigurationData()
                                    )
                    );
            configuration2.getMapping().add
                    (
                            new OLAPPortMapping
                                    (
                                            stringColumn2.getConfigurationData(),
                                            OLAPDataColumnFactory.createOLAPDataColumnOfType
                                                    ("stringColumn1",OLAPColumnDataType.STRING,false).
                                                    getConfigurationData()
                                    )
                    );

            // Configuration 3 - Required fields not found
            configuration3.getMapping().add
                    (
                            new OLAPPortMapping
                                    (
                                            intColumn1.getConfigurationData(),
                                            OLAPDataColumnFactory.createOLAPDataColumnOfType
                                                    ("intColumn1", OLAPColumnDataType.INTEGER, false).
                                                    getConfigurationData()
                                    )
                    );
            configuration3.getMapping().add
                    (
                            new OLAPPortMapping
                                    (
                                            stringColumn1.getConfigurationData(),
                                            OLAPDataColumnFactory.createOLAPDataColumnOfType
                                                    ("bananito",OLAPColumnDataType.STRING,false).
                                                    getConfigurationData()
                                    )
                    );

            // Configuration 4 - Input has columns that do not exist on the dataset
            configuration4.getMapping().add
                    (
                            new OLAPPortMapping
                                    (
                                            intColumn1.getConfigurationData(),
                                            OLAPDataColumnFactory.createOLAPDataColumnOfType
                                                    ("intColumn1", OLAPColumnDataType.INTEGER, false).
                                                    getConfigurationData()
                                    )
                    );
            configuration4.getMapping().add
                    (
                            new OLAPPortMapping
                                    (
                                            stringColumn1.getConfigurationData(),
                                            OLAPDataColumnFactory.createOLAPDataColumnOfType
                                                    ("stringColumn1", OLAPColumnDataType.STRING, false).
                                                    getConfigurationData()
                                    )
                    );
            configuration4.getMapping().add
                    (
                            new OLAPPortMapping
                                    (
                                            stringColumn1.getConfigurationData(),
                                            OLAPDataColumnFactory.createOLAPDataColumnOfType
                                                    ("SomethingElse",OLAPColumnDataType.STRING,false).
                                                    getConfigurationData()
                                    )
                    );

        } catch (OLAPDataColumnException e) {
            e.printStackTrace();
        }
        // Valid configuration
        // Assert both status and message
        DataSetConfigurationValidationResult configurationValidationResult1 =
                dataSet1.validateConfiguration(configuration1);
        System.out.println("Configuration1: " + configurationValidationResult1.getValidationMessage());
        Assert.assertTrue("Expected true", dataSet1.validateConfiguration(configuration1).isValid());
        Assert.assertEquals(DataSetConfigurationValidationResult.VALID_CONFIGURATION,
                configurationValidationResult1.getValidationMessage());

        // Invalid configuration
        // Assert both status and message
        DataSetConfigurationValidationResult configurationValidationResult2 =
                dataSet1.validateConfiguration(configuration2);
        System.out.println("Configuration2: " + configurationValidationResult2.getValidationMessage());
        Assert.assertFalse("Expected false", dataSet1.validateConfiguration(configuration2).isValid());
        Assert.assertEquals("Port bananito expected STRING, got INTEGER instead.",
                configurationValidationResult2.getValidationMessage());

        // Insufficient arguments, not covering required fields
        // Assert both status and message
        DataSetConfigurationValidationResult configurationValidationResult3 =
                dataSet1.validateConfiguration(configuration3);
        System.out.println("Configuration3: " + configurationValidationResult3.getValidationMessage());
        Assert.assertFalse("Expected false", dataSet1.validateConfiguration(configuration3).isValid());
        Assert.assertEquals("Required columns not found\n" +
                        "Column: stringColumn1 is not found",
                configurationValidationResult3.getValidationMessage());

        // Input has fields not existent in the DataSet
        // Assert both status and message
        DataSetConfigurationValidationResult configurationValidationResult4 =
                dataSet1.validateConfiguration(configuration4);
        System.out.println("Configuration4: " + configurationValidationResult4.getValidationMessage());
        Assert.assertFalse("Expected false", dataSet1.validateConfiguration(configuration4).isValid());
        Assert.assertEquals("Columns not present on the destination DataSet\n" +
                        "Column: SomethingElse does not exist in the destination dataset",
                configurationValidationResult4.getValidationMessage());

    }

    @Test
    public void OLAPDataSetCreationTests()
    {

    }
}
