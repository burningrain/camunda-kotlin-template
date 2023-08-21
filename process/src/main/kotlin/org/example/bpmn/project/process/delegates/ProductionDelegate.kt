package org.example.bpmn.project.process.delegates

import org.example.bpmn.project.process.ProcessData
import org.example.bpmn.project.process.Transition
import org.example.bpmn.project.process.context.ExampleProcessContext
import org.example.bpmn.project.process.exceptions.ProdIsOverException
import org.springframework.stereotype.Component
import java.lang.IllegalStateException

@Component("productionDelegate")
class ProductionDelegate() : AbstractJavaDelegate<ExampleProcessContext>() {

    override suspend fun execute(processData: ProcessData, context: ExampleProcessContext): Transition {
        getLogger().info("$processData УРА!!! ПРОМ_СРЕДА!!! Но что-то пошло не так...")
        throw ProdIsOverException("123456", "очень непонятная ошибка")
    }

}