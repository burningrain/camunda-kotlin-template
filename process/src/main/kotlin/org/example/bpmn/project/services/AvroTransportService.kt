package org.example.bpmn.project.services

import org.apache.avro.ipc.Transceiver
import org.apache.avro.ipc.netty.NettyTransceiver
import org.apache.avro.ipc.specific.SpecificRequestor
import org.example.bpmn.project.BpmnExampleProperties
import java.net.InetSocketAddress

/**
 * причина существования данного класса - падение для на старте при создании NettyTransceiver
 * ломает поднятие спринга при инциализации бина
 */
abstract class AvroTransportService<in RQ : Any, out RS : Any, S : Any>(
    private val properties: BpmnExampleProperties.AvroProperties
) : TransportService<RQ, RS> {

    @Volatile
    private var isInitialized : Boolean = false
    private lateinit var service: S

    @Synchronized
    private fun init() {
        if(!isInitialized) {
            val client: Transceiver = NettyTransceiver(InetSocketAddress(properties.host, properties.port))
            service = SpecificRequestor.getClient(getAvroGenerateServiceClass(), client) as S

            isInitialized = true
        }
    }

    protected abstract fun getAvroGenerateServiceClass(): Class<S>

    override suspend fun send(request: RQ): RS {
        if(!isInitialized) {
            init()
        }

        return sendRequest(service, request)
    }

    abstract fun sendRequest(service: S, request: RQ): RS

}