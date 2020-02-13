data class Ingredient(val name:String, val price:Double) {
    fun asString():String{
        return "${name} - ${price}"
    }
}