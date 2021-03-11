package edu.uc.forbesne.shoppingsidekick.dto

// code is based on professor's github - https://github.com/discospiff/MyPlantDiaryQ

data class CartItem (var description : String, var UPC : String, var amount: Int = 0, var imageURL : String) {

    fun add(amount: Int){
        this.amount += amount
    }

    fun reduce(amount: Int){
        this.amount -= amount
        if(this.amount < 0) this.amount = 0
    }

    fun remove(amount: Int){
        this.amount = 0
    }
}