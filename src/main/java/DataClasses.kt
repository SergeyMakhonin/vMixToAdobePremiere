data class Event(
    val inPointStartTime: Long,
    val outPointStartTime: Long,
    val id: Int
)

data class Segment(
    val timeStamp: String,
    val videoStartTime: Long,
    val audioStartTime: Long,
    val fileName: String,
    val offline: Boolean
)

data class EventBetweenSegments(
    val event: Event,
    val segmentFirstStart: Segment,
    val segmentSecondStart: Segment
)