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

@Repository
class PipelineData : PipelineDataSource {
    /**
     * This is a function to read the dataset file.
     * @author Feiqiong DENG
     * @param list The ArrayList input for the function and will be populated by the records of the dataset file.
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
            return allRecords
        } else {
             return getAllRecords()
        }
    }

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

    override fun writeAllData(list: Collection<PipelineRecord>) {
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

    override fun showResults(name: String): String {
        return """
				<b>$name:</b>
				<p>Program by: Feiqiong DENG</p>
	            <a href="http://localhost:8088/">BACK</a>
                <br>
                <br>
				""".trimIndent()
    }

    override fun search(): String {
        return """
				<b>Please enter a keyword to search:</b>
				<br>
				<br>
				<table>
        		<tr><td><input type="text" onblur="getVal()"/></td></tr>
                <tr><td><button id="myBtn">Search</button></td></tr>
    			</table>
	            <br>
	            <a href="http://localhost:8088/">BACK</a>
				<br>
				<p>Program by: Feiqiong DENG</p>
                <script>
                    function getVal()  {
                     return document.querySelector('input').value;
                    }
                     document.getElementById("myBtn").addEventListener("click", myFunction);
                     function myFunction() {
                         window.location.href="http://localhost:8088/result/"+getVal();
                      }
                </script>
				""".trimIndent()
    }

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
}