import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import java.io.File

fun main(vararg configInput: String){

    // reading properties from file
    println("Reading configuration...")
    val configuredFrames = Key("configuredFrames", intType)
    val xmlPath = Key("xmlPath", stringType)
    val edlPath = Key("edlPath", stringType)
    val title = Key("title", stringType)
    val titleNumber = Key("titleNumber", stringType)
    val config = systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromFile(File("config.properties"))

    // check if manual settings given
    if (configInput.isNotEmpty()){
        println("Applying GUI input...")
        config[configuredFrames]
    }

    println("vMixToAdobePremiere started")

    // read xml
    val xmlReader = XmlReader(config[xmlPath])

    // get all events & segments
    val events = xmlReader.getEvents()
    val segments = xmlReader.getSegments(config[configuredFrames])

    // match events between segments
    val inPointEventsBetweenSegments: ArrayList<EventBetweenSegments> = arrayListOf()
    val outPointEventsBetweenSegments: ArrayList<EventBetweenSegments> = arrayListOf()
    println("Matching Events between Segments...")
    for (event in events){
        // inPoint and outPoint parts
        findInPointEventsBetweenSegments(event, segments, config[configuredFrames])?.let { inPointEventsBetweenSegments.add(it) }
        findOutPointEventsBetweenSegments(event, segments, config[configuredFrames])?.let { outPointEventsBetweenSegments.add(it) }
    }

    // write EDL file
    val edlWriter = EdlWriter(config[edlPath])
    edlWriter.writeHeader(config[title].replace("XX", config[titleNumber]))
    edlWriter.writeBody(inPointEvents = inPointEventsBetweenSegments, outPointEvents = outPointEventsBetweenSegments)
}

