package com.cst8333.project

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

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
}
