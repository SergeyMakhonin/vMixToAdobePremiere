data class EventBetweenSegments(
    val event: Event,
    val segmentFirstStart: Segment,
    val segmentSecondStart: Segment,
    val configuredFrames: Int,
    val startTimeDiffEventVsSegments: Long = event.inPointStartTime - segmentFirstStart.videoStartTime,
    val timeCodeStartTimeDiffEventVsSegments: TimeCode = evaluateTimeCode(startTimeDiffEventVsSegments.toFloat(), configuredFrames)
)

fun findOutPointEventsBetweenSegments(
    event: Event,
    segments: ArrayList<Segment>,
    configuredFrames: Int
): EventBetweenSegments? {
    for (i in 0 until segments.size) {
        if ((event.outPointStartTime > segments[i].videoStartTime)
            and
            (event.outPointStartTime < segments[i + 1].videoStartTime)
        ) {
            return EventBetweenSegments(event = event,
                                        segmentFirstStart = segments[i],
                                        segmentSecondStart = segments[i + 1],
                                        configuredFrames =  configuredFrames)
        }
    }
    return null
}

fun findInPointEventsBetweenSegments(
    event: Event,
    segments: ArrayList<Segment>,
    configuredFrames: Int
): EventBetweenSegments? {
    for (i in 0 until segments.size) {
        if ((event.inPointStartTime > segments[i].videoStartTime)
            and
            (event.inPointStartTime < segments[i + 1].videoStartTime)
        ) {
            return EventBetweenSegments(event = event,
                                        segmentFirstStart = segments[i],
                                        segmentSecondStart = segments[i + 1],
                                        configuredFrames = configuredFrames)
        }
    }
    return null
}