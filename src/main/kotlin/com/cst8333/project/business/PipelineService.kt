package com.cst8333.project.business

import com.cst8333.project.model.PipelineRecord
import com.cst8333.project.persistent.PipelineData
import org.springframework.stereotype.Service

@Service
class PipelineService(val source: PipelineData) {
    fun getAllRecords(): Collection<PipelineRecord> = source.getAll()
    fun showResults(name: String): String = source.showResults(name)
    fun search(keyword: String): String = source.search(keyword)
    fun getSearchResults(search: String) = source.getSearchResults(search)
    fun addPage() = source.addPage()
    fun editPage(editRecord: PipelineRecord) = source.editPage(editRecord)
    fun addOneRecord(newRecord: PipelineRecord) = source.addOneRecord(newRecord)
    fun editOneRecord(newRecord: PipelineRecord, number: String) = source.editOneRecord(newRecord, number)
}