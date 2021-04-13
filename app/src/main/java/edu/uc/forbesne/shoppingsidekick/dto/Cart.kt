package edu.uc.forbesne.shoppingsidekick.dto

/**
 * A data class that stores Cart data in a HashMap
 *
 * @param size The cart size
 */
data class Cart (var size :Int =0) {

    // HashMap of all items in cart - more efficient than using an array list, as searching by UPC is needed
    // key = upc, value = cartItem
    var itemQuantityMap: HashMap<String,CartItem> = HashMap<String, CartItem>()

    // gets called from MainViewModel from the getCartFromFirebase()
    // and is called automatically using the addSnapshotListener
    fun addItem(item:CartItem){
        val existingItemInCart = itemQuantityMap.get(item.UPC)

        if(existingItemInCart == null){
            size++
        }

        // Used to adjust the cart item's quantity
        itemQuantityMap.put(item.UPC, item)
    }

    // Currently not being used
    fun addItems(itemList : ArrayList<CartItem>) {
        itemList.forEach {
            addItem(it)
        }
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


    fun emptyCart(){
        itemQuantityMap.clear()
    }
}