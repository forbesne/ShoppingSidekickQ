package edu.uc.forbesne.shoppingsidekick.dto

//itemList is the initial cart data coming in when created
data class Cart (var itemList : ArrayList<CartItem>) {

    //map of all items currently in cart - more efficient than using an array list, as searching for upc is needed
    //key = upc, value = cartItem
    var itemQuantityMap: HashMap<String,CartItem> = HashMap<String, CartItem>()

    //makes sure that map includes all initial items when created
    init {
        itemList.forEach {
            itemQuantityMap.put(it.UPC, it)
        }
    }

    fun addItem(item:CartItem){
        var exsitingItemInCart = itemQuantityMap.get(item.UPC)
        if(exsitingItemInCart ==null){
            itemQuantityMap.put(item.UPC, item)
        }else{
            exsitingItemInCart.add(item.quantity)
            itemQuantityMap.put(item.UPC, exsitingItemInCart)
        }
    }

    fun removeItemFromCart(item:CartItem){
        var exsitingItemInCart = itemQuantityMap.get(item.UPC)
        if(exsitingItemInCart != null){
            exsitingItemInCart.remove()
            itemQuantityMap.put(item.UPC, exsitingItemInCart)
        }
    }

    fun deleteCart(amount: Int){
        itemQuantityMap.clear()
    }
}