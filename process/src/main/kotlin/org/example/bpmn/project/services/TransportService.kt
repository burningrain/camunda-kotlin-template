package org.example.bpmn.project.services

interface TransportService<in RQ : Any, out RS : Any> {
    suspend fun send(request: RQ): RS
}