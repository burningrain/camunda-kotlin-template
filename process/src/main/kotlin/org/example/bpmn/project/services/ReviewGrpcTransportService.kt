package org.example.bpmn.project.services

import io.grpc.Channel
import org.example.bpmn.developer.review.api.ReviewGrpc
import org.example.bpmn.developer.review.api.ReviewV10

class ReviewGrpcTransportService(
    private val channel: Channel
) : TransportService<ReviewV10.SendToReviewRequest, ReviewV10.SendToReviewResponse> {

    private val newBlockingStub = ReviewGrpc.newBlockingStub(channel)

    override suspend fun send(request: ReviewV10.SendToReviewRequest): ReviewV10.SendToReviewResponse {
        return newBlockingStub.sendToCodeReview(request)
    }

}