# *Restaurant Inventory and Recipe Manager*

## A program for keeping track of edible and non-edible inventory, and recipes
## This program is meant for logistical use in a restaurant, however, it can also be utilised for personal uses. 


This program will be capable of:
- Storing ingredients 
- Storing ingredient amounts
- Storing recipes with items, descriptions and tools

### User Stories:
- As a user, I want to be able to add items to an inventory
- As a user, I want to be able to consume items in an inventory
- As a user, I want to be able to create a recipe
- As a user, I want to be able to add ingredients to recipes while they are being made
- As a user, I want to be able to add tools to recipes while they are being made
- As a user, I want to be able to keep track of expiry dates of ingredients
- As a user, I want to be able to save the state of the application
- As a user, I want to be able to load a state of the application


### Phase 4, Task 2:
Here is an example of how the log should look. It logs the imtes that have been added to recipes, and the recipes that
have been added to recipe section, and it also logs the items that have been added to the inventory.

Fri Apr 01 00:19:25 PDT 2022
500 G Sabzi   Stocked at: 2022-04-01 Expiring at: 2023-08-14 has been added to Recipe: Ghorme Sabzi
Fri Apr 01 00:19:25 PDT 2022
500 G Alaf   Stocked at: 2022-04-01 Expiring at: 2022-04-13 has been added to Recipe: Ghorme Sabzi
Fri Apr 01 00:19:25 PDT 2022
1000 G Yonje   Stocked at: 2022-04-01 Expiring at: 2022-04-16 has been added to Recipe: Ghorme Sabzi
Fri Apr 01 00:19:25 PDT 2022
1  Kaftar Plastici   Stocked at: 2022-04-01 has been added to Recipe: Ghorme Sabzi
Fri Apr 01 00:19:25 PDT 2022
Recipe: Ghorme Sabzi has been added to Section: Persion Food
Fri Apr 01 00:20:18 PDT 2022
12  Kaftar Plastici   Stocked at: 2022-04-01 Expiring at: 2022-05-06 has been added to Inventory


### Phase 4, Task 3:
- reduce the number of classes
- making all the methods robust
- factoring out common functionality between Inventory and RecipeBook
- ensure that the methods in the abstract Item and Amount classes are substitutable with their subclasses
- change implementation of toString methods in order to reduce coupling