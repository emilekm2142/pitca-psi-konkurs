//szybki kurs kotlina

//object to static class albo namespace. Beind the courtain to jednak singleton xd
//typy są po dwukropku ale w więkoszości przypadków sa infered przez kompilator
//val to const, var to coś jak auto z c++ albo var z c# (czy w javie to już jest xd?)
object Pizzeria {




    object Ciasta {
        val grube = Ciasto("Grube", 12.0)
        val cienkie = Ciasto("Cienkie", 10.0)
    }

    object Districts {
        val grunwald = District("Grunwald", 4.0)
        val stareMiasto = District("Stare miasto", 5.0)
        val wilda = District("Wilda", 5.0)
        val jeżyce = District("Jeżyce", 5.0)
        val noweMiasto = District("Nowe miasto", 6.0)
        //hehe
        val łazarskiRejon = District("Łazarski rejon", 999.0)
        val all = listOf<District>(grunwald, stareMiasto, wilda, jeżyce, noweMiasto, łazarskiRejon)
    }

    object Ingredients {
        operator fun get(currentIndex: Int):Ingredient {
            return all[currentIndex]
        }

        val papryka = Ingredient("papryka", 0.8)
        val czosnek = Ingredient("czosnek", 0.9)
        val pieczarki: Ingredient = Ingredient("pieczarki", 1.10)
        val ananas = Ingredient("ananas", 1.4)
        val mozarella = Ingredient("mozarella", 1.5)
        val salami = Ingredient("salami", 1.6)
        val salamiPikantne = Ingredient("salami pikantne", 1.6)
        val karczochy = Ingredient("karczochy", 1.7)
        val kukurydza = Ingredient("kukurydza", 1.9)
        val sos = Ingredient("sos pomidorowy", 2.0)
        val bekon = Ingredient("bekon", 2.1)
        val szynka = Ingredient("szynka", 2.2)
        val sosCzosnkowy = Ingredient("sos czosnkowy", 2.5)
        val oliwa = Ingredient("oliwa z oliwek", 3.0)
        val czarneOliwki = Ingredient("czarne oliwki", 3.2)
        val all = listOf<Ingredient>(
            papryka, czosnek, pieczarki, ananas, mozarella, salami, karczochy,
            kukurydza, sos, bekon, szynka, sosCzosnkowy, oliwa, czarneOliwki, salamiPikantne
        )

    }

    //factory ale na lambdach
    object PizzasPresets {

        //sygnatura lambdy (void)->Pizza
        val margarita = { Pizza("Margerita", Ciasta.cienkie, mutableListOf(Ingredients.sos, Ingredients.mozarella)) }
        val marinara = {
            Pizza(
                "marinara",
                Ciasta.cienkie,
                mutableListOf(Ingredients.sos, Ingredients.mozarella, Ingredients.czosnek)
            )
        }
        val napoletana = {
            Pizza(
                "napoletana",
                Ciasta.cienkie,
                mutableListOf(Ingredients.sos, Ingredients.mozarella, Ingredients.czarneOliwki)
            )
        }
        val hawajska = {
            Pizza(
                "hawajska",
                Ciasta.cienkie,
                mutableListOf(Ingredients.sos, Ingredients.mozarella, Ingredients.ananas)
            )
        }
        val funghi = {
            Pizza(
                "funghi",
                Ciasta.cienkie,
                mutableListOf(Ingredients.sos, Ingredients.mozarella, Ingredients.pieczarki)
            )
        }
        val capricciosa = {
            Pizza(
                "capricciosa",
                Ciasta.cienkie,
                mutableListOf(Ingredients.sos, Ingredients.mozarella, Ingredients.pieczarki, Ingredients.szynka)
            )
        }
        val dinamite = {
            Pizza(
                "dinamite",
                Ciasta.cienkie,
                mutableListOf(Ingredients.sos, Ingredients.mozarella, Ingredients.salamiPikantne)
            )
        }
        val quatroStagioni = {
            Pizza(
                "Quatro stagioni",
                Ciasta.cienkie,
                mutableListOf(
                    Ingredients.sos,
                    Ingredients.mozarella,
                    Ingredients.szynka,
                    Ingredients.karczochy,
                    Ingredients.papryka
                )
            )
        }
        val all: MutableList<() -> Pizza> = mutableListOf(
            margarita, marinara, napoletana, hawajska, funghi, capricciosa,
            dinamite, quatroStagioni
        )
    }

    object Discounts {
        val students: Discount = Discount { it.price * 0.8 }
        val none: Discount = Discount { it.price  }
    }
}