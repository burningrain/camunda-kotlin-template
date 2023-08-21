package org.example.bpmn.project.process.delegates

import org.example.bpmn.analyst.api.SendToAnalystRequestV10
import org.example.bpmn.analyst.api.SendToAnalystResponseV10
import org.example.bpmn.project.process.ProcessData
import org.example.bpmn.project.process.Transition
import org.example.bpmn.project.process.Transitions
import org.example.bpmn.project.process.context.ExampleProcessContext
import org.example.bpmn.project.services.TransportService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.lang.IllegalStateException
import java.util.*

@Component("analystTalkTaskDelegate")
class AnalystTalkTaskDelegate(
    @Qualifier("analystService") private val transportService: TransportService<SendToAnalystRequestV10, SendToAnalystResponseV10>
) : AbstractJavaDelegate<ExampleProcessContext>() {

    override suspend fun execute(processData: ProcessData, context: ExampleProcessContext): Transition {
        val rs = transportService.send(createRequest(processData.processBusinessKey!!, context.task!!.id!!))
        return when (rs.status) {
            SendToAnalystResponseV10.Status.NORMAL -> Transitions.TO_TESTING_AGAIN
            SendToAnalystResponseV10.Status.TO_DEV -> Transitions.TO_ANALYZE_AGAIN
            SendToAnalystResponseV10.Status.REWRITE_DOCS -> Transitions.TO_DEVELOPMENT_AGAIN
            else -> throw IllegalStateException("$processData An unknown status from an analyst")
        }
    }

    private fun createRequest(processBusinessKey: String, id: Int): SendToAnalystRequestV10 {
        return SendToAnalystRequestV10().apply {
            this.requestId = UUID.randomUUID().toString()
            this.operationId = processBusinessKey
            this.taskNumber = id
            this.questions = setOf(
                "так там integer или string или что?",
                "а почему мы именно так делаем?",
                "как это вообще должно работать? В аналитике ничего не написано"
            )
        }
    }

}