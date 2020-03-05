class TimeCode(var hours: Int, var minutes: Int, var seconds: Int, private val frames: Int){

    fun substractTimeCode(valueToSubstract: TimeCode): TimeCode{
        var newSeconds: Int
        var newMinutes: Int
        var newHours: Int

        // frames
        var newFrames = frames - valueToSubstract.frames
        if (newFrames < 0){
            newFrames = 50 - newFrames
            seconds--
        }
        //seconds
        newSeconds = seconds - valueToSubstract.seconds
        if (newSeconds < 0){
            newSeconds = 60 - newSeconds
            minutes--
        }
        // minutes
        newMinutes = minutes - valueToSubstract.minutes
        if (newMinutes < 0){
            newMinutes = 60 - newMinutes
            hours--
        }
        // hours
        newHours = hours - valueToSubstract.hours
        if (newHours < 0){
            newHours = 60 - newHours
            println("Hour limit exceeded while substracting TimeCodes")
        }
        return TimeCode(newHours, newMinutes, newSeconds, newFrames)
    }

    override fun toString(): String {
        return "${twoSymbolFormatting(hours)}:${twoSymbolFormatting(minutes)}:${twoSymbolFormatting(seconds)}:${twoSymbolFormatting(frames)}"
    }

    private fun twoSymbolFormatting(digits: Int): String {
        var stringDigits = digits.toString()
        if (stringDigits.length < 2) {
            stringDigits = "0$stringDigits"
        }
        return stringDigits
    }
}

data class Event(
    val inPointStartTime: Long,
    val outPointStartTime: Long,
    val id: Int
)

data class Segment(
    val timeStamp: String,
    val videoStartTime: Long,
    val fileName: String
)

data class EventBetweenSegments(
    val event: Event,
    val segmentFirstStart: Segment,
    val segmentSecondStart: Segment
)