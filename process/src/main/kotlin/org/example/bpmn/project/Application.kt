package org.example.bpmn.project

import com.fasterxml.jackson.databind.ObjectMapper
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.example.bpmn.analyst.api.SendToAnalystResponseV10
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import java.util.*

@EnableProcessApplication
@SpringBootApplication
@EnableConfigurationProperties(
    BpmnExampleProperties::class,
)
class Application {
}

fun main(args: Array<String>) {
//    SpringApplicationBuilder()
//        .sources(
//
//        )
//        .run(*args)

    runApplication<Application>(*args)
}