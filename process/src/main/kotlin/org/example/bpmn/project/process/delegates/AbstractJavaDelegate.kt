package org.example.bpmn.project.process.delegates

import kotlinx.coroutines.runBlocking
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.example.bpmn.project.process.ProcessData
import org.example.bpmn.project.process.Transition
import org.example.bpmn.project.process.Transitions
import org.example.bpmn.project.process.Variables
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.Serializable

abstract class AbstractJavaDelegate<B : Serializable> : JavaDelegate {

    private val logger = LoggerFactory.getLogger(this::class.java)

    protected fun getLogger(): Logger = logger

    protected open fun getBusinessContext(execution: DelegateExecution): B {
        return execution.getVariable(Variables.BUSINESS_CONTEXT) as B
    }

    override fun execute(execution: DelegateExecution) {
        execution.setVariable(Variables.TRANSITION, null)
        val processData = ProcessData(
            processBusinessKey = execution.processBusinessKey,
            processInstanceId = execution.processInstanceId,
            currentActivityId = execution.currentActivityId,
            currentActivityName = execution.currentActivityName,
            executionId = execution.id,
            processDefinitionId = execution.processDefinitionId
        )

        try {
            logger.info("\nНачато выполнение:$processData")
            val businessContext: B = getBusinessContext(execution)
            val transition: Transition
            runBlocking {
                transition = execute(processData, businessContext)
            }
            execution.setVariable(Variables.BUSINESS_CONTEXT, businessContext)
            if(Transitions.EMPTY != transition) {
                execution.setVariable(Variables.TRANSITION, transition.title)
            }
        } catch (t: Throwable) {
            logger.error("$processData ${t.message}", t)
            throw t
        }
    }

    abstract suspend fun execute(processData: ProcessData, context: B): Transition

}