package org.example.bpmn.project.process.context

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExampleProcessContext(
    var businessTask: String? = null,
    var task: TaskDto? = null,
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }

}