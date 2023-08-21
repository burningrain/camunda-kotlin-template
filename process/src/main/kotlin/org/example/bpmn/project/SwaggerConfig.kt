package org.example.bpmn.project

import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType.OAS_30
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig(val properties: BuildProperties?) {

    @Bean
    fun api(apiInfo: ApiInfo): Docket = Docket(OAS_30)
        .select()
        .apis(RequestHandlerSelectors.basePackage("org.example.bpmn.project.rest"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo)

    @Bean
    fun apiInfo() = ApiInfo(
        "Пример работы bpmn",
        "",
        properties?.version ?: "1.0",
        "",
        Contact("", "", ""),
        "",
        "",
        emptyList()
    )

}
