package org.example.bpmn.project.camunda.spin

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormat
import org.camunda.spin.spi.DataFormatConfigurator


/**
 * подключается через механизм SPI. см. resources\META-INF\services\org.camunda.spin.spi.DataFormatConfigurator
 */
class JacksonDataFormatConfigurator : DataFormatConfigurator<JacksonJsonDataFormat?> {


    override fun configure(jacksonJsonDataFormat: JacksonJsonDataFormat?) {
        jacksonJsonDataFormat!!.objectMapper.registerModule(Jdk8Module())
    }

    override fun getDataFormatClass(): Class<JacksonJsonDataFormat?> =
        JacksonJsonDataFormat::class.java as Class<JacksonJsonDataFormat?>


}