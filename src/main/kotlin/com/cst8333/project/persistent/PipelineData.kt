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
            writeAllData(allRecords)
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

    override fun addOneRecord(newRecord: PipelineRecord) {
        var list = ArrayList<PipelineRecord>()
        list.addAll(getAllRecords())
        list.add(newRecord)
        writeAllData(list)
    }

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

    override fun deleteOneRecord(number: String) {
        var list = ArrayList<PipelineRecord>()
        list.addAll(getAllRecords())
        val search = getSearchResults(number)
        var delete = search.first()
        list.remove(delete)
        writeAllData(list)
    }

    override fun showResults(name: String): String {
        return """
				<h3>$name:</h3>
				<p>Program by: Feiqiong DENG</p>
	            <a href="http://localhost:8088/">BACK</a>
                <br>
                <br>
				""".trimIndent()
    }

    override fun search(keyword: String): String {
        return """
				<b>Please enter a $keyword to search:</b>
				<br>
				<br>
				<table>
        		<tr><td><input type="text" onblur="getVal()"/></td></tr>
                <tr><td><button id="myBtn">View</button></td></tr>
                <tr><td><button id="edit">Edit</button></td></tr>
                <tr><td><button id="delete">Delete</button></td></tr>
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
                      document.getElementById("edit").addEventListener("click", editFunction);
                     function editFunction() {
                         window.location.href="http://localhost:8088/edit/"+getVal();
                      }
                      document.getElementById("delete").addEventListener("click", deleteFunction);
                     function deleteFunction() {
                         window.location.href="http://localhost:8088/delete/"+getVal();
                      }
                </script>
				""".trimIndent()
    }

    override fun addPage(): String {
        return """
            	<b>Please enter a new record:</b>
				<br>
				<br>
				<table>
        		<tr><td>Number: </td><td><input id="num" type="text" onblur="getInput(\""+ num+ "\")"/></td></tr>
                <tr><td>Type: </td><td><input id="type" type="text" onblur="getInput(\""+ type+ "\")"/></td></tr>
        		<tr><td>Date: </td><td><input id="date" type="text" onblur="getInput(\""+ date+ "\")"/></td></tr>
        		<tr><td>Center: </td><td><input id="center" type="text" onblur="getInput(\""+ center+ "\")"/></td></tr>
        		<tr><td>Province: </td><td><input id="province" type="text" onblur="getInput(\""+ province+ "\")"/></td></tr>
        		<tr><td>Company: </td><td><input id="company" type="text" onblur="getInput(\""+ company+ "\")"/></td></tr>
        		<tr><td>Substance: </td><td><input id="substance" type="text" onblur="getInput(\""+ substance+ "\")"/></td></tr>
        		<tr><td>Significant: </td><td><input id="significant" type="text" onblur="getInput(\""+ significant+ "\")"/></td></tr>
        		<tr><td>Category: </td><td><input id="category" type="text" onblur="getInput(\""+ category+ "\")"/></td></tr>                   
                <tr><td><button id="add">Add</button></td></tr>
    			</table>
	            <br>
	            <a href="http://localhost:8088/">BACK</a>
				<br>
				<p>Program by: Feiqiong DENG</p>
                <script>
                    function getInput(input)  {
                     return document.getElementById(input).value;
                    }
            
                     document.getElementById("add").addEventListener("click", myFunction);
                     function myFunction() {
                         window.location.href="http://localhost:8088/add/" + getInput("num") + "/" + getInput("type")
                          + "/" + getInput("date") + "/"  + getInput("center") + "/"  + getInput("province")
                          + "/" + getInput("company") + "/"  + getInput("substance")
                          + "/" + getInput("significant") + "/"  + getInput("category");
                      }
                </script>
            """
    }

    override fun editPage(editRecord: PipelineRecord): String {
        return """
            	<b>Edit / Delete the record:</b>
				<br>
				<br>
				<table>
        		<tr><td>Number: </td><td><input id="num" type="text" placeholder="${editRecord.number}" onblur="getInput(\""+ num+ "\")"/></td></tr>
                <tr><td>Type: </td><td><input id="type" type="text" placeholder="${editRecord.type}" onblur="getInput(\""+ type+ "\")"/></td></tr>
        		<tr><td>Date: </td><td><input id="date" type="text" placeholder="${editRecord.date}" onblur="getInput(\""+ date+ "\")"/></td></tr>
        		<tr><td>Center: </td><td><input id="center" type="text" placeholder="${editRecord.centre}" onblur="getInput(\""+ center+ "\")"/></td></tr>
        		<tr><td>Province: </td><td><input id="province" type="text" placeholder="${editRecord.province}" onblur="getInput(\""+ province+ "\")"/></td></tr>
        		<tr><td>Company: </td><td><input id="company" type="text" placeholder="${editRecord.company}" onblur="getInput(\""+ company+ "\")"/></td></tr>
        		<tr><td>Substance: </td><td><input id="substance" type="text" placeholder="${editRecord.substance}" onblur="getInput(\""+ substance+ "\")"/></td></tr>
        		<tr><td>Significant: </td><td><input id="significant" type="text" placeholder="${editRecord.significant}" onblur="getInput(\""+ significant+ "\")"/></td></tr>
        		<tr><td>Category: </td><td><input id="category" type="text" placeholder="${editRecord.category}" onblur="getInput(\""+ category+ "\")"/></td></tr>                   
                <tr><td><button id="edit">Edit</button></td></tr>
                <tr><td><button id="delete">Delete</button></td></tr>
    			</table>
	            <br>
	            <a href="http://localhost:8088/">BACK</a>
				<br>
				<p>Program by: Feiqiong DENG</p>
                <script>
                    function getInput(input) {
                     return document.getElementById(input).value;
                    }
                            
                     document.getElementById("edit").addEventListener("click", myFunction);
                     function myFunction() {
                         window.location.href="http://localhost:8088/edit/" + getInput("num") + "/" + getInput("type")
                          + "/" + getInput("date") + "/"  + getInput("center") + "/"  + getInput("province")
                          + "/" + getInput("company") + "/"  + getInput("substance")
                          + "/" + getInput("significant") + "/"  + getInput("category") + "/" +"${editRecord.number}";
                      }
                      
                     document.getElementById("delete").addEventListener("click", deleteFunction);
                     function deleteFunction() {
                         window.location.href="http://localhost:8088/deleteOne/" + "${editRecord.number}";
                      }
                </script>
            """
    }
}