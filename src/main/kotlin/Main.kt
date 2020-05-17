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
    val channel = Key("titleNumber", stringType)
    val config = systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromFile(File("config.properties"))

    // check if manual settings given and configure
    val finalConfig = Configuration(listOf<String>("configuredFrames", "xmlPath", "edlPath", "title", "channel"))
    if (configInput.isNotEmpty()){
        println("Applying GUI input...")

        // configuredFrames
        if (configInput[0] == ""){
            finalConfig.setProperty("configuredFrames", config[configuredFrames])
        } else{
            finalConfig.setProperty("configuredFrames", configInput[0])
        }

        // channel
        if (configInput[1] == ""){
            finalConfig.setProperty("channel", config[channel])
        } else{
            finalConfig.setProperty("channel", configInput[1])
        }
    }
    finalConfig.setProperty("xmlPath", config[xmlPath])
    finalConfig.setProperty("edlPath", config[edlPath])
    finalConfig.setProperty("title", config[title].replace("XX", finalConfig.getProperty("channel")))

    println("vMixToAdobePremiere started")

    // read xml
    val xmlReader = XmlReader(finalConfig.getProperty("xmlPath"))

    // get all events & segments
    val events = xmlReader.getEvents()
    val segments = xmlReader.getSegments(finalConfig.getProperty("configuredFrames").toInt())

    // match events between segments
    val inPointEventsBetweenSegments: ArrayList<EventBetweenSegments> = arrayListOf()
    val outPointEventsBetweenSegments: ArrayList<EventBetweenSegments> = arrayListOf()
    println("Matching Events between Segments...")
    for (event in events){
        // inPoint and outPoint parts
        findInPointEventsBetweenSegments(event, segments, finalConfig.getProperty("configuredFrames").toInt())?.let { inPointEventsBetweenSegments.add(it) }
        findOutPointEventsBetweenSegments(event, segments, finalConfig.getProperty("configuredFrames").toInt())?.let { outPointEventsBetweenSegments.add(it) }
    }

    // write EDL file
    val edlWriter = EdlWriter(finalConfig.getProperty("edlPath"))
    edlWriter.writeHeader(finalConfig.getProperty("title"))
    edlWriter.writeBody(title = finalConfig.getProperty("title"), inPointEvents = inPointEventsBetweenSegments, outPointEvents = outPointEventsBetweenSegments)
}

