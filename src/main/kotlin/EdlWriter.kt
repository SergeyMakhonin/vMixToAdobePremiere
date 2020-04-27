class EdlWriter(private val xmlPath: String){
    private val fileDescriptor = FileGrinder(xmlPath)
    init {
        println("EDL Writer initialized")
    }

    fun writeHeader(title: String){
        println("Writing header...")
        val headerString = "TITLE: $title\nFCM: NON-DROP FRAME"
        fileDescriptor.write(headerString + "\n")
    }

    fun writeBody(inPointEvents: ArrayList<EventBetweenSegments>,
                  outPointEvents: ArrayList<EventBetweenSegments>){
        println("Writing body...")

        // in case length is not equal only matching events will be processed
        // loop via eventsBetweenSegments
        try {
            fileDescriptor.append("\n")
            var timeCodeDeltaSum: TimeCode = TimeCode(0, 0, 0, 0)
            for (i in 0 until inPointEvents.size) {

                // line 1 assemble
                val inPointTimeCode = inPointEvents[i].segmentFirstStart.timeStampTimeCode.addTimeCode(inPointEvents[i].timeCodeDiffEventVsSegments)
                val outPointTimeCode = outPointEvents[i].segmentFirstStart.timeStampTimeCode.addTimeCode(outPointEvents[i].timeCodeDiffEventVsSegments)
                val timeCodeDelta = outPointTimeCode.subtractTimeCode(inPointTimeCode)
                val bodyLine1 = "${paddingSymbolFormatting(i, 3, '0')}  CDKMN24C V     C        " +
                        "$inPointTimeCode " +
                        "$outPointTimeCode " +
                        "$timeCodeDeltaSum " +
                        "$timeCodeDelta"

                // line 2 assemble
                val timeStamp = inPointEvents[i].segmentFirstStart.timeStamp.split('.')[0].replace('T', ' ')
                val bodyLine2 =
                        "* FROM CLIP NAME: WOL LIV  $timeStamp (0DD69896-BFC1-4BBF-BF14-80FA194AFB52).mov"
                fileDescriptor.append(bodyLine1 + "\n")
                fileDescriptor.append(bodyLine2 + "\n")
                timeCodeDeltaSum = timeCodeDeltaSum.addTimeCode(timeCodeDelta)

                // debug
                println("Iteration $i: event ID ${inPointEvents[i].event.id} matched event ID ${outPointEvents[i].event.id}")
            }
        } catch (ex: IndexOutOfBoundsException){
            println("InPoint (${inPointEvents.size}) and OutPoint (${outPointEvents.size}) events quantities are not equal")
        }
    }
}