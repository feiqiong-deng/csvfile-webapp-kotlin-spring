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

    /**
     * This is the unit test to test sorting records method.
     * The function firstly sort the records by each column, total 9 columns, one by one.
     * Then the function retrieve all data of the sorted column to separate lists to be tested.
     * The function retrieve all data of each column to separate lists before sorting.
     * Each list of data for each column then is sorted by ascending order to be the lists of expected results.
     * The function will then compare every element in each list of column after getSortedResults() with expected results.
     */
    @Test
    fun testSortList() {
        val sortByNumber = testData.getSortedResults("a")
        val sortByType = testData.getSortedResults("b")
        val sortByDate = testData.getSortedResults("c")
        val sortByCenter = testData.getSortedResults("d")
        val sortByProvince = testData.getSortedResults("e")
        val sortByCompany = testData.getSortedResults("f")
        val sortBySubstance = testData.getSortedResults("g")
        val sortBySignificant = testData.getSortedResults("h")
        val sortByCategory = testData.getSortedResults("i")

        val listNumber = ArrayList<String>()
        val listType = ArrayList<String>()
        val listDate = ArrayList<String>()
        val listCenter = ArrayList<String>()
        val listProvince = ArrayList<String>()
        val listCompany = ArrayList<String>()
        val listSubstance = ArrayList<String>()
        val listSignificant = ArrayList<String>()
        val listCategory  = ArrayList<String>()

        val expectedNumList = ArrayList<String>()
        val expectedTypeList = ArrayList<String>()
        val expectedDateList = ArrayList<String>()
        val expectedCenterList = ArrayList<String>()
        val expectedProvinceList = ArrayList<String>()
        val expectedCompanyList = ArrayList<String>()
        val expectedSubsList = ArrayList<String>()
        val expectedSignificanceList = ArrayList<String>()
        val expectedCategoryList = ArrayList<String>()

        sortByNumber.forEach {
            listNumber.add(it.number)
        }
        sortByType.forEach {
            listType.add(it.type)
        }
        sortByDate.forEach {
            listDate.add(it.date)
        }
        sortByCenter.forEach {
            listCenter.add(it.centre)
        }
        sortByProvince.forEach {
            listProvince.add(it.province)
        }
        sortByCompany.forEach {
            listCompany.add(it.company)
        }
        sortBySubstance.forEach {
            listSubstance.add(it.substance)
        }
        sortBySignificant.forEach {
            listSignificant.add(it.significant)
        }
        sortByCategory.forEach {
            listCategory.add(it.category)
        }

        testData.getAllRecords().forEach {
            expectedNumList.add(it.number)
        }
        testData.getAllRecords().forEach {
            expectedTypeList.add(it.type)
        }
        testData.getAllRecords().forEach {
            expectedDateList.add(it.date)
        }
        testData.getAllRecords().forEach {
            expectedCenterList.add(it.centre)
        }
        testData.getAllRecords().forEach {
            expectedProvinceList.add(it.province)
        }
        testData.getAllRecords().forEach {
            expectedCompanyList.add(it.company)
        }
        testData.getAllRecords().forEach {
            expectedSubsList.add(it.substance)
        }
        testData.getAllRecords().forEach {
            expectedSignificanceList.add(it.significant)
        }
        testData.getAllRecords().forEach {
            expectedCategoryList.add(it.category)
        }

        assertThat(listNumber).isEqualTo(expectedNumList.sorted())
        assertThat(listType).isEqualTo(expectedTypeList.sorted())
        assertThat(listDate).isEqualTo(expectedDateList.sorted())
        assertThat(listCenter).isEqualTo(expectedCenterList.sorted())
        assertThat(listProvince).isEqualTo(expectedProvinceList.sorted())
        assertThat(listCompany).isEqualTo(expectedCompanyList.sorted())
        assertThat(listSubstance).isEqualTo(expectedSubsList.sorted())
        assertThat(listSignificant).isEqualTo(expectedSignificanceList.sorted())
        assertThat(listCategory).isEqualTo(expectedCategoryList.sorted())
    }
}

