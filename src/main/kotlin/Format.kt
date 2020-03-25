fun paddingSymbolFormatting(digitalValue: Int, outputValueLength: Int, padSymbol: Char): String {
    var stringDigits = digitalValue.toString()
    if (stringDigits.length < outputValueLength) {
        for (i in 0 until outputValueLength-stringDigits.length){
            stringDigits = "$padSymbol$stringDigits"
        }
    }
    return stringDigits
}
