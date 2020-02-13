data class Order(var pizzas:MutableList<Pizza> = mutableListOf(), var discount:Discount = Discount { it.price }) {

    var delivery:Boolean=false;
    var deliveryPlace:District?=null;
    val price:Double
        get() = pizzas.sumByDouble { it.price }

    val discountedPrice:Double
    get()= discount.discountFunction(this)

    val endPrice:Double
    get() = discountedPrice+ if (delivery) deliveryPlace!!.cost else 0.0
    //tego niżej nie ma bo konwencją w kotlinie jest robienie rzeczy na public zamiast getterów i setterów.
    //Javowi puryści muszą używać getPizzas() dzięki współpracy javy i kotlina i atrybutowi `data class`

//    fun appendPizza(pizza:Pizza){
//        pizzas.add(pizza)
//    }
//    fun removePizza(pizza:Pizza){
//        pizzas.remove(pizza)
//    }
//    fun getPizzas(pizza:Pizza):List<Pizza>{
//        return pizzas
//    }

}