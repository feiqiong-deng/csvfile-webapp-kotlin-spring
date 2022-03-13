package com.cst8333.project.model

/**
 * This is a data class to represent a pipeline record.
 * @author Feiqiong DENG
 * @property number The incident number of each record.
 * @property type The incident types of each record.
 * @property date The reported date of each record.
 * @property centre The nearest populated centre of each record.
 * @property province The province of each record.
 * @property company The company of each record.
 * @property substance The substance of each record.
 * @property significant The significance of each record.
 * @property category The incident category of each record.
 * All properties are declared as mutable ones and String type with default value null.
 * Mutable property has default getter and setter.
 */
data class PipelineRecord(
    var number: String,
    var type: String,
    var date: String,
    var centre: String,
    var province: String,
    var company: String,
    var substance: String,
    var significant: String,
    var category: String
)


