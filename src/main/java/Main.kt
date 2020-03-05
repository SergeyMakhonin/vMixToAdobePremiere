fun main(){
    val configuredFrames = 50
    val xmlPath = "data/replay.xml"
    println("vMixToAdobePremiere started")

    // read xml
    val xmlReader = XmlReader(xmlPath)

    // get all events & segments
    val events = xmlReader.getEvents()
    val segments = xmlReader.getSegments()

    // match events between segments
    val inPointEventsBetweenSegments: ArrayList<EventBetweenSegments> = arrayListOf()
    val outPointEventsBetweenSegments: ArrayList<EventBetweenSegments> = arrayListOf()
    for (event in events){
        // inPoint and outPoint parts
        findInpointEventsBetweenSegments(event, segments)?.let { inPointEventsBetweenSegments.add(it) }
        findOutPointEventsBetweenSegments(event, segments)?.let { outPointEventsBetweenSegments.add(it) }
    }

    // write EDL file

}

