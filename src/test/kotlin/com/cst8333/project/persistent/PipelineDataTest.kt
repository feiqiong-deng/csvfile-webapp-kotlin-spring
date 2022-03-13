package com.cst8333.project.persistent

import com.cst8333.project.model.PipelineRecord
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * This is the unit testing class using JUnit5 framework.
 * @author Feiqiong Deng
 * There are two unit tests to test the adding and deleting functionalities.
 */
internal class PipelineDataTest {

    /**
     * There are three test data.
     * The testData is a PipelineData object of the persistent layer.
     * The testPipelineRecord is a PipelineRecord representing one pipeline record used for testing.
     * The expected is a PipelineRecord to be compared with the testPipelineRecord with all properties of the same values.
     */
    private val testData = PipelineData()

    private val testPipelineRecord = PipelineRecord("SN098","Release of Substance",
        "02/02/2022", "Test", "Ontario","ABC", "substance",
    "No", "Sweet")

    private val expected =  PipelineRecord("SN098","Release of Substance",
        "02/02/2022", "Test", "Ontario","ABC", "substance",
        "No", "Sweet")

    /**
     * This is the unit test to test adding one record method.
     * The function firstly counts the number of records in the datafile before adding a new record.
     * Then the function to make sure the original dataset does not contain record either testPipelineRecord or expected record.
     * After adding the testPipelineRecord, the function test whether the total number increased by one.
     * Also, the function will test whether the newly added record added correctly.
     * Then the test data will be deleted.
     */
    @Test
    fun testAddOneRecord() {
        val sizeBeforeAdd = testData.getAllRecords().size

        assertFalse(testData.getAllRecords().contains(testPipelineRecord))
        assertFalse(testData.getAllRecords().contains(expected))

        testData.addOneRecord(testPipelineRecord)
        val sizeAfterAdd = testData.getAllRecords().size
        val searchNewAdded = testData.getSearchResults("SN098").first()
        val sizeDifference = sizeAfterAdd - sizeBeforeAdd
        assertEquals(1, sizeDifference)
        assertThat(searchNewAdded).isEqualTo(expected)
        testData.deleteOneRecord("SN098")
    }

    /**
     * This is the unit test to test deleting one record method.
     * The function firstly counts the number of records in the datafile before deleting a record.
     * Then the function add a test data to the datafile.
     * After adding the testPipelineRecord, the function test whether the total number increased by one.
     * Then function delete the test data.
     * The function will then compare the size of the list before and after deleting the record.
     * Finally, the function search the deleted record to make sure the datafile does not contain the deleted record.
     */
    @Test
    fun testDeleteOneRecord() {
        val sizeBeforeDelete = testData.getAllRecords().size
        testData.addOneRecord(testPipelineRecord)
        val sizeAfterAdd = testData.getAllRecords().size
        var sizeDifference = sizeAfterAdd - sizeBeforeDelete

        assertEquals(1, sizeDifference)

        val searchRecord = testData.getSearchResults("SN098").first()

        testData.deleteOneRecord("SN098")
        val sizeAfterDelete = testData.getAllRecords().size
        sizeDifference = sizeBeforeDelete - sizeAfterDelete
        assertEquals(0, sizeDifference)
        assertFalse(testData.getAllRecords().contains(searchRecord))
    }


}