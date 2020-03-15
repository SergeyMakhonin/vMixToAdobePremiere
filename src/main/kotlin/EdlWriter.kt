class EdlWriter(val xmlPath: String){
    val fileDescriptor = FileGrinder(xmlPath)
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

        // check length is equal
        if (inPointEvents.size == outPointEvents.size){
            fileDescriptor.append("\n")
            var timeCodeDeltaSum: TimeCode = TimeCode(0, 0, 0, 0)

            // loop via eventsBetweenSegments
            for (i in 0 until inPointEvents.size) {
                val inPointTimeCode = inPointEvents[i].segmentFirstStart.timeStampTimeCode.addTimeCode(inPointEvents[i].timeCodeDiffEventVsSegments)
                val outPointTimeCode = outPointEvents[i].segmentFirstStart.timeStampTimeCode.addTimeCode(outPointEvents[i].timeCodeDiffEventVsSegments)
                val timeCodeDelta = outPointTimeCode.subtractTimeCode(inPointTimeCode)
                val bodyLine1 = "XXX  CDKMN24C V     C        " +
                                "$inPointTimeCode " +
                                "$outPointTimeCode " +
                                "$timeCodeDeltaSum " +
                                "$timeCodeDelta"
                val bodyLine2 =
                    "* FROM CLIP NAME: WOL LIV 23.01.2020 21-30-00 (0DD69896-BFC1-4BBF-BF14-80FA194AFB52).mov"
                fileDescriptor.append(bodyLine1 + "\n")
                fileDescriptor.append(bodyLine2 + "\n")
                timeCodeDeltaSum = timeCodeDeltaSum.addTimeCode(timeCodeDelta)
            }
        }
        else{
            println("InPoint and OutPoint events quantities are not equal")
        }
    }
}