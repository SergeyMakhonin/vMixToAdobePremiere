data class Segment(
    val timeStamp: String,
    val videoStartTime: Long,
    val audioStartTime: Long,
    val fileName: String,
    val offline: Boolean,
    val configuredFrames: Int,
    val timeStampTimeCode: TimeCode = timeStampToTimeCode(timeStamp, configuredFrames)
)

// timeStamp example 2020-01-29T23:15:49.0003729+03:00
fun timeStampToTimeCode(timeStamp: String, configuredFrames: Int): TimeCode {
    val time: String = timeStamp.split("T", limit = 2)[1]
    val timeParts = time.split(":", limit = 3)
    val secondsDotFrames = timeParts[2].split(".", limit = 2)
    val seconds = secondsDotFrames[0].toInt()
    val frames = secondsDotFrames[1].split("+")[0].toInt()*configuredFrames/1000
    val tzDelta = secondsDotFrames[1].split("+")[1]
    return TimeCode(timeParts[0].toInt(), timeParts[1].toInt(), seconds, frames)
}
