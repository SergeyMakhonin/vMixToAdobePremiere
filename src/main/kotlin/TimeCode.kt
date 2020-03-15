import kotlin.math.abs

class TimeCode(var hours: Int, var minutes: Int, var seconds: Int, private val frames: Int){

    fun subtractTimeCode(valueToSubtract: TimeCode): TimeCode {
        var newSeconds: Int
        var newMinutes: Int
        var newHours: Int

        // frames
        var newFrames = frames - valueToSubtract.frames
        if (newFrames < 0){
            newFrames = 49 - abs(newFrames)
            seconds--
        }
        //seconds
        newSeconds = seconds - valueToSubtract.seconds
        if (newSeconds < 0){
            newSeconds = 59 - abs(newSeconds)
            minutes--
        }
        // minutes
        newMinutes = minutes - valueToSubtract.minutes
        if (newMinutes < 0){
            newMinutes = 59 - abs(newMinutes)
            hours--
        }
        // hours
        newHours = hours - valueToSubtract.hours
        if (newHours < 0){
            newHours = 23 - abs(newHours)
            println("Hour limit exceeded while substracting TimeCodes")
        }
        return TimeCode(newHours, newMinutes, newSeconds, newFrames)
    }

    fun addTimeCode(valueToAdd: TimeCode): TimeCode{

        // frames
        var newFrames = frames + valueToAdd.frames
        if (newFrames > 49){
            newFrames -= 49
            seconds++
        }
        //seconds
        var newSeconds: Int = seconds + valueToAdd.seconds
        if (newSeconds > 59){
            newSeconds -= 59
            minutes++
        }
        // minutes
        var newMinutes: Int = minutes + valueToAdd.minutes
        if (newMinutes > 59){
            newMinutes -= 59
            hours++
        }
        // hours
        var newHours: Int = hours + valueToAdd.hours
        if (newHours > 23){
            newHours -= 23
            println("Hour limit exceeded while summing TimeCodes")
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
    var hours: Int = secondsInTotal.toInt() / 3600
    // keep hours under 24
    if (hours >= 24){
        hours=-23
        println("Resolving TimeCode for $nanoseconds gave more than 23 hours, lowered to $hours")
    }

    // need helper val to calculate seconds and minutes
    val minutesAndSecondsHelper = secondsInTotal.toFloat() / 3600F

    val minutes: Float = (minutesAndSecondsHelper - minutesAndSecondsHelper.toInt().toFloat()) * 60F
    val seconds: Int = ((minutes - minutes.toInt().toFloat()) * 60F).toInt()
    return TimeCode(hours, minutes.toInt(), seconds, frames)
}