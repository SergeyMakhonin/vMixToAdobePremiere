import java.io.File

class EdlWriter(val xmlPath: String){
    val fileDescriptor = FileWriter(xmlPath)
    init {
        println("EDL Writer initialized")
    }

    fun writeHeader(){
        println("Writing header...")
        val headerString = ""
        fileDescriptor.write(headerString)
    }

    fun writeBody(){
        println("Writing body...")
    }
}