package org.example.bpmn.project.process;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.example.bpmn.project.process.exceptions.CamundaBpmnException
import org.springframework.stereotype.Component

@Component
class ProcessFacade(private val runtimeService: RuntimeService) {


    fun startProcess(processDefinitionKey: String, processBusinessKey: String, variables: Map<String, Any>): ProcessInstance {
        try {
            return runtimeService.startProcessInstanceByKey(
                    processDefinitionKey,
                    processBusinessKey,
                    variables
            );
        } catch (e: Exception) {
            throw CamundaBpmnException("$processDefinitionKey $processBusinessKey  ${e.message}", e);
        }
    }

    fun startProcessByMessage(messageName: String, processBusinessKey: String, variables: Map<String, Any>): ProcessInstance {
        try {
            return runtimeService.startProcessInstanceByMessage(
                    messageName,
                    processBusinessKey,
                    variables
            );
        } catch (e: Exception) {
            throw CamundaBpmnException(e.message, e);
        }
    }

    fun continueProcessByMessage(messageName: String, processBusinessKey:String, variables: Map<String, Object>) {
        try {
            runtimeService
                    .createMessageCorrelation(messageName)
                    .processInstanceBusinessKey(processBusinessKey)
                    .setVariables(variables)
                    .correlate();
        } catch (e: Exception) {
            throw CamundaBpmnException(e.message, e);
        }
    }

    fun continueProcessByMessageName(key: String, messageName: String) {
        try {
            runtimeService
                    .createMessageCorrelation(messageName)
                    .processInstanceBusinessKey(key)
                    .correlate();
        } catch (e: Exception) {
            throw CamundaBpmnException(e.message, e);
        }
    }

}
