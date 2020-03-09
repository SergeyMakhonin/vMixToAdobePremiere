import java.io.File

class FileWriter(filePath: String) {
    private var filePath: String
        get() {
            return filePath
        }
        set(filePath: String){
            this.filePath = filePath
        }
    fun write(line: String){
        File(filePath).printWriter().use { out ->
            out.println(line)
        }
    }
}