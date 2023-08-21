package org.example.bpmn.project.process

object Transitions {

    val EMPTY = Transition("EMPTY")

    val TO_WAITING_DAILY_END = Transition("TO_WAITING_DAILY_END")
    val TO_TESTING = Transition("TO_TESTING")
    val TO_PROD = Transition("TO_PROD")
    val TO_UNPROD = Transition("TO_UNPROD")
    val TO_TALK_WITH_ANALYST = Transition("TO_TALK_WITH_ANALYST")
    val TO_TESTING_AGAIN = Transition("TO_TESTING_AGAIN")
    val TO_DEVELOPMENT = Transition("TO_DEVELOPMENT")
    val TO_REFRESH_THE_ANALYZE = Transition("TO_REFRESH_THE_ANALYZE")
    val TO_DEVELOPMENT_AGAIN = Transition("TO_DEVELOPMENT_AGAIN")
    val TO_ANALYZE_AGAIN = Transition("TO_ANALYZE_AGAIN")

}