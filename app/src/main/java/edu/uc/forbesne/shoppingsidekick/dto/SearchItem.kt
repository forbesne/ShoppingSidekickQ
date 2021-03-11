package edu.uc.forbesne.shoppingsidekick.dto

data class SearchItem (var UPC : String, var amount: Int) {

    fun add(amount: Int){
        this.amount += amount
    }

    fun reduce(amount: Int){
        this.amount -= amount
        if(this.amount < 0) this.amount = 0
    }
}