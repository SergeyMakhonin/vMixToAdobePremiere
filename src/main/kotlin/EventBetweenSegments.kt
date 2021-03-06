data class EventBetweenSegments(
    val event: Event,
    val segmentFirstStart: Segment,
    val segmentSecondStart: Segment,
    val configuredFrames: Int,
    val timeDiffEventVsSegments: Long,
    val timeCodeDiffEventVsSegments: TimeCode
)

fun findOutPointEventsBetweenSegments(
    event: Event,
    segments: ArrayList<Segment>,
    configuredFrames: Int
): EventBetweenSegments? {
    for (i in 0 until segments.size) {
    try {
            if ((event.outPointStartTime > segments[i].videoStartTime)
                    and
                    (event.outPointStartTime < segments[i + 1].videoStartTime)
            ) {
                val timeDiffEventVsSegments = event.outPointStartTime - segments[i].videoStartTime
                return EventBetweenSegments(event = event,
                        segmentFirstStart = segments[i],
                        segmentSecondStart = segments[i + 1],
                        configuredFrames = configuredFrames,
                        timeDiffEventVsSegments = timeDiffEventVsSegments,
                        timeCodeDiffEventVsSegments = evaluateTimeCode(timeDiffEventVsSegments.toFloat(), configuredFrames))
            }
    } catch (ex: IndexOutOfBoundsException) {
        println("Reached the end of the Segments in vMix XML file while matching to OutPointEvent")
            val timeDiffEventVsSegments = event.outPointStartTime - segments[i].videoStartTime
            return EventBetweenSegments(event = event,
                    segmentFirstStart = segments[i],
                    segmentSecondStart = segments[i],
                    configuredFrames = configuredFrames,
                    timeDiffEventVsSegments = timeDiffEventVsSegments,
                    timeCodeDiffEventVsSegments = evaluateTimeCode(timeDiffEventVsSegments.toFloat(), configuredFrames))

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
        try {
            if ((event.inPointStartTime > segments[i].videoStartTime)
                    and
                    (event.inPointStartTime < segments[i + 1].videoStartTime)
            ) {
                val timeDiffEventVsSegments = event.inPointStartTime - segments[i].videoStartTime
                return EventBetweenSegments(event = event,
                        segmentFirstStart = segments[i],
                        segmentSecondStart = segments[i + 1],
                        configuredFrames = configuredFrames,
                        timeDiffEventVsSegments = timeDiffEventVsSegments,
                        timeCodeDiffEventVsSegments = evaluateTimeCode(timeDiffEventVsSegments.toFloat(), configuredFrames))
            }
        } catch (ex: IndexOutOfBoundsException) {
            println("Reached the end of the Segments in vMix XML file while matching to InPointEvent")
                val timeDiffEventVsSegments = event.inPointStartTime - segments[i].videoStartTime
                return EventBetweenSegments(event = event,
                        segmentFirstStart = segments[i],
                        segmentSecondStart = segments[i],
                        configuredFrames = configuredFrames,
                        timeDiffEventVsSegments = timeDiffEventVsSegments,
                        timeCodeDiffEventVsSegments = evaluateTimeCode(timeDiffEventVsSegments.toFloat(), configuredFrames))
        }
    }
    return null
}