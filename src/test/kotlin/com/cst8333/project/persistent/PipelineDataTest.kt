package com.cst8333.project.persistent

import com.cst8333.project.model.PipelineRecord
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PipelineDataTest {

    //test data
    private val testData = PipelineData()

    private val testPipelineRecord = PipelineRecord("SN098","Release of Substance",
        "02/02/2022", "Test", "Ontario","ABC", "substance",
    "No", "Sweet")

    private val expected =  PipelineRecord("SN098","Release of Substance",
        "02/02/2022", "Test", "Ontario","ABC", "substance",
        "No", "Sweet")

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