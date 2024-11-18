# Inventory Management App

- **Mobile Apps II Assignment 5**

## Project Description

This project is an Android application designed to manage inventory items. 
The application allows users to view, add, and delete items from their inventory, as well as check the total monetary worth of the inventory. 
Built using **Jetpack Compose**, **Room Database**, and **StateFlow**, the app provides a modern and efficient way to manage inventory data.

### Key Features:
- **View Inventory**: Users can see a list of all inventory items with details such as item name, quantity, supplier, and cost per unit.
- **Add New Item**: A button allows users to add new items to the inventory by providing details like name, quantity, supplier, and cost.
- **Delete Items**: Users can remove items from the inventory with a delete option next to each item.
- **Calculate Total Worth**: The app dynamically calculates the total worth of the inventory based on the item quantities and costs.
- **State Management**: Uses **StateFlow** and **LiveData** to handle state changes and synchronize the UI with the database.

### Screens:
- **Inventory Screen**: Displays a list of all inventory items with options to delete items and view the total worth.
- **Add Item Dialog**: Provides a form for adding new items to the inventory, including fields for name, quantity, supplier, and cost.
- **Total Worth Dialog**: Displays the total worth of the inventory when triggered.

### Technical Details:
- **Jetpack Compose** is used for building the UI, providing a declarative and responsive design.
- **Room Database** is used for data persistence, storing inventory items and their details.
- **StateFlow** is used for managing state in the ViewModel and updating the UI reactively.
- **LiveData** is used for observing the total worth of the inventory and updating the UI accordingly.

**Lecturer**: Eugene O'Regan  
**Module**: Mobile App Development 2  

## Disclaimer

This project is for educational purposes only. 
The information and code presented in this report are intended to demonstrate the application of Jetpack Compose in Android development and are not intended for commercial use. 
While efforts have been made to ensure the accuracy and reliability of the content, there may be errors or omissions. 
I am not responsible for any consequences arising from the use of this project or its implementation in real-world applications. 
Users are encouraged to verify and adapt the code as necessary for their specific use cases.

***Filipe Lutz***
