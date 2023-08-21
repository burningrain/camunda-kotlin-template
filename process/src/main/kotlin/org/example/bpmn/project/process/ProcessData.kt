package org.example.bpmn.project.process

data class ProcessData(
    val processBusinessKey: String?,
    val processInstanceId: String?,
    val currentActivityId: String?,
    val currentActivityName: String?,
    val executionId: String?,
    val processDefinitionId: String?
) {

    override fun toString():String {
        return "\n" + """
            processDefinitionId = $processDefinitionId
            currentActivityName = $currentActivityName
            currentActivityId   = $currentActivityId
            processInstanceId   = $processInstanceId
            executionId         = $executionId
            processBusinessKey  = $processBusinessKey
        """.trimIndent() + "\n"
    }

}