class TimeCode(var hours: Int, var minutes: Int, var seconds: Int, private val frames: Int){

    fun substractTimeCode(valueToSubstract: TimeCode): TimeCode {
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

fun evaluateTimeCode(
    nanoseconds: Float,
    configuredFrames: Int
): TimeCode {
    // timeCode seconds, frames, minutes, hours
    val secondsInTotal = nanoseconds / 10000000
    val frames: Int = ((secondsInTotal - secondsInTotal.toInt().toFloat()) * configuredFrames).toInt()
    val hours: Int = secondsInTotal.toInt() / 3600

    // need helper val to calculate seconds and minutes
    val minutesAndSecondsHelper = secondsInTotal.toFloat() / 3600F

    val minutes: Float = (minutesAndSecondsHelper - minutesAndSecondsHelper.toInt().toFloat()) * 60F
    val seconds: Int = ((minutes - minutes.toInt().toFloat()) * 60F).toInt()
    return TimeCode(hours, minutes.toInt(), seconds, frames)
}