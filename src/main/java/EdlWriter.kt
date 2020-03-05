import java.io.File

class EdlWrited(val xmlPath: String){
    val fileDescriptor = File(xmlPath).printWriter()
    init {
        println("EDL Writer initialized")
    }

    fun writeHeader(){
        println("Writing header...")
    }
}