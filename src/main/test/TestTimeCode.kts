class TestTimeCode(){
    fun testMinus(firstValue: TimeCode, secondValue:TimeCode): TimeCode {
        println("$firstValue - $secondValue")
        val result = firstValue.subtractTimeCode(secondValue)
        println("Result: $result")
        return result
    }
    fun testPlus(firstValue: TimeCode, secondValue:TimeCode): TimeCode {
        println("$firstValue + $secondValue")
        val result = firstValue.addTimeCode(secondValue)
        println("Result: $result")
        return result
    }
}

val testObject = TestTimeCode()

// minus
println("Testing minus")
testObject.testMinus(TimeCode(1, 0, 0, 0), TimeCode(23, 0, 0, 0))
testObject.testMinus(TimeCode(1, 0, 0, 0), TimeCode(1, 30, 0, 0))
testObject.testMinus(TimeCode(1, 0, 0, 0), TimeCode(1, 30, 10, 0))
testObject.testMinus(TimeCode(1, 0, 0, 0), TimeCode(1, 30, 10, 10))
println("")
println("Testing plus")
testObject.testPlus(TimeCode(1, 0, 0, 0), TimeCode(23, 0, 0, 0))
testObject.testPlus(TimeCode(1, 50, 0, 0), TimeCode(1, 30, 0, 0))
testObject.testPlus(TimeCode(1, 0, 55, 0), TimeCode(1, 30, 10, 0))
testObject.testPlus(TimeCode(1, 59, 59, 24), TimeCode(23, 30, 10, 10))
