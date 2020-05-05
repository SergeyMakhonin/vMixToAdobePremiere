import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Time{
    fun getTimeStamp(): String? {
        return DateTimeFormatter
                .ofPattern("yyyy-MM-dd_HH-mm-ss")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())

    }
}

