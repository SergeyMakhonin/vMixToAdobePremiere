data class EventBetweenSegments(
    val event: Event,
    val segmentFirstStart: Segment,
    val segmentSecondStart: Segment
)

fun findOutPointEventsBetweenSegments(
    event: Event,
    segments: ArrayList<Segment>
): EventBetweenSegments? {
    for (i in 0 until segments.size) {
        if ((event.outPointStartTime > segments[i].videoStartTime)
            and
            (event.outPointStartTime < segments[i + 1].videoStartTime)
        ) {
            return EventBetweenSegments(event = event, segmentFirstStart = segments[i], segmentSecondStart = segments[i + 1])
        }
    }
    return null
}

fun findInpointEventsBetweenSegments(
    event: Event,
    segments: ArrayList<Segment>
): EventBetweenSegments? {
    for (i in 0 until segments.size) {
        if ((event.inPointStartTime > segments[i].videoStartTime)
            and
            (event.inPointStartTime < segments[i + 1].videoStartTime)
        ) {
            return EventBetweenSegments(event = event, segmentFirstStart = segments[i], segmentSecondStart = segments[i + 1])
        }
    }
    return null
}