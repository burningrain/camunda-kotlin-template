package org.example.bpmn.project.process.delegates

import org.example.bpmn.project.process.ProcessData
import org.example.bpmn.project.process.Transition
import org.example.bpmn.project.process.Transitions
import org.example.bpmn.project.process.context.ExampleProcessContext
import org.springframework.stereotype.Component

@Component("troubleDelegate")
class TroubleDelegate() : AbstractJavaDelegate<ExampleProcessContext>() {

    override suspend fun execute(processData: ProcessData, context: ExampleProcessContext): Transition {
        getLogger().info("$processData Крутили-вертели, запутать хотели. Запутались сами, в час ночи устали... ")

        return Transitions.EMPTY
    }

}