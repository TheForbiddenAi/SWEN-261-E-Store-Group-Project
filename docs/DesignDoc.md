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

Customers, granted they are logged into an account, can view and 
search through a variety of pre-made ducks from the store catalog.
If a customer does not have an account they can register one to use 
the store if the account name does not already exist. This account 
will also save their previous shopping sessions so they can return
at a later date to resume them. Once they find a duck they would like 
to purchase, they can select it and add it to their cart. They may view 
their shopping cart contents at any time during the shopping process by 
clicking on the shopping cart button. When viewing the contents of the 
shopping cart and the customer decides they no longer want to purchase a 
duck, they may remove it from their shopping cart individually or clear 
the entire cart all at once. The total price is also displayed on the page. 
Once a customer has decided they are done shopping and would like to checkout 
they navigate to the shopping cart page and select the checkout button. Here
they will enter their contact, shipping, and payment information. When the 
checkout is complete they will be given a receipt that shows the contents of 
the order and the total price paid. From an owners perspective, they have access
to the whole store catalog and can add/remove new products or even update existing 
ones at the click of a button. They can also search for specific ducks in the inventory
for easier access. Admin accounts do not have access to a shopping cart and cannot shop 
on the store.

### MVP Features
>  _**[Sprint 4]** Provide a list of top-level Epics and/or Stories of the MVP._

### Enhancements
> _**[Sprint 4]** Describe what enhancements you have implemented for the project._

## Application Domain

This section describes the application domain.

![Domain Model](domain-model.png)

As our goal is to create an e-store, our domain model contains entities, such as 
duck (our product), customer, shopping cart, and more. One of our most essential
entities is Duck. Duck is connected to most of the other entities to facilitate
necessary functionality, such as displaying our items on our catalog page.
Additionally, our profile page allows our customers to view and modify their payment
and shipping information. This information can then be auto-filled into the checkout
form, giving them a more enjoyable checkout experience. Finally, our Custom Duck
entity lets customers create their own ducks if they do not like any of our premade ones.

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

### View Tier
> _**[Sprint 4]** Provide a summary of the View Tier UI of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

The View Tier UI is responsible for presenting the user interface to the end user. It consists 
of various components that handle different aspects of the UI such as the User Interface Framework, 
View Manager, View Controller, and the data binding component

The User Interface Framework, which provides a foundation for building the UI. 
This includes things like layout managers, event handlers, and other UI controls.

The View Manager, which is responsible for managing the views that are displayed to the user. 
This includes things like creating new views, updating existing views, and managing the overall 
layout of the UI.

The third component is the view controller, which is responsible for managing the interactions between 
the user and the UI. This includes things like handling user input, responding to user actions, and 
managing the state of the UI.

Finally, there is the data binding component, which is responsible for binding data from the backend 
to the UI. This includes things like retrieving data from a database, formatting the data for display, 
and updating the UI as the data changes.

Overall, the View Tier UI is responsible for providing a responsive and intuitive user interface for 
the application. By using a combination of these components, we created a UI that is both 
easy to use and easy to maintain.


> _**[Sprint 4]** You must  provide at least **2 sequence diagrams** as is relevant to a particular aspects
> of the design that you are describing.  For example, in e-store you might create a
> sequence diagram of a customer searching for an item and adding to their cart.
> As these can span multiple tiers, be sure to include an relevant HTTP requests from the client-side to the server-side
> to help illustrate the end-to-end flow._

> _**[Sprint 4]** To adequately show your system, you will need to present the **class diagrams** where relevant in your design. Some additional tips:_
>* _Class diagrams only apply to the **ViewModel** and **Model** Tier_
>* _A single class diagram of the entire system will not be effective. You may start with one, but will be need to break it down into smaller sections to account for requirements of each of the Tier static models below._
>* _Correct labeling of relationships with proper notation for the relationship type, multiplicities, and navigation information will be important._
>* _Include other details such as attributes and method signatures that you think are needed to support the level of detail in your discussion._

### ViewModel Tier
> _**[Sprint 4]** Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

> _At appropriate places as part of this narrative provide **one** or more updated and **properly labeled**
> static models (UML class diagrams) with some details such as critical attributes and methods._
>
![Replace with your ViewModel Tier class diagram 1, etc.](model-placeholder.png)

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

![Model Tier UML Diagram](UML%20Diagrams/model-uml.png)

## OO Design Principles

### Open/Closed Principle

The Open/Closed Principle is a design principle stating that software entities 
should be open for extension but closed for modification, in order to allow for 
flexibility and maintainability in the software design. 

Applications:<br>
The Open/Closed Principle is applied in our design by creating multiple states/
entities that are open for extension but closed for modification by other 
entities. To further adhere to this principle, we broke down our design into more
specific components and creating additional entities that share functionality, 
allowing for easier extension without the need for modification.
An example of this principle in action is our Account class, which is an abstract
class that provides essential properties and basic functionality to classes like 
UserAccount and OwnerAccount, which implement it. UserAccount and OwnerAccount 
classes meet the Open/Closed Principle by extending the abstract Account class, 
which is closed for modification. Both UserAccount and OwnerAccount classes 
inherit the properties and basic functionality provided by the Account class 
without modifying it. This allows for easy extension of the Account class by 
adding new classes that also inherit from it, without changing the existing 
implementation.

![user-owner-account UML Diagram](UML%20Diagrams/user-owner-account.png)


### Pure Fabrication

Pure Fabrication is a design principle stating that a class or module should be
created solely for the purpose of fulfilling a certain functionality or 
responsibility, without being tied to a specific entity or behavior in the system.

Applications:<br>
This principle is applied in our design with our user account, as seen above in 
the Model Tier UML diagram. This is merely a way to store a username, password, 
and payment information in an easy manner. Our authentication system is going to
be handling all the creation, establishing, and verifying of the credentials 
within it. If the user account is being created, the authentication system will 
store it in its records. If an account already exists and somebody is trying to 
log in, then our authentication system will take the data from the user account 
in its records and parse it respectively with its own methods. If a user wants 
to delete their account, the authentication system removes it from its records. 
A user account has no functionality other than storing the data for an account.

![UserAccount UML Diagram](UML%20Diagrams/UserAccount.png)

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

Single Responsibility is a design principle stating that a class or module 
should have only one reason to change.

Applications:<br>
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

![Inventory Controller UML Diagram](UML%20Diagrams/inventory-controller-uml.png)

### Information Expert

Information Expert is a design principle where responsibility is assigned 
to an object that has the information needed to complete a task.

Applications:<br>
The Model Tier UML diagram above adheres to the information expert principle. For
example, in the shopping cart class, the shopping cart is given the 
responsibility of checking out and removing items from the cart. 
Because the shopping cart object holds the item array, we can assign it the
responsibility of adding items, removing items, and editing the quantity of
each item in the cart. This keeps the class UML diagram simple and easy to 
understand without creating complications. For example, if a user 
wants to remove an item from their cart, the Shopping cart class can check whether
the item exists in the cart by searching through its list of items. If the
item is found, the Shopping cart class can remove it from the cart, as it holds 
the necessary information about the item.

![ShoppingCart UML Diagram](UML%20Diagrams/ShoppingCart.png)

Another class that supports the information expert principle is the Accounts class.
The Accounts class is responsible for updating the profile information and this is 
important as the Accounts class holds a profile object. Since the account class 
holds a profile object, it is appropriate to give the Accounts class the 
responsibility of updating the profile information.

![Account UML Diagram](UML%20Diagrams/Account.png)

## Static Code Analysis/Future Design Improvements
> _**[Sprint 4]** With the results from the Static Code Analysis exercise,
> **Identify 3-4** areas within your code that have been flagged by the Static Code
> Analysis Tool (SonarQube) and provide your analysis and recommendations.  
> Include any relevant screenshot(s) with each area._

> _**[Sprint 4]** Discuss **future** refactoring and other design improvements your team would explore if the team had additional time._

## Testing

### Acceptance Testing

Out of our 64 acceptance criteria tests, all passed except for 7 of them. However, we
expected these 7 to fail because the story card was for a feature that was removed due to
it being no longer necessary. Furthermore, some of our acceptance criteria needed slight 
adjustments to account for pages being turned into angular modals, meaning they no longer 
had the routes the criteria referred to. Other than the tests mentioned above and these slight
changes, everything went well. In this sprint, we also tried to make our acceptance criteria
tests more detailed to cover more paths.

### Unit Testing and Code Coverage

When creating unit tests, we focused on making sure that the most critical features had
nearly all, if not all, branches covered. We deemed all MVP features critical and
aimed for nearly 100% code coverage for them. When it came to non MVP features, such as
our enhancements, we aimed for 90%+ code coverage. We chose these values because we felt
they would nearly guarantee our backend to be bug-free. Additionally, we felt that
because of how important the MVP was, we needed to ensure that there would be as few
bugs as possible, so we aimed for nearly 100% code coverage for those features.

We reached our goals with an overall 96% code coverage. All of our MVP features have
code coverages of upwards of 95%. Our Account class had the lowest coverage of 87%.
This is because we did not unit-test the hashCode and confirmPassword functions.
In fact, a lot of our missing coverage comes from not testing our hashCode functions.

![Overall Code Coverage for Sprint 2](Code%20Coverage/Overall.png)
![Persistence Code Coverage for Sprint 2](Code%20Coverage/Persistence.png)
![Model Code Coverage for Sprint 2](Code%20Coverage/Model.png)
![Controller Code Coverage for Sprint 2](Code%20Coverage/Controller.png)
