# Hotel-Reservation-Collections
Designing and implementing a simple hotel reservation program using CLI and Collections in java.
The hotel reservation application will allow customers to find and book a hotel room based on room availability. 
In this project I demonstrated the ability to design classes using OOP, organize and process data with collections, and use common Java types.

### The app will be separated into the following layers:
  -User interface (UI), including a main menu for the users who want to book a room, and an admin menu for administrative functions.
 
  -Resources will act as our Application Programming Interface (API) to UI.
  
  -Services will communicate with our resources, and each other, to build the business logic necessary to provide feedback to UI.
  
  -Data models will be used to represent the domain that we're using within the system.
  
### User Scenarios
``The application provides four user scenarios:``

  -Creating a customer account. The user needs to first create a customer account before they can create a reservation.
  
  -Searching for rooms. The app should allow the user to search for available rooms based on provided checkin and checkout dates. If the application has available rooms for the      specified date range, a list of the corresponding rooms will be displayed to the user for choosing.
  
  -Booking a room. Once the user has chosen a room, the app will allow them to book the room and create a reservation.
  
  -Viewing reservations. After booking a room, the app allows customers to view a list of all their reservations.

``The application allows customers to reserve a room. Here are the specifics:``

  -Avoid conflicting reservations. A single room may only be reserved by a single customer per a checkin and checkout date range.
  
  -Search for recommended rooms. If there are no available rooms for the customer's date range, a search will be performed that displays recommended rooms on alternative dates.
  The   recommended room search will add seven days to the original checkin and checkout dates to see if the hotel has any availabilities, and then display the recommended  
  rooms/dates     to the customer.
