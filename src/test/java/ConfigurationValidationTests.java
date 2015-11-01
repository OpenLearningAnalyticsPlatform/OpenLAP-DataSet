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
    OLAPPortConfiguration configuration1;

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
        try {
            dataSet1.addOLAPDataColumn(
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("intColumn1",OLAPColumnDataType.INTEGER,true));
            dataSet1.addOLAPDataColumn(
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("stringColumn1",OLAPColumnDataType.STRING,true));
            dataSet1.addOLAPDataColumn(
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("bananito",OLAPColumnDataType.STRING,true));

            configuration1 = new OLAPPortConfiguration();
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

        } catch (OLAPDataColumnException e) {
            e.printStackTrace();
        }
        // Valid configuration
        // Assert both status and message
        System.out.println(dataSet1.validateConfiguration(configuration1).getValidationMessage());
        Assert.assertTrue("Expected true", dataSet1.validateConfiguration(configuration1).isValid());

        // Invalid configuration
        // Assert both status and message

        // Insufficient arguments
        // Assert both status and message

        // Input has fieds not existent in the DataSet
        // Assert both status and message

        //
    }

    @Test
    public void OLAPDataSetCreationTests()
    {

    }
}
