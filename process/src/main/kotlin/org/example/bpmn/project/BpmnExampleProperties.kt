package org.example.bpmn.project

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.boot.context.properties.bind.DefaultValue

@ConfigurationProperties("example")
@ConstructorBinding
data class BpmnExampleProperties(

    val analystUrl: String,
    val testerUrl: String,

    @NestedConfigurationProperty
    @DefaultValue
    val developer1: GrpcProperties,

    @NestedConfigurationProperty
    @DefaultValue
    val developer2: AvroProperties,

    @NestedConfigurationProperty
    @DefaultValue
    val review: GrpcProperties,
) {

    data class GrpcProperties(
        val host: String,
        val port: Int,
    )

    data class AvroProperties(
        val host: String,
        val port: Int,
    )

}