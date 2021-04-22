package edu.uc.forbesne.shoppingsidekick.dto

/**
 * Data calss that represents Market Object
 *
 * @param market Market Object
 * @param products List of all the products coming in from the api call for that market
 */
class MarketApiObject(var market: Market = Market(), var products: ArrayList<Product> = ArrayList<Product>()) {
}