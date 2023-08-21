package org.example.bpmn.project.process.delegates

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.example.bpmn.developer2.avro.api.TaskRequest
import org.example.bpmn.developer2.avro.api.TaskResponse
import org.example.bpmn.developer2.avro.api.Status
import org.example.bpmn.project.process.ProcessData
import org.example.bpmn.project.process.Transition
import org.example.bpmn.project.process.Transitions
import org.example.bpmn.project.process.context.ExampleProcessContext
import org.example.bpmn.project.services.TransportService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.lang.IllegalStateException
import java.util.*

@Component("developer2TaskDelegate")
class Developer2TaskDelegate(
    @Qualifier("developer2Service") private val transportService: TransportService<TaskRequest, TaskResponse>
) : AbstractJavaDelegate<ExampleProcessContext>() {

    override suspend fun execute(processData: ProcessData, context: ExampleProcessContext): Transition {
        val deferred: Deferred<TaskResponse> = CoroutineScope(Dispatchers.IO).async {
            transportService.send(createRequest(processData.processBusinessKey!!, context.task!!.id!!))
        }

        val rs = deferred.await()

        if (Status.OK != rs.status) {
            throw IllegalStateException("$processData ${rs.operUid} status is ${rs.status}!")
        }
        getLogger().info("$processData taskId=${context.task!!.id!!} комментарий разработчика: ${rs.comment}")

        return Transitions.EMPTY
    }

    private fun createRequest(operUid: String, taskId: Int): TaskRequest {
        return TaskRequest().apply {
            this.rqUid = UUID.randomUUID().toString()
            this.operUid = operUid
            this.taskNumber = taskId
            this.comment = "тут все подробно описано в аналитике"
        }
    }

}