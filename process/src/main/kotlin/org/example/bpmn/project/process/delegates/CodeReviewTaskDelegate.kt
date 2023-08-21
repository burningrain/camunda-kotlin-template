package org.example.bpmn.project.process.delegates

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.example.bpmn.developer.review.api.ReviewV10
import org.example.bpmn.project.process.ProcessData
import org.example.bpmn.project.process.Transition
import org.example.bpmn.project.process.Transitions
import org.example.bpmn.project.process.context.ExampleProcessContext
import org.example.bpmn.project.services.TransportService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.lang.IllegalStateException
import java.util.*

@Component("codeReviewTaskDelegate")
class CodeReviewTaskDelegate(
    @Qualifier("reviewService") private val reviewTransport: TransportService<ReviewV10.SendToReviewRequest, ReviewV10.SendToReviewResponse>
) : AbstractJavaDelegate<ExampleProcessContext>() {

    override suspend fun execute(processData: ProcessData, context: ExampleProcessContext): Transition {
        val deferred: Deferred<ReviewV10.SendToReviewResponse> = CoroutineScope(Dispatchers.IO).async {
            reviewTransport.send(createRequest(processData.processBusinessKey!!, context.task!!.id!!))
        }
        val rs = deferred.await()
        return when (rs.status) {
            ReviewV10.ReviewStatus.APPROVE      -> Transitions.TO_TESTING
            ReviewV10.ReviewStatus.NEED_WORK    -> Transitions.TO_WAITING_DAILY_END
            else -> throw IllegalStateException("$processData An unknown status from a code review")
        }
    }

    private fun createRequest(processBusinessKey: String, id: Int): ReviewV10.SendToReviewRequest {
        return ReviewV10.SendToReviewRequest.newBuilder()
            .setRqUid(UUID.randomUUID().toString())
            .setOperUid(processBusinessKey)
            .setTaskNumber(id)
            .setProgramCode("a lot of code")
            .build()
    }

}