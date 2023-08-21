package org.example.bpmn.project.process.context

import java.io.Serializable

data class TaskDto(
    var id: Int? = null,
    var description: String? = null,
    var status: Status? = null
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }

}