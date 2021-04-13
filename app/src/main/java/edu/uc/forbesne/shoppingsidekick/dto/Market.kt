package edu.uc.forbesne.shoppingsidekick.dto

/**
 * Data class that represents a market and market info
 *
 * @param name Market name
 * @param distance How far is the market located
 * @param cartPrice Total price of the cart items in that market
 * @param id Market ID
 */
data class Market (var name : String="", var distance : Float = 0f, var cartPrice : Float = 0f,
                   var latitude :String = "", var longitude :String = "",
                   val cartItems: ArrayList<CartItem> = ArrayList<CartItem>(),
                   var id : String="") {

    override fun toString(): String {
        return "Name: $name, Price: $cartPrice, Distance: $distance"
    }
}