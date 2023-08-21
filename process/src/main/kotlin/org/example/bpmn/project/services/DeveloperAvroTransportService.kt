package org.example.bpmn.project.services

import org.example.bpmn.developer2.avro.api.Developer2
import org.example.bpmn.developer2.avro.api.TaskRequest
import org.example.bpmn.developer2.avro.api.TaskResponse
import org.example.bpmn.project.BpmnExampleProperties

class DeveloperAvroTransportService(properties: BpmnExampleProperties.AvroProperties) :
    AvroTransportService<TaskRequest, TaskResponse, Developer2>(properties) {

    override fun getAvroGenerateServiceClass(): Class<Developer2> {
        return Developer2::class.java
    }

    override fun sendRequest(service: Developer2, request: TaskRequest): TaskResponse {
        return service.sendTask(request)
    }

}