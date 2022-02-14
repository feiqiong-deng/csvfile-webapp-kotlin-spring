package com.cst8333.project.persistent

import com.cst8333.project.model.PipelineRecord

interface PipelineDataSource {
    fun getAll(): Collection<PipelineRecord>
    fun showAll(): String
}