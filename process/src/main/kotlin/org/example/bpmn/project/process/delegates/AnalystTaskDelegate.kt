package org.example.bpmn.project.process.delegates

import org.example.bpmn.analyst.api.SendToAnalystRequestV10
import org.example.bpmn.analyst.api.SendToAnalystResponseV10
import org.example.bpmn.project.process.ProcessData
import org.example.bpmn.project.process.Transition
import org.example.bpmn.project.process.Transitions
import org.example.bpmn.project.process.context.ExampleProcessContext
import org.example.bpmn.project.process.context.Status
import org.example.bpmn.project.process.context.TaskDto
import org.example.bpmn.project.services.TransportService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component("analystTaskDelegate")
class AnalystTaskDelegate(
    @Qualifier("analystService") private val transportService: TransportService<SendToAnalystRequestV10, SendToAnalystResponseV10>
) : AbstractJavaDelegate<ExampleProcessContext>() {

    override suspend fun execute(processData: ProcessData, context: ExampleProcessContext): Transition {
        val task: TaskDto = when(context.businessTask!!.length) {
            42 -> throw IllegalArgumentException("это как описать смысл жизни, нам на это нужно пару лет!")
            else -> createTrivialTask()
        }
        context.task = task

        return Transitions.EMPTY
    }

    private fun createTrivialTask(): TaskDto {
        return TaskDto(4, "я в личном созвоне расскажу словами как это вижу, а вы там на тестировании сами разберетесь", Status.CREATED)
    }

}