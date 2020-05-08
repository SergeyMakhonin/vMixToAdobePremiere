import kotlin.math.abs

class TimeCode(var hours: Int, var minutes: Int, var seconds: Int, private val frames: Int){

    fun subtractTimeCode(valueToSubtract: TimeCode): TimeCode {
        var newSeconds = seconds
        var newMinutes = minutes
        var newHours = hours

        // frames
        var newFrames = frames - valueToSubtract.frames
        if (newFrames < 0){
            newFrames = 25 - abs(newFrames)
            newSeconds -= 1
        }
        //seconds
        newSeconds = seconds - valueToSubtract.seconds
        if (newSeconds < 0){
            newSeconds = 60 - abs(newSeconds)
            newMinutes -=1
        }
        // minutes
        newMinutes = minutes - valueToSubtract.minutes
        if (newMinutes < 0){
            newMinutes = 60 - abs(newMinutes)
            newHours -=1
        }
        // hours
        newHours = hours - valueToSubtract.hours
        if (newHours < 0){
            newHours = 24 - abs(newHours)
            println("Hour limit exceeded while substracting TimeCodes")
        }
        return TimeCode(newHours, newMinutes, newSeconds, newFrames)
    }

    fun addTimeCode(valueToAdd: TimeCode): TimeCode{
        var newSeconds = seconds
        var newMinutes = minutes
        var newHours = hours

        // frames
        var newFrames = frames + valueToAdd.frames
        if (newFrames >= 25){
            newFrames -= 25
            newSeconds +=1
        }
        //seconds
        newSeconds = newSeconds + valueToAdd.seconds
        if (newSeconds >= 60){
            newSeconds -= 60
            newMinutes +=1
        }
        // minutes
        newMinutes = minutes + valueToAdd.minutes
        if (newMinutes >= 60){
            newMinutes -= 60
            newHours +=1
        }
        // hours
        newHours = hours + valueToAdd.hours
        if (newHours >= 24){
            newHours -= 24
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