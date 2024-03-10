package org.jfree.data;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class DataUtilitiesTest {
    // Mockery objects for mocking Values2D and KeyedValues interfaces
    private Mockery mockery, mockery_missing, mockery_one_row, mockery_one_column, mockery_key_values;
    // KeyedValues object for testing createNumberArray2D method
    private KeyedValues keyedValues;
    // Values2D objects for different test scenarios
    private Values2D values2D, values2D_with_missing, values2D_one_row, values2D_one_column;

    // Setup method to initialize mock objects before each test
    @Before
    public void setUp() throws Exception {
    	
        // Mockery for KeyedValues
        mockery_key_values = new Mockery();
        keyedValues = mockery_key_values.mock(KeyedValues.class);
        mockery_key_values.checking(new Expectations() {{
            // Expectations for item count
            atLeast(1).of(keyedValues).getItemCount();
            will(returnValue(4));

            // Expectations for keys and values
            atLeast(1).of(keyedValues).getKey(0);
            will(returnValue(0));

            atLeast(1).of(keyedValues).getKey(1);
            will(returnValue(1));

            atLeast(1).of(keyedValues).getKey(2);
            will(returnValue(2));

            atLeast(1).of(keyedValues).getKey(3);
            will(returnValue(3));

            atLeast(1).of(keyedValues).getValue(0);
            will(returnValue(1.0));

            atLeast(1).of(keyedValues).getValue(1);
            will(returnValue(5.0));

            atLeast(1).of(keyedValues).getValue(2);
            will(returnValue(10.0));

            atLeast(1).of(keyedValues).getValue(3);
            will(returnValue(15.0));
        }});
        
        // Mockery for regular Values2D
        mockery = new Mockery();
        values2D = mockery.mock(Values2D.class);
        mockery.checking(new Expectations() {{
            // Expectations for column count
            one(values2D).getColumnCount();
            will(returnValue(3));

            // Expectations for row count
            one(values2D).getRowCount();
            will(returnValue(2));

            // Expectations for values at specific indices
            one(values2D).getValue(0, 0);
            will(returnValue(0));

            one(values2D).getValue(0, 1);
            will(returnValue(1));

            one(values2D).getValue(0, 2);
            will(returnValue(2));

            one(values2D).getValue(1, 0);
            will(returnValue(10));

            one(values2D).getValue(1, 1);
            will(returnValue(20));

            one(values2D).getValue(1, 2);
            will(returnValue(30));
        }});

        // Mockery for Values2D with missing values
        mockery_missing = new Mockery();
        values2D_with_missing = mockery_missing.mock(Values2D.class);
        mockery_missing.checking(new Expectations() {{
            // Expectations for column count
            one(values2D_with_missing).getColumnCount();
            will(returnValue(3));

            // Expectations for row count
            one(values2D_with_missing).getRowCount();
            will(returnValue(2));

            // Expectations for values at specific indices, with a missing value
            one(values2D_with_missing).getValue(0, 0);
            will(returnValue(0));

            one(values2D_with_missing).getValue(0, 1);
            will(returnValue(null));

            one(values2D_with_missing).getValue(0, 2);
            will(returnValue(2));

            one(values2D_with_missing).getValue(1, 0);
            will(returnValue(10));

            one(values2D_with_missing).getValue(1, 1);
            will(returnValue(20));

            one(values2D_with_missing).getValue(1, 2);
            will(returnValue(30));
        }});
        
        // Mockery for Values2D with one column
        mockery_one_column = new Mockery();
        values2D_one_column = mockery_one_column.mock(Values2D.class);
        mockery_one_column.checking(new Expectations() {{
            // Expectations for column count
            one(values2D_one_column).getColumnCount();
            will(returnValue(1));

            // Expectations for row count
            one(values2D_one_column).getRowCount();
            will(returnValue(2));

            // Expectations for values at specific indices
            one(values2D_one_column).getValue(0, 0);
            will(returnValue(10));

            one(values2D_one_column).getValue(1, 0);
            will(returnValue(20));
        }});

        // Mockery for Values2D with one row
        mockery_one_row = new Mockery();
        values2D_one_row = mockery_one_row.mock(Values2D.class);
        mockery_one_row.checking(new Expectations() {{
            // Expectations for column count
            one(values2D_one_row).getColumnCount();
            will(returnValue(3));

            // Expectations for row count
            one(values2D_one_row).getRowCount();
            will(returnValue(1));

            // Expectations for values at specific indices
            one(values2D_one_row).getValue(0, 0);
            will(returnValue(0));

            one(values2D_one_row).getValue(0, 1);
            will(returnValue(1));

            one(values2D_one_row).getValue(0, 2);
            will(returnValue(2));
        }});

    }



    // Test cases for calculateColumnTotal
    // Test case for DataUtilities.calculateColumnTotal with zero column
    @Test
    public void testCalculateColumnTotalWithColumnZero() {
        double result = DataUtilities.calculateColumnTotal(values2D, 0);
        assertEquals("Total of the column should be 10", 10, result, 0);
    }

    // Test case for DataUtilities.calculateColumnTotal with missing values
    @Test
    public void testCalculateColumnTotalWithMissingValue() {
        double result = DataUtilities.calculateColumnTotal(values2D_with_missing, 1);
        assertEquals("Total of the column with missing values should be 20", 20, result, 0);
    }
    
    // Test case for DataUtilities.calculateColumnTotal with one random input
    @Test
    public void testCalculateColumnTotalBasic() {
        double result = DataUtilities.calculateColumnTotal(values2D, 1);
        assertEquals("Total of the column should be 21", 21, result, 0);
    }

    // Test case for DataUtilities.calculateColumnTotal with last column
    @Test
    public void testCalculateColumnTotalLastColumn() {
        double result = DataUtilities.calculateColumnTotal(values2D, 2);
        assertEquals("Total of the last column should be 32", 32, result, 0);
    }

    // Test case for DataUtilities.calculateColumnTotal with one row
    @Test
    public void testCalculateColumnTotalOneRow() {
        double result = DataUtilities.calculateColumnTotal(values2D_one_row, 1);
        assertEquals("Total of the column with one row should be 1", 1, result, 0);
    }

    // Test case for DataUtilities.calculateColumnTotal with one column
    @Test
    public void testCalculateColumnTotalOneColumn() {
        double result = DataUtilities.calculateColumnTotal(values2D_one_column, 0);
        assertEquals("Total of the column with one column should be 30", 30, result, 0);
    }

    
    
    
    
    // Test cases for calculateRowTotal
    // Test case for DataUtilities.calculateRowTotal with zero row
    @Test
    public void testCalculateRowTotalWithRowZero() {
        double result = DataUtilities.calculateRowTotal(values2D, 0);
        assertEquals("Total of the row should be 3", 3, result, 0);
    }
    
    // Test case for DataUtilities.calculateRowTotal with missing values
    @Test
    public void testCalculateRowWithMissingValue() {
        double result = DataUtilities.calculateRowTotal(values2D_with_missing, 0);
        assertEquals("Total of the row with missing values should be 2", 2, result, 0);
    }
    
    // Test case for DataUtilities.calculateRowTotal with random input
    @Test
    public void testCalculateRowBasic() {
        double result = DataUtilities.calculateRowTotal(values2D, 0);
        assertEquals("Total of the row should be 3", 3, result, 0);
    }

    // Test case for DataUtilities.calculateRowTotal with last row
    @Test
    public void testCalculateRowLastRow() {
        double result = DataUtilities.calculateRowTotal(values2D, 1);
        assertEquals("Total of the last row should be 60", 60, result, 0);
    }

    // Test case for DataUtilities.calculateRowTotal with one row
    @Test
    public void testCalculateRowOneRow() {
        double result = DataUtilities.calculateRowTotal(values2D_one_row, 0);
        assertEquals("Total of the row with one row should be 3", 3, result, 0);
    }

    // Test case for DataUtilities.calculateRowTotal with one column
    @Test
    public void testCalculateRowOneColumn() {
        double result = DataUtilities.calculateRowTotal(values2D_one_column, 0);
        assertEquals("Total of the row with one column should be 10", 10, result, 0);
    }
    
    
    
    
    
    // Test cases for createNumberArray
    // Test case for DataUtilities.createNumberArray with size greater than one
    @Test
    public void testCreateNumberArrayWithSizeMoreThanOne() {
        // Creating a sample list of doubles
        double[] list = new double[10];
        for (int i = 0; i < 10; i++) {
            list[i] = i * 1.1;
        }
        // Generating Number array
        Number[] generated_list = DataUtilities.createNumberArray(list);
        // Assertions
        assertEquals("The input list length should be 10", list.length, 10);
        assertEquals("The generated number array length should be 10", generated_list.length, 10);
        for (int i = 0; i < 10; i++) {
            assertEquals("All the values in input and generated list should be equal", list[i], generated_list[i]);
        }
    }

    // Test case for DataUtilities.createNumberArray with size of one
    @Test
    public void testCreateNumberArrayWithSizeOne() {
        double[] list = new double[1];
        list[0] = 5;
        Number[] generated_list = DataUtilities.createNumberArray(list);
        assertEquals("Array of values length should be 1 ", generated_list.length, 1);
        assertEquals("Array of values first value should be 5", 5, generated_list[0].intValue());
    }

    // Test case for DataUtilities.createNumberArray with size of zero
    @Test
    public void testCreateNumberArrayWithSizeZero() {
        double[] list = new double[0];
        Number[] generated_list = DataUtilities.createNumberArray(list);
        assertEquals("The length of array of values should be 0", 0, generated_list.length);
    }

    
    
    
    
    // Test cases for createNumberArray2D
    // Test case for DataUtilities.createNumberArray2D with null input
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumberArray2dNullInput() {
        DataUtilities.createNumberArray2D(null);
    }

    
    // Test case for DataUtilities.createNumberArray2D with multi-dimensional array
    @Test
    public void testCreateNumberArray2d() {
        double[][] list = new double[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                list[i][j] = i * 1.1 + j;
            }
        }
        Number[][] generated_list = DataUtilities.createNumberArray2D(list);
        assertEquals("The length of the input list row should be 10", list.length, 10);
        assertEquals("The length of list row should be 10", generated_list.length, 10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals("All the values in input and generated list should be equal", list[i][j], generated_list[i][j]);
            }
        }
    }
    
    // Test case for DataUtilities.createNumberArray2D with size zero
    @Test
    public void testCreateNumberArray2dWithSizeZero() {
        double[][] list = new double[0][0];
        Number[][] generated_list = DataUtilities.createNumberArray2D(list);
        assertEquals("The length of the input list should be 0", list.length, 0);
        assertEquals("The length of list should be 0", generated_list.length, 0);
    }
    
    // Test case for DataUtilities.createNumberArray2D with zero columns
    @Test
    public void testCreateNumberArray2dWithZeroColumn() {
        double[][] list = new double[10][0];
        Number[][] generated_list = DataUtilities.createNumberArray2D(list);
        assertEquals("The length of list column should be 10", generated_list.length, 10);
        assertEquals("The length of list row should be 0", generated_list[0].length, 0);
    }

    // Test case for DataUtilities.createNumberArray2D with one row
    @Test
    public void testCreateNumberArray2dWithOneRow() {
        double[][] list = new double[1][10];
        for (int i = 0; i < 10; i++) {
            list[0][i] = i * 1.1;
        }
        Number[][] generated_list = DataUtilities.createNumberArray2D(list);
        assertEquals("The length of list row should be 1", generated_list.length, 1);
        assertEquals("The length of list column should be 10", generated_list[0].length, 10);
        for (int i = 0; i < 10; i++) {
            assertEquals("All the values in input and generated list should be equal", list[0][i], generated_list[0][i]);
        }
    }

    // Test case for DataUtilities.createNumberArray2D with one column
    @Test
    public void testCreateNumberArray2dWithOneColumn() {
        double[][] list = new double[10][1];
        for (int i = 0; i < 10; i++) {
            list[i][0] = i * 1.1;
        }
        Number[][] generated_list = DataUtilities.createNumberArray2D(list);
        assertEquals("The length of list column should be 10", generated_list.length, 10);
        assertEquals("The length of list should be 1", generated_list[0].length, 1);
        for (int i = 0; i < 10; i++) {
            assertEquals("All the values in input and generated list should be equal", list[i][0], generated_list[i][0]);
        }
    }
    
    
    
    
    
    // Test cases for getCumulativePercentage:
    @Test
    public void testValidInput() {
        // Creating a dataset with positive values
        DefaultKeyedValues data = new DefaultKeyedValues();
        data.addValue("0", 5.0);
        data.addValue("1", 9.0);
        data.addValue("2", 2.0);
        
        // Testing cumulative percentages calculation
        KeyedValues result = DataUtilities.getCumulativePercentages(data);
        
        // Assertions for each value's cumulative percentage
        assertEquals(0.3125, (double) result.getValue("0"), 0.0001);
        assertEquals(0.875, (double) result.getValue("1"), 0.0001);
        assertEquals(1.0, (double) result.getValue("2"), 0.0001);
    }

    @Test
    public void testEmptyInput() {
        // Creating an empty dataset
        DefaultKeyedValues data = new DefaultKeyedValues();
        
        // Testing behavior with an empty dataset
        KeyedValues result = DataUtilities.getCumulativePercentages(data);
        
        // Verifying that the result contains no items
        assertEquals(0, result.getItemCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullInput() {
        // Testing behavior with null input (expecting an IllegalArgumentException)
        DataUtilities.getCumulativePercentages(null);
    }

    @Test
    public void testNegativeValuesInput() {
        // Creating a dataset with negative values
        DefaultKeyedValues data = new DefaultKeyedValues();
        data.addValue("0", -5.0);
        data.addValue("1", -9.0);
        data.addValue("2", -2.0);
    
        // Testing cumulative percentages calculation with negative values
        KeyedValues result = DataUtilities.getCumulativePercentages(data);
    
        // Verifying the cumulative percentage for one of the negative values
        assertEquals(1.0, (double) result.getValue("2"), 0.0001);   // Cumulative percentage for -2.0
    }
    
    @Test
    public void testLargeValuesInput() {
        // Creating a dataset with large positive values
        DefaultKeyedValues data = new DefaultKeyedValues();
        data.addValue("0", 1000000.0); // Example large value
        data.addValue("1", 2000000.0); // Example large value
        data.addValue("2", 3000000.0); // Example large value
    
        // Testing cumulative percentages calculation with large values
        KeyedValues result = DataUtilities.getCumulativePercentages(data);
    
        // Verifying cumulative percentages for each large value
        assertEquals(0.16666666666666666, (double) result.getValue("0"), 0.0001); // Cumulative percentage for 1000000.0
        assertEquals(0.5, (double) result.getValue("1"), 0.0001);                  // Cumulative percentage for 2000000.0
        assertEquals(1.0, (double) result.getValue("2"), 0.0001);                  // Cumulative percentage for 3000000.0
    }
}


