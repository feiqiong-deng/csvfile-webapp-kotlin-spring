package com.cst8333.project.presentation

import com.cst8333.project.business.PipelineService
import com.cst8333.project.model.PipelineRecord
import org.springframework.web.bind.annotation.*
import java.rmi.Naming.list

/**
 * This is a class of presentation layer to interact with users.
 * @author Feiqiong Deng
 * @param service The input of PipelineService object from the service layer.
 * This class include functions to show the view in web browser to the users.
 * It also gets the input from the users and presents information to the users.
 * Most functions is this class are using RequestMapping annotation to map web requests.
 */
@RestController
class ProjectView(val service: PipelineService) {

    /**
     * This is a function to show all records.
     * The function will use the service object to call the getAllRecords to get all data.
     * It will use html format to show all records to users.
     */
    @RequestMapping("/a")
    fun showAllRecords(): String {
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

    /**
     * This is a function to navigate to the sort page.
     * The function will use the service object to call the getAllRecords to get all data.
     * It will use html format to show all records to users.
     */
    @RequestMapping("/f")
    fun sortPage(): String {
        return sort()
    }

    /**
     * This is a function to present the page of showing the search results to users.
     * @param sortBy It is the input criterion to sort records from the users.
     * It will use call the getSortedResults method from the business layer.
     * Then it will use html format to show all sorted results to users.
     */
    @RequestMapping("/sort/{sortBy}")
    fun showSortedRecords(@PathVariable("sortBy") sortBy: String): String {
        var result = showResults("Results after sort")
        val count = service.getSortedResults("$sortBy").size
        service.getSortedResults("$sortBy").forEach {

            when (sortBy) {
                "a" ->  result += it.number + ", " + it.type + ", " + it.date + ", " +
                        it.centre + ", " + it.province + ", " + it.company + ", " +
                        it.substance + ", " + it.significant + ", " + it.category + "<br>"
                "b" ->  result += it.type + ", " + it.number + ", " + it.date + ", " +
                        it.centre + ", " + it.province + ", " + it.company + ", " +
                        it.substance + ", " + it.significant + ", " + it.category + "<br>"
                "c" ->  result += it.date + ", " + it.number + ", " + it.type + ", " +
                        it.centre + ", " + it.province + ", " + it.company + ", " +
                        it.substance + ", " + it.significant + ", " + it.category + "<br>"
                "d" ->  result += it.centre + ", " + it.number + ", " + it.type + ", " + it.date + ", " +
                        it.province + ", " + it.company + ", " +
                        it.substance + ", " + it.significant + ", " + it.category + "<br>"
                "e" ->  result +=  it.province + ", " + it.number + ", " + it.type + ", " + it.date + ", " +
                        it.centre + ", " + it.company + ", " +
                        it.substance + ", " + it.significant + ", " + it.category + "<br>"
                "f" ->  result += it.company + ", " + it.number + ", " + it.type + ", " + it.date + ", " +
                        it.centre + ", " + it.province + ", " +
                        it.substance + ", " + it.significant + ", " + it.category + "<br>"
                "g" ->  result += it.substance + ", " + it.number + ", " + it.type + ", " + it.date + ", " +
                        it.centre + ", " + it.province + ", " + it.company + ", " +
                        it.significant + ", " + it.category + "<br>"
                "h" ->  result += it.significant + ", " + it.number + ", " + it.type + ", " + it.date + ", " +
                        it.centre + ", " + it.province + ", " + it.company + ", " +
                        it.substance + ", " + it.category + "<br>"
                "i" ->  result += it.category + it.number + ", " + it.type + ", " + it.date + ", " +
                        it.centre + ", " + it.province + ", " + it.company + ", " +
                        it.substance + ", " + it.significant + ", " +  "<br>"
            }

        }
        result += "<br><b>Total records: $count</b>"
        return result
    }

    /**
     * This is a function to present search page to users.
     */
    @RequestMapping("/b")
    fun oneRecord(): String {
        return search("keyword")
    }

    /**
     * This is a function to present the page of showing the search results to users.
     * @param search It is the input of search keyword from the users.
     * It will use call the search method from the business layer.
     * Then it will use html format to show all search results to users.
     */
    @RequestMapping("/result/{search}")
    fun showSearchRecords(@PathVariable("search") search: String): String {
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

    /**
     * This is the method is to map to the show users the adding record page.
     */
    @RequestMapping("/c")
    fun addRecord(): String {
        return addPage()
    }

    /**
     * This is the method to map to the get the adding record input data from users.
     * @param a The input of incident number from the user.
     * @param b The input of incident type from the user.
     * @param mm The input of incident month from the user.
     * @param dd The input of incident day from the user.
     * @param yy The input of incident year from the user.
     * @param d The input of center from the user.
     * @param e The input of province from the user.
     * @param f The input of company from the user.
     * @param g The input of substance from the user.
     * @param h The input of significance from the user.
     * @param i The input of category from the user.
     * The function will transfer input data to pipeline object and add it to the datafile.
     */
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

    /**
     * This is the method is to map to the show users the editing record page.
     * This page will get the input of searching keyword from the user.
     */
    @RequestMapping("/d")
    fun editRecord(): String {
        return search("Incident Number")
    }

    /**
     * This is the method is to map to the page to ask user to enter the incident number of
     * the record to be deleted.
     */
    @RequestMapping("/e")
    fun deleteRecord(): String {
        return search("Incident Number")
    }

    /**
     * This is the method is to map to the show users the editing record page.
     * @param search The searching keyword from the user.
     * After searching the record from the datafile, the record to be edited will be shown to the user.
     */
    @RequestMapping("/edit/{search}")
    fun showEditRecords(@PathVariable("search") search: String): String {
        val record = service.getSearchResults("$search")
        return editPage(record.first())

    }

    /**
     * This function is to delete a record from the datafile.
     * @param search The searching keyword from the user.
     * The user is asked to enter the unique incident number of the pipeline incident.
     * The function will firstly search the record using the incident number and then delete it.
     */
    @RequestMapping("/delete/{search}")
    fun showDeleteRecords(@PathVariable("search") search: String): String {
        val record = service.getSearchResults("$search")
        return editPage(record.first())
    }

    /**
     * This is a function to show an editable page to edit the record to the user.
     * @param a The input of incident number from the user.
     * @param b The input of incident type from the user.
     * @param mm The input of incident month from the user.
     * @param dd The input of incident day from the user.
     * @param yy The input of incident year from the user.
     * @param d The input of center from the user.
     * @param e The input of province from the user.
     * @param f The input of company from the user.
     * @param g The input of substance from the user.
     * @param h The input of significance from the user.
     * @param i The input of category from the user.
     * After the getting all information from the users, the function will update the edited record in the datafile.
     */
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

    /**
     * This is a function to show an editable page to edit the record to the user.
     * @param a The input of incident number from the user.
     * @param b The input of incident type from the user.
     * @param mm The input of incident month from the user.
     * @param dd The input of incident day from the user.
     * @param yy The input of incident year from the user.
     * @param d The input of center from the user.
     * @param e The input of province from the user.
     * @param f The input of company from the user.
     * @param g The input of substance from the user.
     * @param h The input of significance from the user.
     * @param i The input of category from the user.
     * After the getting all information from the users, the function will update the edited record in the datafile.
     */
    @RequestMapping("/search/{columns}")
    fun searchByColumns(@PathVariable("columns") columns: String): String {
        var result = showResults("Search Results")

        var map = mutableMapOf<String, String>()

        val list = "$columns".split(" ")

        list.forEach {
            val element = it.split(":")
            val key = element[0]
            val value = element[1]
            map[key] = value
        }
        println(map)
        return result
    }

    /**
     * This is the function to delete a record.
     * @param delete The input of incident number from the user.
     * After searching the record from the datafile, the record will be deleted.
     */
    @RequestMapping("/deleteOne/{delete}")
    fun deleteRecords(@PathVariable("delete") delete: String): String {
        service.deleteOneRecord("$delete")
        return messages("deleted")
    }

    /**
     * This is a function to present search page to users.
     */
    @RequestMapping("/g")
    fun searchRecords(): String {
        return searchPageByColumns()
    }

    /**
     * This is a function to show users some information using html strings.
     * @param message The string message input to show.
     */
    fun messages(message: String): String {
        return """
               <h4>The record is already $message.</h4>
               <a href="http://localhost:8087/">BACK</a>
               <br>
               <b>Program by: Feiqiong DENG</b>
               """
    }

    /**
     * This is a function to show users results using html strings.
     * @param name The string input of message to show what kind of data results are presented.
     */
     fun showResults(name: String): String {
        return """
				<h3>$name:</h3>
				<p>Program by: Feiqiong DENG</p>
	            <a href="http://localhost:8087/">BACK</a>
                <br>
                <br>
				""".trimIndent()
    }

    /**
     * This is the function returning a page using html to show a search page to ask users to enter required data.
     * @param keyword The string input of the search keyword from the users.
     */
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
	            <a href="http://localhost:8087/">BACK</a>
				<br>
				<p>Program by: Feiqiong DENG</p>
                <script>
                    function getVal()  {
                     return document.querySelector('input').value;
                    }
                     document.getElementById("myBtn").addEventListener("click", myFunction);
                     function myFunction() {
                         window.location.href="http://localhost:8087/result/"+getVal();
                      }
                      document.getElementById("edit").addEventListener("click", editFunction);
                     function editFunction() {
                         window.location.href="http://localhost:8087/edit/"+getVal();
                      }
                      document.getElementById("delete").addEventListener("click", deleteFunction);
                     function deleteFunction() {
                         window.location.href="http://localhost:8087/delete/"+getVal();
                      }
                </script>
				""".trimIndent()
    }

    /**
     * This is the function returning a page using html to show a sort page to ask users to enter required data.
     */
    fun sort(): String {
        return """
            <b>Please enter a criterion to sort records:</b>
            <br>
            <br>
                <table>
                    <tr>
                        <td>(A) Incident Number</td>
                    </tr>
                    <tr>
                        <td>(B) Incident Types</td>
                    </tr>
                    <tr>
                        <td>(C) Reported Date</td>
                    </tr>
                    <tr>
                        <td>(D) Nearest Populated Centre</td>
                    </tr>
                    <tr>
                        <td>(E) Province</td>
                    </tr>
                    <tr>
                        <td>(F) Company</td>
                    </tr>
                     <tr>
                        <td>(G) Substance</td>
                    </tr>
                     <tr>
                        <td>(H) Significant</td>
                    </tr>
                     <tr>
                        <td>(I) What happened category</td>
                    </tr>
                    <tr>
                        <td><label path="br"></label></td>
                    </tr>
                    <tr>
                        <td>Your choice:</td>
                    </tr>
                    <tr>
                        <td><input name="input"/></td>
                         <tr><td><button id="myBtn">Submit</button></td></tr>
                    </tr>
                </table>
                <p>Program by: Feiqiong DENG</p>
                <script>
              function getVal()  {
                return document.querySelector('input').value;
              }
               document.getElementById("myBtn").addEventListener("click", myFunction);
                 function myFunction() {
                 window.location.href="http://localhost:8087/sort/"+getVal();
                 }
                 </script>
            """.trimIndent()
    }

    /**
     * This is a function to present the adding page to users using html format.
     */
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
	            <a href="http://localhost:8087/">BACK</a>
				<br>
				<p>Program by: Feiqiong DENG</p>
                <script>
                    function getInput(input)  {
                     return document.getElementById(input).value;
                    }
            
                     document.getElementById("add").addEventListener("click", myFunction);
                     function myFunction() {
                         window.location.href="http://localhost:8087/add/" + getInput("num") + "/" + getInput("type")
                          + "/" + getInput("date") + "/"  + getInput("center") + "/"  + getInput("province")
                          + "/" + getInput("company") + "/"  + getInput("substance")
                          + "/" + getInput("significant") + "/"  + getInput("category");
                      }
                </script>
            """
    }

    /**
     * This is a function to present the adding page to users using html format.
     */
    fun searchPageByColumns(): String {
        return """
            	<b>Please enter keywords you want to search:</b>
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
                <tr><td><button id="add">SEARCH</button></td></tr>
    			</table>
	            <br>
	            <a href="http://localhost:8087/">BACK</a>
				<br>
				<p>Program by: Feiqiong DENG</p>
                <script>
                    function getInput(input)  {
                     return document.getElementById(input).value;
                    }
            
                     document.getElementById("add").addEventListener("click", myFunction);
                     function myFunction() {
                         var url = window.location.href;
                         url = "http://localhost:8087/search/";
                         if(getInput("num") != "") {
                          url += "number:" + getInput("num") + " "
                         }
                         if(getInput("type") != "") {
                          url += "type:" + getInput("type") + " "
                         }
                          if(getInput("date") != "") {
                          url += "date:" + getInput("date") + " "
                         }
                          if(getInput("center") != "") {
                          url += "center:" + getInput("center") + " "
                         }
                          if(getInput("province") != "") {
                          url += "province:" + getInput("province") + " "
                         }
                          if(getInput("company") != "") {
                          url += "company:" + getInput("company") + " "
                         }
                          if(getInput("substance") != "") {
                          url += "substance:" + getInput("substance") + " "
                         }
                          if(getInput("significant") != "") {
                          url += "significant:" + getInput("significant") + " "
                         }
                          if(getInput("category") != "") {
                          url += "category:" + getInput("category") + " "
                         }
                      window.location.href = url;
                      }
                </script>
            """
    }

    /**
     * This is a function to present the editing page to users using html format.
     * @param editRecord The input of a pipeline record from users after searching in the datafile.
     */
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
	            <a href="http://localhost:8087/">BACK</a>
				<br>
				<p>Program by: Feiqiong DENG</p>
                <script>
                    function getInput(input) {
                     return document.getElementById(input).value;
                    }
                            
                     document.getElementById("edit").addEventListener("click", myFunction);
                     function myFunction() {
                         window.location.href="http://localhost:8087/edit/" + getInput("num") + "/" + getInput("type")
                          + "/" + getInput("date") + "/"  + getInput("center") + "/"  + getInput("province")
                          + "/" + getInput("company") + "/"  + getInput("substance")
                          + "/" + getInput("significant") + "/"  + getInput("category") + "/" +"${editRecord.number}";
                      }
                      
                     document.getElementById("delete").addEventListener("click", deleteFunction);
                     function deleteFunction() {
                         window.location.href="http://localhost:8087/deleteOne/" + "${editRecord.number}";
                      }
                </script>
            """
    }
}

