package org.example.bpmn.project.camunda.optimization

import org.camunda.bpm.engine.impl.history.event.HistoryEvent
import org.camunda.bpm.engine.impl.history.handler.DbHistoryEventHandler
import org.springframework.stereotype.Component

@Component
class CamundaFilterHistoryEventHandler: DbHistoryEventHandler() {

    override fun handleEvent(historyEvent: HistoryEvent?) {
        //do nothing
    }

}