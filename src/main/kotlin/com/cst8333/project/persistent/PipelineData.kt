package com.cst8333.project.persistent

import com.cst8333.project.model.PipelineRecord
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.stereotype.Repository
import java.io.IOException
import java.nio.file.Paths
import kotlin.io.path.bufferedReader

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
        /**
         * Locate the csv file and use bufferedReader to read the file.
         * Initialize the CSVParser instance and set to ignore the header row, case-insensitive and trim the records.
         */
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
    }

    override fun showAll(): String {
        return """
				<b>All Records:</b>
				<p>Program by: Feiqiong DENG</p>
	            <a href="http://localhost:8088/">BACK</a>
                <br>
                <br>
				""".trimIndent()
    }


}