data class Ciasto(val name:String, val price:Double)
data class Pizza(val name:String, var ciasto:Ciasto, val ingredients:MutableList<Ingredient>) {
    val price:Double
    get()= ingredients.sumByDouble { it.price } + ciasto.price
    fun asString():String{
        return "${name.capitalize()}, ${ciasto.name} - ${ingredients.joinToString { it.name }}"
    }
}