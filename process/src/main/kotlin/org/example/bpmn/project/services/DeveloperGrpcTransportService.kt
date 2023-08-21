package org.example.bpmn.project.services

import io.grpc.Channel
import org.example.bpmn.developer.developer1.api.Developer1Grpc
import org.example.bpmn.developer.developer1.api.Developer1V10

class DeveloperGrpcTransportService(
    private val channel: Channel
) : TransportService<Developer1V10.TaskRequest, Developer1V10.TaskResponse> {

    private val newBlockingStub = Developer1Grpc.newBlockingStub(channel)

    override suspend fun send(request: Developer1V10.TaskRequest): Developer1V10.TaskResponse {
        return newBlockingStub.sendTask(request)
    }

}