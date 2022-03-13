package com.cst8333.project

import com.cst8333.project.business.PipelineService
import com.cst8333.project.persistent.PipelineData
import com.cst8333.project.presentation.ProjectView
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.PathVariable

@SpringBootApplication
class ProjectApplication
/**
 * This is the entry point of the application.
 * @author Feiqiong DENG
 * In Kotlin language, the main method is not a method inside the class.
 * This application is a simple web application using SpringBoot and Kotlin.
 */
fun main(args: Array<String>) {
	runApplication<ProjectApplication>(*args)
//	val source = PipelineData()
//	val service = PipelineService(source)
//	val view = ProjectView(service)
//	view.showAllRecords()
//	view.showSearchRecords("search")
//	view.addRecord()
////	view.addOneRecord("a", "a", "a", "a","a", "a", "a", "a","a", "a", "a")
//	view.showEditRecords("search")
//	view.showDeleteRecords("search")
//	view.ediTOneRecord("a", "a", "a", "a","a", "a", "a", "a","a", "a", "a","a")
//	view.deleteRecords("search")
}
