# HomeFood Capstone Project

# Database Connect Information (MySQL)
- database name: capstonedb
- username: root
- password: password

# Table(8)
1. roles (user role)
2. user  (user)
3. user_roles
4. items (item table)
5. carts (selected item is in a cart before order)
6. order_items (about online order items)
7. orders   (about online order)
8. reserve (about reservation)

# View(8) - Html, CSS, JavaScript, BootStrap, Thymeleaf
1. index.html
  - Landing page. You can see the information about the store.

2. menu.html
  - You can see the menu and you can select the item you want to order, but using this function you have to login first. 
    If you select the item, then you can check out them at the cart page.

3. Contact.html
 - You can contact the store with a message.
   If you send a message, it will be saved at google sheets below.
   https://docs.google.com/spreadsheets/d/1509dmBsg6MNITFycWZRwlALpkldl8-T9ePLfdaqCf-M/edit?usp=sharing
   Also, an email will be sending to the store email address ( home.7.food@gmail.com )
        
4. Book.html
 - You can make a reservation about this page.
   Only admin user can see the reservations at the bottom page of book.html page.
   There are two types reservation view.
   (1) Valid Reservations : View current and future reservations.
   (2) All Reservations : View all reservations including past reservations.

5. Cart.html
 - You can delete the selected item.
   If you want to order all the items in the cart, you can go to order page by clicking the 'go to checkout' button.
   Also you can see the past order history.

6. Order.html
 - You can order, seleted item. (there is some error after submit, so you move to cart page manually)

7. Login.html
 - User login
   
8. Register.html
 - Register a new user.
   All user will be registred as a user role, so if you want to register a admin user, then you can do it at the database directly.   

# Tech Stack
- Spring Boot, Spring Security, Rombock, Logback, Junit 
- HTML, CSS, JavsScript, Thymeleaf, BootStrap
- MySQL, JDBC, Hibernate
