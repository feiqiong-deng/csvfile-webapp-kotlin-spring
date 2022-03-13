package com.cst8333.project.persistent

import com.cst8333.project.model.PipelineRecord
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVRecord
import org.springframework.stereotype.Repository
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
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