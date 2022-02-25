package com.cst8333.project.business

import com.cst8333.project.model.PipelineRecord
import com.cst8333.project.persistent.PipelineData
import org.springframework.stereotype.Service

/**
 * This is a class in business layer. It is an entry point to the business logic.
 * @author Feiqiong Deng
 * @param source This is the input of a pipelineData from persistent layer.
 * This class implements use cases the application supports.
 * It behaves like a middleman between persistent layer and presentation layer.
 */
@Service
class PipelineService(val source: PipelineData) {
    fun getAllRecords(): Collection<PipelineRecord> = source.getAll()
    fun getSearchResults(search: String) = source.getSearchResults(search)
    fun addOneRecord(newRecord: PipelineRecord) = source.addOneRecord(newRecord)
    fun editOneRecord(newRecord: PipelineRecord, number: String) = source.editOneRecord(newRecord, number)
    fun deleteOneRecord(number: String) = source.deleteOneRecord(number)
}