package org.example.bpmn.project.process.delegates

import org.example.bpmn.project.process.ProcessData
import org.example.bpmn.project.process.Transition
import org.example.bpmn.project.process.Transitions
import org.example.bpmn.project.process.context.ExampleProcessContext
import org.example.bpmn.project.services.TransportService
import org.example.bpmn.tester.api.Status
import org.example.bpmn.tester.api.TaskRequest
import org.example.bpmn.tester.api.TaskResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.lang.IllegalStateException
import java.util.*

@Component("testingTaskDelegate")
class TestingTaskDelegate(
    @Qualifier("testerService") private val transportService: TransportService<TaskRequest, TaskResponse>
) : AbstractJavaDelegate<ExampleProcessContext>() {

    override suspend fun execute(processData: ProcessData, context: ExampleProcessContext): Transition {
        val rs = transportService.send(createRequest(processData.processBusinessKey!!, context.task!!.id!!))
        return when(rs.status) {
            Status.OK           -> Transitions.TO_PROD
            Status.ERROR        -> Transitions.TO_UNPROD
            Status.NEED_INFO    -> Transitions.TO_TALK_WITH_ANALYST
            else -> throw IllegalStateException("$processData An unknown status from a code review")
        }
    }

    private fun createRequest(processBusinessKey: String, id: Int): TaskRequest {
        return TaskRequest().apply {
            this.rqUID = UUID.randomUUID().toString()
            this.operUID = processBusinessKey
            this.taskNumber = id
            this.comment = "без комментариев разработки"
        }
    }

}