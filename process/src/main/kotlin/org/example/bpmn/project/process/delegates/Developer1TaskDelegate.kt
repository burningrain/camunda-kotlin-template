package org.example.bpmn.project.process.delegates

import com.google.protobuf.StringValue
import kotlinx.coroutines.*
import org.example.bpmn.developer.developer1.api.Developer1V10
import org.example.bpmn.project.process.ProcessData
import org.example.bpmn.project.process.Transition
import org.example.bpmn.project.process.Transitions
import org.example.bpmn.project.process.context.ExampleProcessContext
import org.example.bpmn.project.services.TransportService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.lang.IllegalStateException
import java.util.*

@Component("developer1TaskDelegate")
class Developer1TaskDelegate(
    @Qualifier("developer1Service") private val developer1Service: TransportService<Developer1V10.TaskRequest, Developer1V10.TaskResponse>
) : AbstractJavaDelegate<ExampleProcessContext>() {

    override suspend fun execute(processData: ProcessData, context: ExampleProcessContext): Transition {
        val deferred: Deferred<Developer1V10.TaskResponse> = CoroutineScope(Dispatchers.IO).async {
            developer1Service.send(createRequest(processData.processBusinessKey!!, context.task!!.id!!))
        }
        val rs = deferred.await()

        if (Developer1V10.Status.OK != rs.status) {
            throw IllegalStateException("$processData ${rs.operUid} status is ${rs.status}!")
        }
        getLogger().info("$processData taskId=${context.task!!.id!!} комментарий разработчика: ${rs.comment.value}")

        return Transitions.EMPTY
    }

    private fun createRequest(operUid: String, taskNumber: Int): Developer1V10.TaskRequest {
        return Developer1V10.TaskRequest.newBuilder()
            .setRqUid(UUID.randomUUID().toString())
            .setOperUid(operUid)
            .setTaskNumber(taskNumber)
            .setComment(StringValue.newBuilder().setValue("если что непонятно - спрашивай!").build())
            .build()
    }

}