
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class XmlReader (xmlPath: String){
    private val xmlFile = File(xmlPath)
    private val xmlContent: Document = readXml()
    init {
        println("XmlReader Initialized")
    }

    private fun readXml(): Document {
        println("Reading XML...")
        val builderFactory = DocumentBuilderFactory.newInstance()
        val documentBuilder = builderFactory.newDocumentBuilder()
        return documentBuilder.parse(xmlFile)
    }

    fun getEvents(): ArrayList<Event> {
        println("Getting Events...")
        // find all events
        val events: ArrayList<Event> = arrayListOf()

        // create nodes list from found xml elements
        val parsedEvents: NodeList = xmlContent.getElementsByTagName("event")

        // loop via nodes list
        for (i in 0 until parsedEvents.length) {
            val parsedEvent: Node = parsedEvents.item(i)
            if (parsedEvent.getNodeType() === Node.ELEMENT_NODE) {
                val element = parsedEvent as Element
                events.add(Event(
                    inPointStartTime = element.getElementsByTagName("inPoint").item(0).textContent.toLong(),
                    outPointStartTime = element.getElementsByTagName("outPoint").item(0).textContent.toLong(),
                    id = element.getElementsByTagName("id").item(0).textContent.toInt()
                ))
            }
        }
        return events
    }

    fun getSegments(configuredFrames: Int): ArrayList<Segment> {
        println("Getting Segments...")
        // find all events
        val segments: ArrayList<Segment> = arrayListOf()

        // create nodes list from found xml elements
        val parsedSegments: NodeList = xmlContent.getElementsByTagName("segment")

        // loop via nodes list
        for (i in 0 until parsedSegments.length) {
            val parsedSegment: Node = parsedSegments.item(i)
            if (parsedSegment.getNodeType() === Node.ELEMENT_NODE) {
                val element = parsedSegment as Element
                segments.add(Segment(
                    timeStamp = element.getElementsByTagName("timestamp").item(0).textContent,
                    videoStartTime = element.getElementsByTagName("videoStartTime").item(0).textContent.toLong(),
                    audioStartTime = element.getElementsByTagName("audioStartTime").item(0).textContent.toLong(),
                    fileName = element.getElementsByTagName("filename").item(0).textContent,
                    offline = element.getElementsByTagName("offline").item(0).textContent!!.toBoolean(),
                    configuredFrames = configuredFrames
                ))
            }
        }
        return segments
    }

    fun getEventsFromSequencedList(property: String): ArrayList<Event> {
        println("Getting Events for list #$property...")
        val events: ArrayList<Event> = arrayListOf()

        // create nodes list from found xml elements
        val parsedLists: NodeList = xmlContent.getElementsByTagName("list")

        // pick list according to property
        val pickedList: Node = parsedLists.item(property.toInt()-1)
        val eventsList = (pickedList as Element).getElementsByTagName("event")

        // loop via nodes list
        for (i in 0 until eventsList.length) {
            val parsedEvent: Node = eventsList.item(i)
            if (parsedEvent.getNodeType() === Node.ELEMENT_NODE) {
                val element = parsedEvent as Element
                events.add(Event(
                        inPointStartTime = element.getElementsByTagName("inPoint").item(0).textContent.toLong(),
                        outPointStartTime = element.getElementsByTagName("outPoint").item(0).textContent.toLong(),
                        id = element.getElementsByTagName("id").item(0).textContent.toInt()
                ))
            }
        }
        return events
    }
}
