package org.example.bpmn.project

import io.grpc.ManagedChannelBuilder
import org.example.bpmn.analyst.api.SendToAnalystRequestV10
import org.example.bpmn.analyst.api.SendToAnalystResponseV10
import org.example.bpmn.developer.developer1.api.Developer1V10
import org.example.bpmn.developer.review.api.ReviewV10
import org.example.bpmn.project.services.*
import org.example.bpmn.tester.api.TaskRequest
import org.example.bpmn.tester.api.TaskResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class TransportConfig {

    @Autowired
    private lateinit var props: BpmnExampleProperties

    @Bean("developer1Service")
    fun developer1Service(): TransportService<Developer1V10.TaskRequest, Developer1V10.TaskResponse> {
        val developer1 = props.developer1

        return DeveloperGrpcTransportService(
            ManagedChannelBuilder.forAddress(developer1.host, developer1.port).usePlaintext().build()
        )
    }

    @Bean("developer2Service")
    fun developer2Service(): TransportService<org.example.bpmn.developer2.avro.api.TaskRequest, org.example.bpmn.developer2.avro.api.TaskResponse> {
        return DeveloperAvroTransportService(props.developer2)
    }

    @Bean("reviewService")
    fun reviewService(): TransportService<ReviewV10.SendToReviewRequest, ReviewV10.SendToReviewResponse> {
        val review = props.review

        return ReviewGrpcTransportService(
            ManagedChannelBuilder.forAddress(review.host, review.port).usePlaintext().build()
        )
    }

    @Bean("analystService")
    fun analystService(webClientBuilder: WebClient.Builder): TransportService<SendToAnalystRequestV10, SendToAnalystResponseV10> {
        return HttpTransportService(
            SendToAnalystResponseV10::class,
            props.analystUrl,
            "/task",
            webClientBuilder,
            MediaType.APPLICATION_JSON
        )
    }

    @Bean("testerService")
    fun testerService(webClientBuilder: WebClient.Builder): TransportService<TaskRequest, TaskResponse> {
        return HttpTransportService(
            TaskResponse::class,
            props.testerUrl,
            "/task",
            webClientBuilder,
            MediaType.APPLICATION_XML
        )
    }

}