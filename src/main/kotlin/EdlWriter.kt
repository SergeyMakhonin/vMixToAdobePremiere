class EdlWriter(private val edlDir: String){
    private val timeStamp = Time().getTimeStamp()
    private val fileDescriptor = FileGrinder(edlDir, "generated_$timeStamp.edl")
    init {
        println("EDL Writer initialized")
    }

    fun writeHeader(title: String){
        println("Writing header...")
        val headerString = "TITLE: $title\nFCM: NON-DROP FRAME"
        fileDescriptor.write(headerString + "\n")
    }

    fun writeBody(title: String, inPointEvents: ArrayList<EventBetweenSegments>,
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
                val bodyLine1 = "${paddingSymbolFormatting(i, 3, '0')}  $title V     C        " +
                        "$inPointTimeCode " +
                        "$outPointTimeCode " +
                        "$timeCodeDeltaSum " +
                        "$timeCodeDelta"

                // line 2 assemble
                val timeStamp = inPointEvents[i].segmentFirstStart.timeStamp.split('.')[0].replace('T', ' ')
                val bodyLine2 =
                        "* FROM CLIP NAME: $title"

                // line 3 assemble
                val bodyLine3 = "${paddingSymbolFormatting(i, 3, '0')}  $title  A     C        " +
                        "$inPointTimeCode " +
                        "$outPointTimeCode " +
                        "$timeCodeDeltaSum " +
                        "$timeCodeDelta"

                // line 4 assemble
                val bodyLine4 = "* FROM CLIP NAME: $title"

                // line 5 assemble
                val bodyLine5 = "${paddingSymbolFormatting(i, 3, '0')}  $title  A2     C        " +
                        "$inPointTimeCode " +
                        "$outPointTimeCode " +
                        "$timeCodeDeltaSum " +
                        "$timeCodeDelta"

                // line 6 assemble
                val bodyLine6 = "* FROM CLIP NAME: $title"

                // line 7 assemble
                val bodyLine7 = "${paddingSymbolFormatting(i, 3, '0')}  $title  NONE     C        " +
                        "$inPointTimeCode " +
                        "$outPointTimeCode " +
                        "$timeCodeDeltaSum " +
                        "$timeCodeDelta"

                // line 8
                val bodyLine8 = "* FROM CLIP NAME: $title"

                // line 9
                val bodyLine9 = "AUD  3"

                // line 10
                val bodyLine10 = "${paddingSymbolFormatting(i, 3, '0')}  $title  NONE     C        " +
                        "$inPointTimeCode " +
                        "$outPointTimeCode " +
                        "$timeCodeDeltaSum " +
                        "$timeCodeDelta"

                // line 11
                val bodyLine11 = "* FROM CLIP NAME: $title"

                // line 12
                val bodyLine12 = "AUD  4"

                fileDescriptor.append(bodyLine1 + "\n")
                fileDescriptor.append(bodyLine2 + "\n")
                fileDescriptor.append("\n")
                fileDescriptor.append(bodyLine3 + "\n")
                fileDescriptor.append(bodyLine4 + "\n")
                fileDescriptor.append("\n")
                fileDescriptor.append(bodyLine5 + "\n")
                fileDescriptor.append(bodyLine6 + "\n")
                fileDescriptor.append("\n")
                fileDescriptor.append(bodyLine7 + "\n")
                fileDescriptor.append(bodyLine8 + "\n")
                fileDescriptor.append(bodyLine9 + "\n")
                fileDescriptor.append("\n")
                fileDescriptor.append(bodyLine10 + "\n")
                fileDescriptor.append(bodyLine11 + "\n")
                fileDescriptor.append(bodyLine12 + "\n")
                fileDescriptor.append("\n")

                timeCodeDeltaSum = timeCodeDeltaSum.addTimeCode(timeCodeDelta)

                // debug
                println("Iteration $i: event ID ${inPointEvents[i].event.id} matched event ID ${outPointEvents[i].event.id}")
            }
        } catch (ex: IndexOutOfBoundsException){
            println("InPoint (${inPointEvents.size}) and OutPoint (${outPointEvents.size}) events quantities are not equal")
        }
    }
}