# Shopping Sidekick
---
###### Design Document

TJ Dailey  
Natalie Forbes  
Benjamin Gomori  
Joshua Gyau  
Mamadou Kone

## Introduction

Shopping sidekick is an app that saves you time and money by helping you figure out the best store/s to buy everything you need. Shopping sidekick allows you to:
-	Add your shopping list
-	Select your favorite stores and branches
-	Select your favorite brands
-	Ignore brands you don’t like
-	Specify a maximum quantity 

Shopping sidekick searches your chosen stores for your items, then checks stock availability and prices to let you know your best options. 
## Storyboard
[Shopping Sidekick](https://projects.invisionapp.com/prototype/ckkhhheid002wmg01p4rl98gv/play)

![ShoppingSidekickPreview](https://user-images.githubusercontent.com/64596547/106222273-2c930080-61ad-11eb-9c5a-90e546288892.png)

## Functional Requirements

### Requirement 1: Search Grocery Items by Price
#### SCENARIO
As a user that buys groceries,
I want to find the cheapest prices for groceries,
So that I can save money.
#### DEPENDENCIES
Store price lists are available and accessible.

#### EXAMPLES
###### 1.1
**Given** a feed of grocery items data  
**When** I search for a list of groceries  
**Then** I should get a list of stores with the cheapest grocery lists

###### 1.2
**Given** a feed of grocery items data  
**When** I search for a list containing “apples”  
**Then** I should get a list of stores and prices for grocery lists containing apples

### Requirement 2: Search Grocery Items by Stock Availability
#### SCENARIO
As a user that buys groceries,
I want to find stores that have what I need in stock,
So that I can save time.
#### DEPENDENCIES
Store stock lists are available and accessible.

#### EXAMPLES
###### 2.1
**Given** a feed of grocery items data for specific stores  
**When** I search for a list of groceries  
**Then** I should get a list of stores with all my grocery items in stock

###### 2.2
**Given** a feed of grocery items data for specific stores  
**When** I search for a list containing “apples”  
**Then** I should get a list of stores where apples are in stock

### Requirement 3: Save Shopping List
#### SCENARIO
As a user that buys groceries,
I want to save my shopping list,
So that I can view it in the store and use it as my starting point for my next shopping list.
#### DEPENDENCIES
access to cloud
#### EXAMPLES
###### 3.1
**Given** a feed of grocery store shopping lists  
**When** I select and save my list  
**Then** when I go to the store I can view my list.

###### 3.2
**Given** a list has been previously saved  
**When** I start a new list  
**Then** I can select items from my previous list

## Class Diagram
![ClassDiagram](https://user-images.githubusercontent.com/54749949/115615604-2e0c2a80-a2bd-11eb-969d-e73d6cf438c7.png)

## Class Diagram Description
**MainActivity:** The program starts running here. Manages the different fragments.<br/>
**MapsActivity:** Helps to incorporate Google's maps.<br/>

**MainFragment:** Landing page. Displays the products retrieved from the market API and enables the user to add products as CartItem to his cart.<br/>
**CartFragment:** Display the user cart items and enables the user can change quantity.<br/>
**MarketFragment:** Displays the list of markets from the one with the cheapest cart total to the most expensive one.<br/>
User can click on the market's name -> Goes to StoreFragment.<br/>
User can click on the market's map icon-> Goes to MapFragment.<br/>
**StoreFragment:** Displays the market details and a list of user cart items with the market's price.<br/>
**MapsFragment:** Displays distance and directions from user location to market address. Incorporates Google's Maps.<br/>


**MarketListAdapter:** Populates the MarketFragment's recycler view with a list of markets.<br/>
**ProductListAdapter:** Populates the MainFragment's recycler view with a list of products.<br/>
**StoreListAdapter:** Populates the StoreFragment's recycler view with a list of markets.<br/>

**MainViewModel:** Responsible for providing data to MainActivity from the market APIs and Firebase.<br/>
**AppViewModel:** Helps to incorporate location data.<br/>

**RetrofitClientInstance:** Used to easily manage HTTP requests and responses.<br/>
**LocationLiveData:** Helps to incorporate location live data.<br/>
**LocationDetails:** Data class that represents the user's location. <br/>
**IMarketApiObjectDAO:** An implementation of this interface will be used by the RetrofitClientInstance to make HTTP requests that retrieve entire data from markets' APIs.<br/>

**FirebaseService:** A class that manages the firebase logic.<br/>
**Cart:** Data class that represents a user cart.<br/>
**CartItem:** Data class that represents a user's cart item in firebase.<br/>

**MarketAPIService:** A class that manages the connection to the market APIs.<br/>
**MarketApiObject:** Data class that represents the entire data retrieved from the market API. This includes market metadata and product list.<br/>
**Market:** Data class that represents a market's metadata retrieved from the market API.<br/>
**Product:** Data class that represents a market's single product data retrieved from a market API.<br/>
**ProductPriceList:** Data class represents a list of MarketProductPrice.<br/>
**MarketProductPrice:** Data class represents a market's single product's price.<br/>

## Scrum Roles

-	DevOps/Product Owner/Scrum Master: Natalie Forbes
-	Frontend Developer: Joshua Gyau, Mamadou Kone
-	Integration Developer: TJ Dailey, Benjamin Gomori
## Weekly Meeting

Microsoft Teams  
Sunday at 7PM
> Extra meeting: Thursday at 5PM


