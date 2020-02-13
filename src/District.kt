data class District(val name:String, val cost:Double) {
    fun asString():String{
        return "${name} - ${cost}zł"
    }
}