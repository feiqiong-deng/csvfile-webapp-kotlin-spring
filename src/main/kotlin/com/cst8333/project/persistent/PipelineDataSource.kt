package com.cst8333.project.persistent

import com.cst8333.project.model.PipelineRecord

/**
 * This is an interface defining what kinds of functionality in the layer of persistent
 * @author Feiqiong Deng
 * This class include abstract functions.
 * There is getAll function to return all  pipeline records.
 * The getSearchResults function is to search specific records.
 * The addOneRecord function is to perform editing record functionality.
 * The deleteOneRecord function is to perform deleting record functionality.
 */
interface PipelineDataSource {
    fun getAll(): Collection<PipelineRecord>
    fun getSearchResults(search: String): Collection<PipelineRecord>
    fun addOneRecord(newRecord: PipelineRecord)
    fun editOneRecord(newRecord: PipelineRecord, number: String)
    fun deleteOneRecord(number: String)
    fun getSortedResults(sortBy: String): Collection<PipelineRecord>
    fun getResultsByColumns(columns: MutableMap<String, String>): Collection<PipelineRecord>

}