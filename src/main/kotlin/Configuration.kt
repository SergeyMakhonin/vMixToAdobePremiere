
class Configuration(propertyList: List<String>){
    val configMap = mutableMapOf<String, String>()
    init {
        for (key in propertyList){
            configMap[key] = ""
        }
    }

    fun setProperty(key: String, value: Any){
        configMap[key] = value.toString()
    }

    fun getProperty(key: String): String {
        return configMap[key].toString()
    }
}