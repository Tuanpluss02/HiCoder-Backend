# HiCoder-Backend

Welcome to HiCoder-Backend, the backend component of the HiCoder project. HiCoder is a web application built with Spring
Boot and PostgreSQL, designed to provide functionalities related to user management, messaging, and more.

## Installation

```
1. Clone the repository:
 > git clone https://github.com/Tuanpluss02/HiCoder-Backend.git


2. Navigate to the project directory:
> cd HiCoder-Backend


3. Install the required dependencies:
> mvn install


4. Configure the application properties by editing `src/main/resources/application.properties` and set the appropriate database connection and other settings.

5. Run the application:
> mvn spring-boot:run
```

## Usage

Once the backend server is up and running, you can access the RESTful APIs provided by the HiCoder-Backend application.
Here are some of the available endpoints:

- `/api/v1/auth` - Manages user-related operations, such as registration, login, and retrieving user information.
- `/api/v1/chat` - Handles messaging functionalities, allowing users to send and receive messages.
- `/api/v1/post` - Manages post-related operations, enabling users to create, edit, and delete posts.

Please refer to the source code and API documentation for more detailed information on the available endpoints and their
usage.
