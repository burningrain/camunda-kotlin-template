package org.example.bpmn.project.services

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import kotlin.reflect.KClass

class HttpTransportService<in RQ : Any, out RS : Any>(
    private val rsClazz: KClass<RS>,
    private val baseUrl: String,
    private val url: String,
    private val webClientBuilder: WebClient.Builder,
    private val type: MediaType,
) : TransportService<RQ, RS> {

    private val webClient = webClientBuilder.baseUrl(baseUrl).build()


    override suspend fun send(request: RQ): RS = webClient.post()
        .uri {
            it.path(url)
                .build()
        }
        .contentType(type)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(rsClazz.java)
        //.timeout(properties.commonLimits.requestTimeout)
        .awaitSingle()

}