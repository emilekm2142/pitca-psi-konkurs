class Interface(){
    lateinit var currentView:View
    fun loop(){
        currentView.draw()
        val l = readLine()
        when(l){
            "k"->currentView.down()
            "i"->currentView.up()
            "o"->currentView.ok()
            "j"->currentView.left()
            "l"->currentView.right()
        }

    }
    fun changeView(view: View){
        currentView=view;
        currentView.draw()
    }
    fun cls(){
        //windows ma uposledzone cmd
        for (clear in 0..300) {
            println("\b")
        }
    }
}
abstract  class View(val i:Interface, val name:String){
    open fun draw(){
        i.cls()
        println("----PITCA-----")
        println("i - góra, k-dół, o-ok. Po wciśnięciu literki klepnij enter")
        println(name)
    }
    var currentIndex:Int=0
    open fun down(){}
    open fun up(){}
    open fun ok(){}
    open fun right(){}
    open fun left(){}
}
val i = Interface()
class MainView(i:Interface):View(i,"Wybierz pitcę"){
    val pizzasList = mutableListOf<Pizza>()
    val selectedPizzas = mutableListOf<Pizza>()
    val extraOptionsList = mutableListOf<Pair<String, ()->Unit>>(Pair("Customizacja pitc", {i.changeView(OrderView(i,selectedPizzas))}))
    init{
        for (pizza in Pizzeria.PizzasPresets.all) {
            pizzasList.add(pizza())
        }

    }

    override fun draw() {
        super.draw()
        println("Zamówienie: ${selectedPizzas.joinToString { it.name }}")

        for ((i,pizza) in pizzasList.withIndex()){
            println("${if (i==currentIndex) '>' else ' ' } ${pizza.name.capitalize()} - ${pizza.ingredients.joinToString { it.name }}")
        }
        for ((i,extra) in extraOptionsList.withIndex()){
            val o = i+pizzasList.size;
            println("${if (o==currentIndex) '>' else ' ' } ${extra.first}")
        }
    }

    override fun down() {
        currentIndex++;
        if (currentIndex>=Pizzeria.PizzasPresets.all.size +extraOptionsList.size) currentIndex=0;
    }

    override fun up() {
        currentIndex--;
        if (currentIndex<0) currentIndex=Pizzeria.PizzasPresets.all.size-1   +extraOptionsList.size;

    }

    override fun ok() {
        if (currentIndex<=pizzasList.size-1) {
            selectedPizzas.add(Pizzeria.PizzasPresets.all[currentIndex]());
        }
        else {
            extraOptionsList[currentIndex-pizzasList.size].second();
        }
    }
}
class OrderView(i:Interface, val selectedPizzas:MutableList<Pizza>):View(i, "Customizacja i zamawianie pitcy"){
    val order:Order = Order(selectedPizzas)
    var delivery=false
        set(a:Boolean) {
            extraOptionsList[1] =extraOptionsList[1].copy(first="${if (a) "Nie" else ""} chcę z dowozem")
            field=a
            order.delivery=a
        }
    var student=false
        set(a:Boolean) {
            extraOptionsList[0] =extraOptionsList[0].copy(first="${if (a) "Nie" else ""} jestem studentem")
            field=a
        }
    var extraOptionsList = mutableListOf<Pair<String, ()->Unit>>(
        Pair("Jestem studentem", {student= !student; order.discount = if (student) Pizzeria.Discounts.students else Pizzeria.Discounts.none}),
        Pair("Chcę z dowozem", {delivery=!delivery; if (delivery) i.changeView(DeliveryView(i,order,this)) } ),
        Pair("Luksja zamawianko :DD", {i.changeView(MainView(i))} )
    )
    override fun draw() {
        super.draw()
        println("Wejdź w pickę, żeby edytować składniki")
        for ((i,pizza) in order.pizzas.withIndex()){
            println("${if (i==currentIndex) '>' else ' ' } ${pizza.asString()}")
        }
        println("Cena bazowa: ${order.price}")
        println("\n Dodatkowe opcje i finalizacja:")
        for ((i,extra) in extraOptionsList.withIndex()){
            val o = i+selectedPizzas.size;
            println("${if (o==currentIndex) '>' else ' ' } ${extra.first}")
        }
        if (student)
            println("Cena ze zniżką: ${order.discountedPrice}")
        println("Cena końcowa: ${order.endPrice}")
    }

    override fun ok() {
        if (currentIndex<=selectedPizzas.size-1) {
            i.changeView(IngridientEditView(i,order, selectedPizzas[currentIndex], this))
        }
        else {
            extraOptionsList[currentIndex-selectedPizzas.size].second();
        }
    }
    override fun down() {
        currentIndex++;
        if (currentIndex>=Pizzeria.PizzasPresets.all.size +extraOptionsList.size) currentIndex=0;
    }

    override fun up() {
        currentIndex--;
        if (currentIndex<0) currentIndex=Pizzeria.PizzasPresets.all.size-1   +extraOptionsList.size;

    }
}

class IngridientEditView(i:Interface, val order:Order, val pizza: Pizza, val previousScreen:View):View(i, "customizacja"){
    val extraOptionsList = mutableListOf<Pair<String, ()->Unit>>(
        Pair("Ciasto grube", {pizza.ciasto = Pizzeria.Ciasta.grube}),
        Pair("Ciasto cienkie", {pizza.ciasto = Pizzeria.Ciasta.cienkie}),
        Pair("OK, spoko picka", {i.changeView(previousScreen)}))
    override fun draw() {
        super.draw()
        println(pizza.asString())
        for ((i,ingr) in Pizzeria.Ingredients.all.withIndex()){
            println("${if (i==currentIndex) '>' else ' ' } ${ingr.asString()}")
        }
        for ((i,extra) in extraOptionsList.withIndex()){
            val o = i+Pizzeria.Ingredients.all.size;
            println("${if (o==currentIndex) '>' else ' ' } ${extra.first}")
        }
        println("\n Cena: ${pizza.price}")
    }

    override fun ok() {
        if (currentIndex<- Pizzeria.Ingredients.all.size-1) {
            pizza.ingredients.add(Pizzeria.Ingredients[currentIndex])
        }
        else {
            extraOptionsList[currentIndex- Pizzeria.Ingredients.all.size].second();
        }
    }
    override fun down() {
        currentIndex++;
        if (currentIndex>=Pizzeria.Ingredients.all.size +extraOptionsList.size) currentIndex=0;
    }

    override fun up() {
        currentIndex--;
        if (currentIndex<0) currentIndex=Pizzeria.Ingredients.all.size-1   +extraOptionsList.size;

    }
}
class DeliveryView(i:Interface, val order:Order, val prevScreen:View):View(i, "Dowóz") {
    override fun draw() {
        super.draw()

        for ((i,d) in Pizzeria.Districts.all.withIndex()){
            println("${if (i==currentIndex) '>' else ' ' } ${d.asString()}")
        }

    }

    override fun ok() {
        order.delivery=true;
        order.deliveryPlace = Pizzeria.Districts.all[currentIndex]
        i.changeView(prevScreen)


    }
    override fun down() {
        currentIndex++;
        if (currentIndex>=Pizzeria.Ingredients.all.size ) currentIndex=0;
    }

    override fun up() {
        currentIndex--;
        if (currentIndex<0) currentIndex=Pizzeria.Ingredients.all.size-1 ;

    }
}