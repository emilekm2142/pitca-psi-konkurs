//Discount to pojemnik na lambdę zwracającą nową cenę na podstawie zamówienia. Można dzięki  temu sprawdzić np czy w jakiejś
// pizzy jest sos + pieczarki + ananas i zaliczyć discount tylko do takiego combo
class Discount(val discountFunction:(Order)-> Double) {
}