package edu.uc.forbesne.shoppingsidekick.dto

data class Market (var name : String="", var distance : Float = 0f, var cartPrice : Float = 0f,
                   var id : String="") {

    override fun toString(): String {
        return "Name: $name, Price: $cartPrice, Distance: $distance"
    }
}