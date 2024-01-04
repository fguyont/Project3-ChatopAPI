# Chatop application

The purpose of Chatop is to manage house rentals (creation, detail view, update) by connected users. Messages on rentals can also be sold by users.
This application consists of a back-end project and a front-end project.

## Technologies
- Java
- SpringBoot
- MySQLServer
- Angular 14
- HTML
- CSS
- Cloudinary

## SQL setup
- Install MySQL Server and MYSQL Workbench
- Take note of your user name, your password and the port number for MySQL
- From the Workbench, create a new schema. Take note of the schema name.
- Right-click on your schema and select it as default schema
- Open an explorer and go to this link
  https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring/blob/main/ressources/sql/script.sql
  and copy all the lines
- From the Workbench, open a new query tab and paste all the copied lines. Select all and execute.

## File uploading setup (for rentals images)
- Create a Cloudinary account
- From Cloudinary dashboard, take note of your Cloud Name, API Key and API Secret

## Back-end project setup
- Clone this project https://github.com/fguyont/Project3-ChatopAPI.git
- Go to this file https://github.com/fguyont/Project3-ChatopAPI/blob/main/src/main/resources/application.properties
- For the line spring.datasource.url and after = , put this line jdbc:mysql://localhost:PORT/SCHEMA by replacing PORT and SCHEMA by the SQL port and the schema used respectively
- For the line spring.datasource.username=, insert your SQL user name
- Same thing with the line spring.datasource.password=
- Generate a 64 random hexadecimal characters password, copy it and set the value app.jwt-secret=
- Go the file https://github.com/fguyont/Project3-ChatopAPI/blob/main/src/main/java/com/openclassrooms/chatopapi/fileconfig/CloudinaryConfig.java and insert your Cloudinary credentials : Cloud Name, API Key, API Secret

## Front-end project setup
- Clone this project https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring.git
- Enter the project folder
- Open a console and enter npm install

## Application run
- Run the back-end project
- You can now use the back-end project with Postman or with Swagger http://localhost:3001/swagger-ui/index.html
- To use the application with an user interface, open a terminal, go to the front-end project directory, enter npm start and navigate to `http://localhost:4200/`.

## Author

Frédéric Guyont
