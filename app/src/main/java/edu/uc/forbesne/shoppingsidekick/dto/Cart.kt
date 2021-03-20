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
        val existingItemInCart = itemQuantityMap.get(item.UPC)

        if(existingItemInCart == null){
            itemQuantityMap.put(item.UPC, item)
        }else{

            var adjustedCartItem = CartItem(existingItemInCart!!.UPC, existingItemInCart!!.quantity)
            adjustedCartItem.id = existingItemInCart.id //this will change one line up

            adjustedCartItem.add(item.quantity)
            itemQuantityMap.put(item.UPC, adjustedCartItem)
        }
    }

    fun removeItemFromCart(item:CartItem){
        var existingItemInCart = itemQuantityMap.get(item.UPC)
        if(existingItemInCart != null){
            existingItemInCart.remove()
            itemQuantityMap.put(item.UPC, existingItemInCart)
        }
    }

    fun deleteCart(amount: Int){
        itemQuantityMap.clear()
    }

    fun doesHaveItem(upc:String):Boolean{
        var existingItemInCart  = itemQuantityMap.get(upc)
        if(existingItemInCart == null) return false
        return true
    }

    fun getCartItem(upc:String):CartItem {
        var existingItemInCart = itemQuantityMap.get(upc)
        if (existingItemInCart == null) return CartItem()
        return existingItemInCart
    }

    fun getCartItemQuantity(upc:String):Int {
        var existingItemInCart = itemQuantityMap.get(upc)
        if (existingItemInCart == null) return 0
        return existingItemInCart.quantity
    }
}