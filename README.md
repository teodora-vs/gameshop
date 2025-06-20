
Welcome to Gameshop, a Spring web application for managing a game catalog, shopping cart, and orders.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

A web application built with Spring Boot, MySQL, Thymeleaf and Bootstrap 5. It includes features for managing user roles with Spring Security, a game catalog, a shopping cart, and simple order functionality.

## Features

- User authentication and authorization with Spring Security.
- User roles: User and Admin.
- Game catalog with details, prices, and quantity.
- Shopping cart functionality.
- Place orders and view order history.
- Admin access to all user orders.
- Admin ability to manage user roles.
- Admin ability to view, edit, and delete games.
- **User Reviews:**
    - Users can write reviews for games in the catalog.
    - Ratings and detailed feedback help the community make informed decisions.
    - Access reviews on the game details page.

## Technologies Used

- Java
- Spring Boot (Including Spring MVC, Spring Data, Spring Security)
- Thymeleaf
- Bootstrap 5
- MySQL Database

## Getting Started

1. **Clone the repository:**
    ```bash
    git clone https://github.com/TeodoraV1415/gameshop.git
    cd gameshop
    ```

2. **Configure the Database (Optional):**
    - The application is configured to use a MySQL database with default settings.
    - If you want to use a different database or have specific configurations, update the `application.yaml` file in the `src/main/resources` directory.
    - Ensure you have MySQL installed on your system.
    - Create a new MySQL database if needed.
    - To ensure the database is properly populated, set spring.sql.init.mode to always in application.yaml. This allows the application to run initialization scripts on startup.
    - Update the application.yaml file in src/main/resources with your MySQL username and password by replacing ${db_username} and ${db_password}.

3. **Run the Application:**
    ```bash
    ./mvnw spring-boot:run
    ```

4. **Access the Application:**
    Open your browser and go to [http://localhost:8080](http://localhost:8080)

## Usage

- **User Roles:**
    - Users can browse the game catalog, add items to their cart, and place orders.
    - Admins have additional privileges to view all user orders, manage user roles, and manage games.

- **Game Catalog:**
    - Browse available games with details, prices, and quantity.
    - Add games to the shopping cart.
    - Review game or read user reviews.

- **Shopping Cart:**
    - Add or remove items.
    - Adjust quantities and view the total price.

- **Orders:**
    - Place orders and view order history.

- **Admin Actions:**
    - Admins can view all user orders.
    - Admins can manage user roles, including promoting other users to admins.
    - Admins can view, edit, and delete games, including updating quantities.

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

## License

This project is licensed under the [MIT License](LICENSE).
