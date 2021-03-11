package edu.uc.forbesne.shoppingsidekick.dto

data class Cart (var itemList : ArrayList<CartItem>) {

    fun addItem(item:CartItem){
        itemList.add(item)
    }

    fun removeItem(cartItem:CartItem){
        if(cartItem != null) itemList.remove(cartItem)
    }

    fun deleteCart(amount: Int){
        this.itemList = ArrayList<CartItem>()
    }
}