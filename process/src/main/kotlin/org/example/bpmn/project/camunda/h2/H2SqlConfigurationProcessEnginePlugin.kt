package org.example.bpmn.project.camunda.h2

import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin
import org.camunda.bpm.engine.impl.db.sql.DbSqlSessionFactory
import org.camunda.bpm.spring.boot.starter.configuration.Ordering
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

/**
 * до версии 7.17 камунда не совместима с h2
 * так что это временный хак до обновления версии камунды, которая на сегодня не вышла
 *
 * https://forum.camunda.org/t/camunda-not-compatible-with-h2-2-0-202/32250/3
 */
@Component
@Order(Ordering.DEFAULT_ORDER + 2)
class H2SqlConfigurationProcessEnginePlugin : ProcessEnginePlugin {

    override fun preInit(processEngineConfiguration: ProcessEngineConfigurationImpl?) {
        DbSqlSessionFactory.databaseSpecificTrueConstant["h2"] = "true";
        DbSqlSessionFactory.databaseSpecificFalseConstant["h2"] = "false";
        DbSqlSessionFactory.databaseSpecificBitAnd2["h2"] = ",CAST(";
        DbSqlSessionFactory.databaseSpecificBitAnd3["h2"] = " AS BIGINT))";
    }

    override fun postInit(processEngineConfiguration: ProcessEngineConfigurationImpl?) {
        // in this case only preInit is necessary
    }

    override fun postProcessEngineBuild(processEngineConfiguration: ProcessEngine?) {
        // in this case only preInit is necessary
    }

}