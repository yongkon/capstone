# Yongkon Hahn : HomeFood Capstone Project

# Database Connect information
database name: capstonedb
username: root
password: password

# Table(8)
1. roles (user role)
2. user  (user)
3. user_roles
4. items (item table)
5. carts (selected item is in a cart before order)
6. order_items (about online order items)
7. orders   (about online order)
8. reserve (about reservation)

# View(7)
1. index.html
    Landing page. You can see the information about the store.

2. menu.html
    You can see the menu and you can select the item you want to order, but using this function you have to login first. 
    If you select the item, then you can check out them at the cart page.

3. Contact.html
   You can contact the store with a message.
   If you send a message, it will be saved at google sheets below.
   https://docs.google.com/spreadsheets/d/1509dmBsg6MNITFycWZRwlALpkldl8-T9ePLfdaqCf-M/edit?usp=sharing
   Also, an email will be sending to the store email address ( home.7.food@gmail.com )
        
4. Book.html

   

**Reservation
Only Admin user can see the reservations bottom of the booking page
1. Valid reservations
2. All reservations 

**Menu
1. Lunch menu 
2. Dinner menu
You can select one item at a time, the quantity function is not applied yet, so you can choose one item per menu.
If you select an item, then you can check out that at the cart page

**Cart
1. Current cart
2. Order History

**Order page

**Login


**Register
All user will be registred as a user role, so if you want to register a admin user, then you can do it at the database directly.
