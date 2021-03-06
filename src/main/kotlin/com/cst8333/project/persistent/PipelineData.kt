package com.cst8333.project.persistent

import com.cst8333.project.model.PipelineRecord
import org.apache.catalina.valves.rewrite.InternalRewriteMap.LowerCase
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVRecord
import org.springframework.stereotype.Repository
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList
import kotlin.io.path.Path
import kotlin.io.path.bufferedReader
import kotlin.io.path.exists

/**
 * This is a class to implement the interface PipelineDataSource class.
 * @author Feiqiong Deng
 * This class has all methods to interact with the pipeline data and implements all methods from the interface.
 * There are methods of reading the csv file and getting all records from the file, searching specific records,
 * adding one new record to the file, deleting one record from the file and updating records.
 */
@Repository
class PipelineData : PipelineDataSource {
    /**
     * This is a function to read the dataset file.
     * This function is firstly to read the csv file in the resource folder by using bufferedReader.
     * CSVParser is from the Apache Commons library and can handle reading and writing csv file in Kotlin.
     * Getting the values of specific columns and stored in PipelineRecord object.
     * Each PipelineRecord object is added to the list.
     */
    override fun getAll(): Collection<PipelineRecord> {
        var list = ArrayList<PipelineRecord>()
        val newFile = Path("src/main/resources/newFile.csv")
        /**
         * Locate the csv file and use bufferedReader to read the file.
         * Initialize the CSVParser instance and set to ignore the header row, case-insensitive and trim the records.
         */
        if(!newFile.exists()) {
            try {
                val reader = Paths.get("src/main/resources/pipeline-incidents-comprehensive-data.csv").bufferedReader()
                val parser = CSVParser(
                    reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
                        .withIgnoreHeaderCase().withTrim()
                )
                /**
                 * Iterate over the parser instance and extract records specific columns value from it.
                 * Column records values are transferred and stored in a PipelineRecord object.
                 * Each PipelineRecord object is added to the arraylist.
                 * The first 100 records will be stored in a new file.
                 */
                try {
                    for (record in parser) {
                        val number = record.get(0)
                        val type = record.get(1)
                        val date = record.get(2)
                        val centre = record.get(3)
                        val province = record.get(4)
                        val company = record.get(5)
                        val substance = record.get(10)
                        val significant = record.get(12)
                        val category = record.get(17)

                        list.add(
                            PipelineRecord(
                                number,
                                type,
                                date,
                                centre,
                                province,
                                company,
                                substance,
                                significant,
                                category
                            )
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var allRecords = ArrayList<PipelineRecord>()
            for (i in 0..99) {
                allRecords.add(list[i])
            }
            writeAllData(allRecords)
            return allRecords
        } else {
             return getAllRecords()
        }
    }

    /**
     * This is a function to use CSVParser to read a file to get all records from the file.
     * This function is firstly to read the csv file in the resource folder by using bufferedReader.
     * Getting the values of specific columns and stored in PipelineRecord object.
     * Each PipelineRecord object is added to the list and the function return a list of pipeline objects.
     */
    fun getAllRecords(): Collection<PipelineRecord>{
        var list = ArrayList<PipelineRecord>()
        try {
            val reader = Paths.get("src/main/resources/newFile.csv").bufferedReader()
            val parser = CSVParser(
                reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
                    .withIgnoreHeaderCase().withTrim()
            )
            /**
             * Iterate over the parser instance and extract records specific columns value from it.
             * Column records values are transferred and stored in a PipelineRecord object.
             * Each PipelineRecord object is added to the arraylist.
             */
            try {
                for (record in parser) {
                    assignRecord(record, list)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return list
    }

    /**
     * This is a function to use getAllRecords() to get all records from the file.
     * List is a basic data structure in Kotlin and is mutable data structure.
     * Getting the values of the sorted criterion.
     * @param sortBy The input sorting criterion from a user.
     * Use the built-in sorted API to sort records by a specific column.
     */
    override fun getSortedResults(sortBy: String): Collection<PipelineRecord>{
        var list = getAllRecords()

        when (sortBy) {
            "a" -> list = list.sortedBy { it.number }
            "b" -> list = list.sortedBy { it.type }
            "c" -> list = list.sortedBy { it.date }
            "d" -> list = list.sortedBy { it.centre }
            "e" -> list = list.sortedBy { it.province }
            "f" -> list = list.sortedBy { it.company }
            "g" -> list = list.sortedBy { it.substance }
            "h" -> list = list.sortedBy { it.significant }
            "i" -> list = list.sortedBy { it.category }
        }
        return list
    }

    /**
     * This is a function to use getResultsByColumns() to get all records based
     * on multiple columns at same time from the file.
     * @param columns The input searching criterion from a user.
     * This function firstly get all records from dataset and create a list of PipelineData objects.
     * Then getting each property of an object to compare whether each property contains the search
     * keyword for the column. If the object's properties match the user search keywords for
     * corresponding fields, the object will be added to the list of results.
     */
    override fun getResultsByColumns(columns: MutableMap<String, String>): Collection<PipelineRecord> {
        var allRecords = getAllRecords()
        var list = ArrayList<PipelineRecord>()
            for (record in allRecords) {
                val number = formatString(record.number)
                val type = formatString(record.type)
                val date = formatString(record.date)
                val centre = formatString(record.centre)
                val province = formatString(record.province)
                val company = formatString(record.company)
                val substance = formatString(record.substance)
                val significant = formatString(record.significant)
                val category = formatString(record.category)

                var result = 0

                columns.keys.forEach {
                    val value = columns[it] as String
                    when (it) {
                        "number" -> result += checkMatch(value, number)
                        "type" -> result += checkMatch(value, type)
                        "date" -> result += checkMatch(value, date)
                        "center" -> result += checkMatch(value, centre)
                        "province" -> result += checkMatch(value, province)
                        "company" -> result += checkMatch(value, company)
                        "substance" -> result += checkMatch(value, substance)
                        "significant" -> result += checkMatch(value, significant)
                        "category" -> result += checkMatch(value, category)
                    }
                }

                if (result == columns.size){
                    list.add(record)
                }
        }
        return list
    }

    /**
     * This is a function to check whether the Pipeline object's properties contains the search keyword.
     * @param match The user input keyword.
     * @param value The value of the corresponding column of a record.
     * If the column value contains the search keyword, 1 will be returned.
     */
    fun checkMatch(match: String, value: String): Int {
        var result = 0
        if(value.contains(match)) {
            result += 1
        }
        return result
    }

    /**
     * This function is to format the string to remove spaces between strings and make every letter to lowercase.
     * @param string The string to be formatted.
     */
    fun formatString(string: String): String {
        return string.lowercase().replace("\\s".toRegex(), "")
    }

    /**
     * This is a function to transfer the data from the file to pipeline object.
     * @param record The CSVRecord input reading data from a file.
     * @param list The ArrayList input for the function and will be populated by the records of the dataset file.
     */
    fun assignRecord(record: CSVRecord, list: ArrayList<PipelineRecord>) {
        val number = record.get(0)
        val type = record.get(1)
        val date = record.get(2)
        val centre = record.get(3)
        val province = record.get(4)
        val company = record.get(5)
        val substance = record.get(6)
        val significant = record.get(7)
        val category = record.get(8)

        list.add(
            PipelineRecord(
                number,
                type,
                date,
                centre,
                province,
                company,
                substance,
                significant,
                category
            )
        )
    }

    /**
     * This is a function to write data to the datafile.
     * @param list The Collection of pipeline objects input to write to the datafile.
     * It uses the CSVPrinter to write data to the datafile.
     * The CSVPrinter formats the first row is header and will write each pipeline object to each row of data.
     */
     fun writeAllData(list: Collection<PipelineRecord>) {
        val newFile = "src/main/resources/newFile.csv"
        val writer = Files.newBufferedWriter(Paths.get(newFile))
        val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT
            .withHeader("Incident Number", "Incident Types", "Reported Date", "Nearest Populated Centre",
                        "Province", "Company", "Substance", "Significant", "What happened category"))
         try{
            for(eachRecord in list) {
                csvPrinter.printRecord(eachRecord.number, eachRecord.type, eachRecord.date, eachRecord.centre,
                    eachRecord.province, eachRecord.company, eachRecord.substance, eachRecord.significant,
                    eachRecord.category)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            csvPrinter.flush()
            csvPrinter.close()
        }
    }

    /**
     * This method implements the function from the interface.
     * @param search This is a string input to search in the datafile.
     * CSVParser will read all data in the datafile and if records contain the string input.
     * The records will be added into a list and returned a list of records containing the search keyword.
     */
    override fun getSearchResults(search: String): Collection<PipelineRecord> {
        var list = ArrayList<PipelineRecord>()
        /**
         * Locate the csv file and use bufferedReader to read the file.
         * Initialize the CSVParser instance and set to ignore the header row, case-insensitive and trim the records.
         */
        try {
            val reader = Paths.get("src/main/resources/newFile.csv").bufferedReader()
            val parser = CSVParser(
                reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
                    .withIgnoreHeaderCase().withTrim()
            )

            /**
             * Iterate over the parser instance and extract records specific columns value from it.
             * Column records values are transferred and stored in a PipelineRecord object.
             * Each PipelineRecord object is added to the arraylist.
             */
            try {
                for (record in parser) {
                    val number = record.get(0)
                    val type = record.get(1)
                    val date = record.get(2)
                    val centre = record.get(3)
                    val province = record.get(4)
                    val company = record.get(5)
                    val substance = record.get(6)
                    val significant = record.get(7)
                    val category = record.get(8)

                    /**
                     * This is to specify the conditions to get the records.
                     * Either property of the pipeline object contains the search word will be added to the list.
                     */
                    if(number.contains(search) || type.contains(search) || date.contains(search) ||
                            centre.contains(search) || province.contains(search) ||
                        company.contains(search) || substance.contains(search) ||
                            significant.contains(search) || category.contains(search)) {

                        list.add(
                            PipelineRecord(
                                number,
                                type,
                                date,
                                centre,
                                province,
                                company,
                                substance,
                                significant,
                                category
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return list
    }

    /**
     * This method implements the function addOneRecord from the interface.
     * @param newRecord This is an input of pipeline object to be added to the datafile.
     * The new pipeline object will be added to the list of all records.
     * Then the new record will be written to the datafile.
     */
    override fun addOneRecord(newRecord: PipelineRecord) {
        var list = ArrayList<PipelineRecord>()
        list.addAll(getAllRecords())
        list.add(newRecord)
        writeAllData(list)
    }

    /**
     * This method implements the function editOneRecord from the interface.
     * @param newRecord This is an input of pipeline object to be edited.
     * @param number This is an input to get the record from the datafile to be updated.
     * The function will search the record is edited and then reassign the edited record to replace it.
     * Then the updated record is written to the datafile.
     */
    override fun editOneRecord(newRecord: PipelineRecord, number: String) {
        var list = ArrayList<PipelineRecord>()
        val search = getSearchResults(number)
        var edit = search.first()
        var original = getAllRecords()
        list.addAll(getAllRecords())
        val index = original.indexOf(edit)
        list[index] = newRecord
        writeAllData(list)
    }

    /**
     * This method implements the function deleteOneRecord from the interface.
     * @param number This is an input to get the record from the datafile to be deleted.
     * The function will search the record to be deleted from the datafile.
     * Then the record is removed from the list.
     * All records after deletion will be written to the datafile.
     */
    override fun deleteOneRecord(number: String) {
        var list = ArrayList<PipelineRecord>()
        list.addAll(getAllRecords())
        val search = getSearchResults(number)
        var delete = search.first()
        list.remove(delete)
        writeAllData(list)
    }
}