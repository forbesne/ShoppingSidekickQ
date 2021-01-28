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

## Class Diagram Description

## Scrum Roles

-	DevOps/Product Owner/Scrum Master: Natalie Forbes
-	Frontend Developer: Joshua Gyau, Mamadou Kone
-	Integration Developer: TJ Dailey, Benjamin Gomori
## Weekly Meeting

Microsoft Teams  
Sunday at 7PM
> Extra meeting: Thursday at 5PM


