package edu.uc.forbesne.shoppingsidekick.dto


// Used to store data fetched from Market API
class MarketApiObject(var market: Market = Market(), var products: ArrayList<Product> = ArrayList<Product>()) {
}