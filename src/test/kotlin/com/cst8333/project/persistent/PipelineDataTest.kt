package com.cst8333.project.persistent

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PipelineDataTest {

    private val pipelineData = PipelineData()
    @Test
    fun test1() {
        // when
        val data = pipelineData.getAll()
        
        // then
        assertThat(data).isNotEmpty
     }
}