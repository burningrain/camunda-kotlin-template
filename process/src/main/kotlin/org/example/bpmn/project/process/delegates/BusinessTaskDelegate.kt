package org.example.bpmn.project.process.delegates

import org.example.bpmn.project.process.ProcessData
import org.example.bpmn.project.process.Transition
import org.example.bpmn.project.process.Transitions
import org.example.bpmn.project.process.context.ExampleProcessContext
import org.springframework.stereotype.Component

@Component("businessTaskDelegate")
class BusinessTaskDelegate() : AbstractJavaDelegate<ExampleProcessContext>() {

    override suspend fun execute(processData: ProcessData, context: ExampleProcessContext): Transition {
        context.businessTask = "надо что-то придумать такое глобальное, чтоб потом хорошо и правильно за это отчитаться!"

        return Transitions.EMPTY
    }

}