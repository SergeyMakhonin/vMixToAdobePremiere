import java.io.File

class FileWriter(val filePath: String) {

    fun write(line: String){
        File(filePath).printWriter().use { out ->
            out.println(line)
        }
    }
}