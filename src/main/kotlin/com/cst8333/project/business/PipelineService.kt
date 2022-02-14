package com.cst8333.project.business

import com.cst8333.project.model.PipelineRecord
import com.cst8333.project.persistent.PipelineData
import org.springframework.stereotype.Service

@Service
class PipelineService(val source: PipelineData) {
    fun getAllRecords(): Collection<PipelineRecord> = source.getAll()
    fun writeRecords() = source.writeAllData(source.getAll())
    fun showResults(name: String): String = source.showResults(name)
    fun search(): String = source.search()
    fun getSearchResults(search: String) = source.getSearchResults(search)
}