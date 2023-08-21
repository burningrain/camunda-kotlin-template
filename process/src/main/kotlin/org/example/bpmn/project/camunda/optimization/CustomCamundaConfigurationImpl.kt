package org.example.bpmn.project.camunda.optimization

import org.camunda.bpm.engine.impl.history.HistoryLevel
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration
import org.springframework.stereotype.Component
import java.lang.Boolean

@Component
class CustomCamundaConfigurationImpl : AbstractCamundaConfiguration() {

    override fun preInit(configuration: SpringProcessEngineConfiguration) {
        configuration.isJdbcBatchProcessing = true // для оракла нужно ставить false. Postgres - true
    }

    override fun postInit(configuration: SpringProcessEngineConfiguration) {
        // для оптимизации отрубаем историю и метрики
        configuration.historyLevel = HistoryLevel.HISTORY_LEVEL_NONE
        configuration.isMetricsEnabled = false
        configuration.isDbMetricsReporterActivate = false
        configuration.databaseSchemaUpdate = Boolean.TRUE.toString()
        configuration.isJobExecutorActivate = true
        //configuration.failedJobCommandFactory = jobCommandFactory
    }

}