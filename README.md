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

###### 1.3
**Given** a feed of grocery items data  
**When** I search for “aldskfjiafmnlfjv;aigfj;fj”  
**Then** I should get a message saying “no results found”

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

###### 2.3
**Given** a feed of grocery items data for specific stores  
**When** I search for “aldskfjiafmnlfjv;aigfj;fj”  
**Then** I should get a message saying “no results found”

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
![ClassDiagram](https://user-images.githubusercontent.com/64596547/106208442-6229f080-6191-11eb-8ae0-6560272eb416.png)
## Class Diagram Description
**MainActivity:** Landing page where the user adds products to their cart. 

**MarketsActivity:** Displays a list of all markets sorted in descending order based on the total cart price. 

**CartsActivity:** Displays a list of all previous carts that the user has created or stored. 

**MainViewModel, MarketsViewModel, CartsViewModel:** Responsible for the logic for, and provide data to their corresponding activity. 

**RetrofitClientInstance:** Used to easily manage HTTP requests and responses. 

**ContextAwareViewModel:** Will be set to provide the app with the user's location information. 

**User:** An instance of this class will represent a user. 

**Product:** An instance of this class will represent a product. 

**Cart:** An instance of this class will represent a cart. 

**Market:** An instance of this class will represent a market. 

**IProductDAO:** An implementation of this interface will be used to get a connection to, query, and managed the data stored in the Product table in the database. 

**IUserDAO:** An implementation of this interface will be used to get a connection to, query, and managed the data stored in the User table in the database. 

**ICartDAO:** An implementation of this interface will be used to get a connection to, query, and managed the data stored in the Cart table in the database. 

**IMarketDAO:** An implementation of this interface will be used to manage the connection to and the state of the Market table in the database. 

**RoomDatabase:** A provided class representing the local Room database. 

**AppDatabase:** A subclass of RoomDatabase used to connect to, query, and manage the data stored in the local Room database. 
## Scrum Roles

-	DevOps/Product Owner/Scrum Master: Natalie Forbes
-	Frontend Developer: Joshua Gyau, Mamadou Kone
-	Integration Developer: TJ Dailey, Benjamin Gomori
## Weekly Meeting

Microsoft Teams  
Sunday at 7PM
> Extra meeting: Thursday at 5PM


