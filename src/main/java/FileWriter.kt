import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption

class FileWriter(val filePath: String) {

    fun write(line: String){
        File(filePath).printWriter().use { out ->
            out.write(line)
        }
    }
    fun append(line: String){
        val myFile = File(filePath)
        Files.write(myFile.toPath(), line.toByteArray(), StandardOpenOption.APPEND)
    }
}