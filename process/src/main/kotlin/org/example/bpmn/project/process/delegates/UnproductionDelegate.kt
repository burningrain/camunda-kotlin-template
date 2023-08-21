package org.example.bpmn.project.process.delegates

import org.example.bpmn.project.process.ProcessData
import org.example.bpmn.project.process.Transition
import org.example.bpmn.project.process.Transitions
import org.example.bpmn.project.process.context.ExampleProcessContext
import org.springframework.stereotype.Component

@Component("unproductionDelegate")
class UnproductionDelegate() : AbstractJavaDelegate<ExampleProcessContext>() {

    override suspend fun execute(processData: ProcessData, context: ExampleProcessContext): Transition {
        getLogger().info("$processData так и не попали в пром")

        return Transitions.EMPTY
    }

}