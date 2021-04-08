package edu.uc.forbesne.shoppingsidekick.dto

/**
 * Data calss that represents Market Object
 *
 * @param market Market Object
 * @param products List of all the products
 */
class MarketApiObject(var market: Market = Market(), var products: ArrayList<Product> = ArrayList<Product>()) {
}