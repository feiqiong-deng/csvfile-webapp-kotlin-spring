package com.cst8333.project.presentation

import com.cst8333.project.business.PipelineService
import com.cst8333.project.model.PipelineRecord
import org.springframework.web.bind.annotation.*

@RestController
class ProjectView(val service: PipelineService) {
    @RequestMapping("/a")
    fun showAllRecords(): String {
        var result = service.showResults("All Records")
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
        return service.search("keyword")
    }

    @RequestMapping("/result/{search}")
    fun showSearchRecords(@PathVariable("search") search: String): String {
        var result = service.showResults("Search Results")
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
        return service.addPage()
    }

    @RequestMapping("/add/{a}/{b}/{mm}/{dd}/{yy}/{d}/{e}/{f}/{g}/{h}/{i}")
    fun addOneRecord(@PathVariable("a") a: String, @PathVariable("b") b: String, @PathVariable("mm") mm: String,
                     @PathVariable("dd") dd: String, @PathVariable("yy") yy: String, @PathVariable("d") d: String,
                     @PathVariable("e") e: String,@PathVariable("f") f: String, @PathVariable("g") g: String,
                     @PathVariable("h") h: String, @PathVariable("i") i: String): String {

        val oneRecord = PipelineRecord("$a", "$b", "$mm/$dd/$yy", "$d", "$e",
                                "$f", "$g", "$h", "i")

        service.addOneRecord(oneRecord)

        return """
               <h4>The record is already added.</h4>
               <a href="http://localhost:8088/">BACK</a>
               <br>
               <b>Program by: Feiqiong DENG</b>
               """
    }

    @RequestMapping("/d")
    fun editRecord(): String {
        return service.search("Incident Number")
    }

    @RequestMapping("/edit/{search}")
    fun showEditRecords(@PathVariable("search") search: String): String {
        val record = service.getSearchResults("$search")
        return service.editPage(record.first())
    }

    @RequestMapping("/delete/{search}")
    fun showDeleteRecords(@PathVariable("search") search: String): String {
        return "delete"
    }

    @RequestMapping("/edit/{a}/{b}/{mm}/{dd}/{yy}/{d}/{e}/{f}/{g}/{h}/{i}/{j}")
    fun ediTOneRecord(@PathVariable("a") a: String, @PathVariable("b") b: String, @PathVariable("mm") mm: String,
                     @PathVariable("dd") dd: String, @PathVariable("yy") yy: String, @PathVariable("d") d: String,
                     @PathVariable("e") e: String,@PathVariable("f") f: String, @PathVariable("g") g: String,
                     @PathVariable("h") h: String, @PathVariable("i") i: String, @PathVariable("j") j: String): String {

        val oneRecord = PipelineRecord("$a", "$b", "$mm/$dd/$yy", "$d", "$e",
            "$f", "$g", "$h", "i")

        service.editOneRecord(oneRecord, "$j")

        return """
               <h4>The record is already updated.</h4>
               <a href="http://localhost:8088/">BACK</a>
               <br>
               <b>Program by: Feiqiong DENG</b>
               """
    }
}

