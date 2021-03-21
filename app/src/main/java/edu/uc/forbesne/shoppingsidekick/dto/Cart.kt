package edu.uc.forbesne.shoppingsidekick.dto

//itemList is the initial cart data coming in when created
data class Cart (var size :Int =0) {

    //map of all items currently in cart - more efficient than using an array list, as searching for upc is needed
    //key = upc, value = cartItem
    var itemQuantityMap: HashMap<String,CartItem> = HashMap<String, CartItem>()

    //Currently not being used
    fun addItems(itemList : ArrayList<CartItem>) {
        itemList.forEach {
            addItem(it)
       }
    }

    // gets called from MianViewModel from the getCartFromFirebase()
    // and is updated automatically using the addSnapshotListener
    fun addItem(item:CartItem){
        val existingItemInCart = itemQuantityMap.get(item.UPC)

        if(existingItemInCart == null){
            //itemQuantityMap.put(item.UPC, item)
            size++
        }

        itemQuantityMap.put(item.UPC, item)
    }

    // This is still not implemented in the application
    // once it will we need to change this to use firebase
    fun removeItemFromCart(item:CartItem){
        var existingItemInCart = itemQuantityMap.get(item.UPC)
        if(existingItemInCart != null){
            existingItemInCart.remove()
            itemQuantityMap.put(item.UPC, existingItemInCart)
        }
    }

    // This is still not implemented in the application
    // once it will we need to change this to use firebase
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

    // This is still not implemented in the application
    // once it will we need to change this to use firebase
    fun emptyCart(){
        itemQuantityMap.clear()
    }
}