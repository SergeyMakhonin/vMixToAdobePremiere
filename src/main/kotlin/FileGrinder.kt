import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption

class FileGrinder(dirPath: String, fileName: String) {

    private val fullPath = "$dirPath/${fileName}"

    init {
        // ensure dir exists
        val directory = File(dirPath);
        if (! directory.exists()) {
            directory.mkdir()
        }

        // ensure file will be there
        val edlFile = File(fullPath)
        edlFile.createNewFile()
    }

    fun write(line: String){
        File(fullPath).printWriter().use { out ->
            out.write(line)
        }
    }
    fun append(line: String){
        val myFile = File(fullPath)
        Files.write(myFile.toPath(), line.toByteArray(), StandardOpenOption.APPEND)
    }

}