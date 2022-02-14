package com.cst8333.project.persistent

import com.cst8333.project.model.PipelineRecord

interface PipelineDataSource {
    fun getAll(): Collection<PipelineRecord>
    fun writeAllData(list: Collection<PipelineRecord>)
    fun showResults(name: String): String
    fun search(): String
    fun getSearchResults(search: String): Collection<PipelineRecord>
}