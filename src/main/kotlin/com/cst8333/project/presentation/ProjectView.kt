package com.cst8333.project.presentation

import com.cst8333.project.business.PipelineService
import com.cst8333.project.model.PipelineRecord
import org.springframework.web.bind.annotation.*

@RestController
class ProjectView(val service: PipelineService) {
    @RequestMapping("/a")
    fun showAllRecords(): String {
//        var result = service.showResults("All Records")
        var result = showResults("All Records")
        val count = service.getAllRecords().size

        service.getAllRecords().forEach {
            result += it.number + ", " + it.type + ", " + it.date + ", " +
                    it.centre + ", " + it.province + ", " + it.company + ", " +
                    it.substance + ", " + it.significant + ", " + it.category + "<br>"
        }
        result += "<br><b>Total records: $count</b>"
        return result
    }

    @RequestMapping("/b")
    fun oneRecord(): String {
//        return service.search("keyword")
        return search("keyword")

    }

    @RequestMapping("/result/{search}")
    fun showSearchRecords(@PathVariable("search") search: String): String {
//        var result = service.showResults("Search Results")
        var result = showResults("Search Results")
        val count = service.getSearchResults("$search").size
        service.getSearchResults("$search").forEach {
            result += it.number + ", " + it.type + ", " + it.date + ", " +
                    it.centre + ", " + it.province + ", " + it.company + ", " +
                    it.substance + ", " + it.significant + ", " + it.category + "<br>"
        }
        result += "<br><b>Total records: $count</b>"
        return result
    }

    @RequestMapping("/c")
    fun addRecord(): String {
//        return service.addPage()
        return addPage()
    }

    @RequestMapping("/add/{a}/{b}/{mm}/{dd}/{yy}/{d}/{e}/{f}/{g}/{h}/{i}")
    fun addOneRecord(@PathVariable("a") a: String, @PathVariable("b") b: String, @PathVariable("mm") mm: String,
                     @PathVariable("dd") dd: String, @PathVariable("yy") yy: String, @PathVariable("d") d: String,
                     @PathVariable("e") e: String,@PathVariable("f") f: String, @PathVariable("g") g: String,
                     @PathVariable("h") h: String, @PathVariable("i") i: String): String {

        val oneRecord = PipelineRecord("$a", "$b", "$mm/$dd/$yy", "$d", "$e",
                                "$f", "$g", "$h", "$i")

        service.addOneRecord(oneRecord)

        return messages("added")
    }

    @RequestMapping("/d")
    fun editRecord(): String {
//        return service.search("Incident Number")
        return search("Incident Number")
    }

    @RequestMapping("/e")
    fun deleteRecord(): String {
//        return service.search("Incident Number")
        return search("Incident Number")
    }

    @RequestMapping("/edit/{search}")
    fun showEditRecords(@PathVariable("search") search: String): String {
        val record = service.getSearchResults("$search")
//        return service.editPage(record.first())
        return editPage(record.first())

    }

    @RequestMapping("/delete/{search}")
    fun showDeleteRecords(@PathVariable("search") search: String): String {
        val record = service.getSearchResults("$search")
//        return service.editPage(record.first())
        return editPage(record.first())
    }

    @RequestMapping("/edit/{a}/{b}/{mm}/{dd}/{yy}/{d}/{e}/{f}/{g}/{h}/{i}/{j}")
    fun ediTOneRecord(@PathVariable("a") a: String, @PathVariable("b") b: String, @PathVariable("mm") mm: String,
                     @PathVariable("dd") dd: String, @PathVariable("yy") yy: String, @PathVariable("d") d: String,
                     @PathVariable("e") e: String,@PathVariable("f") f: String, @PathVariable("g") g: String,
                     @PathVariable("h") h: String, @PathVariable("i") i: String, @PathVariable("j") j: String): String {

        val oneRecord = PipelineRecord("$a", "$b", "$mm/$dd/$yy", "$d", "$e",
            "$f", "$g", "$h", "$i")

        service.editOneRecord(oneRecord, "$j")

        return messages("updated")
    }

    @RequestMapping("/deleteOne/{delete}")
    fun deleteRecords(@PathVariable("delete") delete: String): String {
        val record = service.deleteOneRecord("$delete")
        return messages("deleted")
    }

    fun messages(message: String): String {
        return """
               <h4>The record is already $message.</h4>
               <a href="http://localhost:8088/">BACK</a>
               <br>
               <b>Program by: Feiqiong DENG</b>
               """
    }

     fun showResults(name: String): String {
        return """
				<h3>$name:</h3>
				<p>Program by: Feiqiong DENG</p>
	            <a href="http://localhost:8088/">BACK</a>
                <br>
                <br>
				""".trimIndent()
    }

     fun search(keyword: String): String {
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

     fun addPage(): String {
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

     fun editPage(editRecord: PipelineRecord): String {
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

