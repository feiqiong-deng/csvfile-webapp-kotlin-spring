package com.cst8333.project.persistent

import com.cst8333.project.model.PipelineRecord

interface PipelineDataSource {
    fun getAll(): Collection<PipelineRecord>
    fun getSearchResults(search: String): Collection<PipelineRecord>
    fun addOneRecord(newRecord: PipelineRecord)
    fun editOneRecord(newRecord: PipelineRecord, number: String)
    fun deleteOneRecord(number: String)
}