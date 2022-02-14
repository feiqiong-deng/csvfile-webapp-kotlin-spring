package com.cst8333.project.presentation

import com.cst8333.project.business.PipelineService
import com.cst8333.project.model.PipelineRecord
import org.springframework.web.bind.annotation.*

@RestController
class ProjectView(val service: PipelineService) {
    @RequestMapping("/a")
    fun showAllRecords(): String {
        var result = service.showAll()
        service.getAllRecords().forEach {
            result += it.number + ", " + it.type + ", " + it.date + ", " +
                    it.centre + ", " + it.province + ", " + it.company + ", " +
                    it.substance + ", " + it.significant + ", " + it.category + "<br>"
        }
        return result
    }

    @RequestMapping("/b")
    fun oneRecord(): String {
		return """
				<b>Please enter a keyword to search:</b>
				<p>Program by: Feiqiong DENG</p>
	            <a href="http://localhost:8088/">BACK</a>
                <br>
                <br>
				""".trimIndent()
    }


}

