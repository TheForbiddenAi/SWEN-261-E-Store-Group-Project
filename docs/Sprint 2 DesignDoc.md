---
geometry: margin=1in
---

# PROJECT Design Documentation

## Team Information

* Team name: Quackers
* Team members
    * Mason Bausenwein
    * Travis Hill
    * Beining Zhou
    * Eric Choi
    * Andrew Le

## Executive Summary

This is a summary of the project.

### Purpose

Our goal is to create an onlne e-store that sells highly customizable
ducks. We aim to be able to fulfill both pre-made ducks and user customized
ducks.

### Glossary and Acronyms

| Term | Definition                |
|------|---------------------------|
| SPA  | Single Page               |
| MVP  | Minimum Viable Product    |
| UML  | Unified Modeling Language |
| OCP  | Open/Closed Principle     |

## Requirements

This section describes the features of the application.

### Definition of MVP

As of right now, our customers can select and search through a variety of
pre-made ducks from our store catalog and add or remove them from their
shopping cartsas they please. Users can register accounts to save their 
previous shopping sessions and login at a later date to resume them. 
From an owners perspective, they have access to the whole store catalog 
and can add/remove new products or even update existing ones at the click 
of a button.


## Application Domain

This section describes the application domain.

![Domain Model](domain-model.png)

As our goal is to make an e-store, our domain contains entities, such as products, 
customer, shopping cart, and more. Products are connected to most things are our
domain model, which exemplifies how important they are as an entity. For instance,
products have to be added and removed from the shopping cart, and customers need to
be able to view the products. Customers are also vital to our domain model, as we
can't make money if customers cannot use our website. As seen in the model 
above, customers can browse products, add items to shopping carts, complete 
orders, and more.

## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's
architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

The e-store web application, is built using the Model–View–ViewModel (MVVM)
architecture pattern.

The Model stores the application data objects including any functionality to
provide persistance.

The View is the client-side SPA built with Angular utilizing HTML, CSS and
TypeScript. The ViewModel provides RESTful APIs to the client (View) as well as
any logic required to manipulate the data objects from the Model.

Both the ViewModel and Model are built using Java and Spring Framework. Details
of the components within these tiers are supplied below.

### Overview of User Interface

This section describes the web interface flow; this is how the user views and
interacts
with the e-store application.

Upon opening our website, the user will be greeted with a login page where they 
can either log in or register. Users who log in with an admin account will
be directed to the inventory management page. From this page, they will be able to
create, modify, and delete products. If a non-admin attempts to access the inventory
management page, they will be redirected to the login page. If the user logs in as a
buyer, they will be redirected to a catalog page where they can view all of the
available items. Furthermore, they can use a search box to filter through
the available items. They are also able to add these items to their cart. Once they have
added the items they want, they can proceed to the shopping cart to modify
the quantity of each item and checkout. After checking out, they are directed to a page
where they are given a receipt.


### Model Tier

We have three main classes in our Model Tier and various smaller classes that
are utilized in these main classes. These classes are Duck, ShoppingCart, and
Account. Our entire website will revolve around these base classes. A user will
create an Account to add a Duck to their Shopping Cart
to be eventually purchased. The Duck class utilizes three smaller classes
defined in the model tier: Colors, Size, and DuckOutfit. Both Colors and Size
are enums meant to describe the constant color and size of the duck. DuckOutfit
will describe the accessories a duck can have at the time of purchase. All
these classes will affect the total price of the duck for the shopper. Account
is an abstract class that has two subclasses, UserAccount and OwnerAccount
which both inherits Account's base properties. A UserAccount is what a regular
shopper will have when they register to the site. A OwnerAccount is created on
startup and can not be regsitered or created directly. The UserAccount's ID is
linked to their own Shopping Cart ID, an OwnerAccount's ID is not and they do
not have access to a shopping cart.

![Model Tier UML Diagram](model-uml.png)


## OO Design Principles

### Open/Closed Principle

The Open/Closed Principle is that software entities should be open for 
extension but closed for modification. Our Model Tier UML diagram illustrates 
this by creating multiple states/entities that can be used in other entities 
through extension but cannot be changed/modified by them. 

An example of this is our Account class. Our Account class on its own is an
abstract class that supplies basic functionality and necessary properties
to classes that implement it, such as our UserAccount and OwnerAccount classes.

We could follow this principle better by either breaking down our design 
into more specific components or by creating more entities that share
functionality.

### Pure Fabrication

This principle is applied in our design with our user account, as seen above in
the Model Tier UML diagram. This is merely a way to store a username, password, 
and payment information in an easy manner. Our authentication system is going 
to be handling all the creation, establishing, and verifying of the credentials 
within it. If the user account is being created, the authentication system will 
store it in its records. If an account already exists and somebody is trying to 
log in, then our authentication system will take the data from the user account 
in its records and parse it respectively with its own methods. If a user wants 
to delete their account, the authentication system removes it from its records. 
A user account has no functionality other than storing the data for an account.

Our shopping cart would most likely serve to benefit from pure fabrication. We
need something to handle the product methods. Right now, we would have to add
multiple methods to our shopping cart class to gather all the information
needed to properly calculate the total of all the items in our cart and display
them. These methods have no logic related to a shopping cart so including them
in this class would prove to be troublesome. So instead, we should
create a checkout class that handles all these calculations making the shopping
cart class more cohesive in the process. Not to mention, this code can also be
applied in other situations than the checkout, such as showing the total value
of all the items in the shopping cart when a user is not on the checkout
screen.

### Single Responsibility

As of now, our design makes use of the single responsibility object-oriented
design principle by separating our entity objects from our data accessor
objects. We use the entities to allow for runtime data persistence, and we use
our data accessors to read data from a file, so it can be serialized to an
entity. Additionally, our controller classes will only serve one group of
endpoints. For example, our inventory controller will only serve
endpoints relating to inventory management. Furthermore, if a controller class
started to become very lengthy, we could divide the controller class into
multiple classes that would encompass all the original endpoints. Finally, our
inventory class will serve as an information expert on products, allowing
products to be added, removed, edited, and searched based on specific
parameters. Separating our responsibilities like this makes our code more
readable and easier to work on.

To incorporate the single responsibility object-oriented design principle even
more into our design, I suggest that for our duck class that the property
attributes of the duck, such as color, size, etc., are stored in a separate
DuckProperties class. Then, the duck entity class could serve as an information
expert for the duck’s properties. Additionally, a customer’s profile data could
be saved in a profile entity object that is linked to the customer entity
object. However, certain information, such as the customer’s name, would remain
stored in the customer entity object.

![Inventory Controller UML Diagram](inventory-controller-uml.png)

### Information Expert

The responsibility should be assigned to the object that has the information
that allows the task to be completed.

Applications:<br>
The Model Tier UML diagram above adhere to the information expert principle.
For example, in the shopping cart class, the shopping cart is given the
responsibility of checking out and removing the item. This adheres to the
principle because since the object holds the item, we can assign the
responsibilities of adding the item, removing the item, and editing the number
of the item to the Shopping cart class. This keeps the class UML diagram simple
and easy to understand without creating complications. Another class that
supports the information expert principle is the Accounts class. The Accounts
class is responsible for updating the profile information and this is important
as the Accounts class holds a profile object. Since the account class holds a
profile object, It is appropriate to give the Accounts class the responsibility
of updating the profile information. Thus, making the UML diagram adhere to
the information expert principle.


## Testing

### Acceptance Testing

All 37 of our acceptance criteria tests for sprint 2 have passed. We have a total
of 47 acceptance criteria tests, 10 of which have not been tested as they are sprint
3 stories. For the sake of the acceptance criteria test plan, they have been marked
as failing for sprint 2 but are noted as not being sprint 2 stories. We do not have
any major concerns about our acceptance test plan. However, we have realized that 
some of our acceptance criteria are a little vague. We hope to fix that going forward.
Other than that, we believe everything is ready to be demoed.

### Unit Testing and Code Coverage

![Overall Code Coverage for Sprint 2](Sprint%202%20Code%20Coverage/Overall.png)
![Persistence Code Coverage for Sprint 2](Sprint%202%20Code%20Coverage/Persistence.png)
![Model Code Coverage for Sprint 2](Sprint%202%20Code%20Coverage/Model.png)
![Controller Code Coverage for Sprint 2](Sprint%202%20Code%20Coverage/Controller.png)