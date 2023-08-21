package org.example.bpmn.project.process.exceptions;

class CamundaBpmnException : Exception {

    constructor(message: String?, cause: Throwable?): super(message, cause)

    constructor(message: String?): super(message)

    constructor(cause: Throwable?): super(cause)

}
