fun main(){
    val configuredFrames = 50
    val xmlPath = "data/replay.xml"
    println("vMixToAdobePremiere started")

    // read xml
    val xmlReader = XmlReader(xmlPath)
    val events = xmlReader.getEvents()

    // get all events

    // get all segments

    // match events between segments

    // write EDL file

}