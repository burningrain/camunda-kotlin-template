package org.example.bpmn.project.process.exceptions

import org.camunda.bpm.engine.delegate.BpmnError

class ProdIsOverException(errorCode: String?, message: String?) : BpmnError(errorCode, message) {



}