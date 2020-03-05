private fun evaluateTimeCode(
    startTimeDiffEventVsSegment: Float,
    configuredFrames: Int
): TimeCode {
    // timeCode seconds, frames, minutes, hours
    val secondsInTotal = startTimeDiffEventVsSegment / 10000000
    val frames: Int = ((secondsInTotal - secondsInTotal.toInt().toFloat()) * configuredFrames).toInt()
    val hours: Int = secondsInTotal.toInt() / 3600

    // need helper val to calculate seconds and minutes
    val minutesAndSecondsHelper = secondsInTotal.toFloat() / 3600F

    val minutes: Float = (minutesAndSecondsHelper - minutesAndSecondsHelper.toInt().toFloat()) * 60F
    val seconds: Int = ((minutes - minutes.toInt().toFloat()) * 60F).toInt()
    return TimeCode(hours, minutes.toInt(), seconds, frames)
}