package org.example.bpmn.project.rest

import io.swagger.annotations.ApiOperation
import org.example.bpmn.project.process.ContinuePoints
import org.example.bpmn.project.process.ProcessFacade
import org.example.bpmn.project.process.Variables
import org.example.bpmn.project.process.context.ExampleProcessContext
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/process")
class ProcessController(private val processFacade: ProcessFacade) {

    @ApiOperation(value = "старт процесса", response = Unit::class)
    @PutMapping(value = ["/start"])
    fun startProcess(): ResponseEntity<Unit> {
        val processBusinessKey = UUID.randomUUID().toString()
        val map = mapOf<String, Any>(
            Variables.BUSINESS_CONTEXT to ExampleProcessContext()
        )
        processFacade.startProcess("PROCESS_EXAMPLE", processBusinessKey, map)

        return ResponseEntity.accepted().build()
    }

    @ApiOperation(value = "отправить задачу на разработку")
    @GetMapping("/continue/send-to-dev/{processBusinessKey}")
    fun continueProcessSendDev(@PathVariable processBusinessKey: String): ResponseEntity<Unit> {
        processFacade.continueProcessByMessage(ContinuePoints.SEND_TO_DEV_RQ, processBusinessKey, Collections.emptyMap())

        return ResponseEntity.accepted().build()
    }

    @ApiOperation(value = "пнуть второго разработчика")
    @GetMapping("/continue/send-to-dev2/{processBusinessKey}")
    fun continueProcessSendDev2(@PathVariable processBusinessKey: String): ResponseEntity<Unit> {
        processFacade.continueProcessByMessage(ContinuePoints.SEND_TO_DEVELOPER_2_RQ, processBusinessKey, Collections.emptyMap())

        return ResponseEntity.accepted().build()
    }

    @ApiOperation(value = "отправить задачу на код-ревью")
    @GetMapping("/continue/send-to-code-review/{processBusinessKey}")
    fun continueProcessSendCodeReview(@PathVariable processBusinessKey: String): ResponseEntity<Unit> {
        processFacade.continueProcessByMessage(ContinuePoints.SEND_TO_CODE_REVIEW_RQ, processBusinessKey, Collections.emptyMap())

        return ResponseEntity.accepted().build()
    }


}