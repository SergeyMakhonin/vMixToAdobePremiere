import java.io.File

class EdlWriter(val xmlPath: String){
    val fileDescriptor = FileWriter(xmlPath)
    init {
        println("EDL Writer initialized")
    }

    fun writeHeader(title: String){
        println("Writing header...")
        val headerString = "TITLE: $title\nFCM: NON-DROP FRAME"
        fileDescriptor.write(headerString)
    }

    fun writeBody(){
        println("Writing body...")
    }
}