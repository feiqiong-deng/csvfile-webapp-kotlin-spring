package com.cst8333.project.presentation

import com.cst8333.project.business.PipelineService
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
        return service.search()
    }

    @RequestMapping("/result/{search}")
    @ResponseBody
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



}

