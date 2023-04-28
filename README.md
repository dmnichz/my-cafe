#  My Cafe Web App

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/csLsD6teNaY/0.jpg)](https://www.youtube.com/watch?v=csLsD6teNaY)


## Description

Online catalog of laptop-friendly places & community of work-in-cafe fans

Link to application [My Cafe Web App](https://mykolakaradzha.github.io/cafe_catalog/#/)
___

## Features
When you open application website you can see list of cafe cards with brief information in each.

### Features available for all users:
- Sorting and filtering cafes by various criteria
- Open detailed information about each cafe
- Registration

### After registration, you can:
- Log in
- Add cafe to your personal list of favourite cafes
- Remove cafe from your personal list of favourite cafes
- Add comments to cafe and rate it
- Log out

We are planning to add more new features
___

## Project structure
Project is built on a three-tier architecture:
1. Presentation layer (controllers);
2. Application layer (services);
3. Data access layer (repositories)

### Configured role access to endpoints
- POST: /api/auth/register - all
- POST: /api/auth/login - user
- POST: /api/auth/refresh-token - user
- GET: /api/cabinet - user
- POST: /api/cabinet/favourite - user
- POST: /api/cabinet/favourite/remove - user
- POST: /api/cabinet/comment - user
- GET: /api/cafe - all
- GET: /api/cafe/{id} - all
___

## Technologies
While developing the backend part of our app we use:
- SpringBoot(Web, Security, Data Jpa, Validation, Tomcat, Devtools)
- Lombok
- JSON, Jsonwebtoken
- MySQL
- AWS
- IntelliJ IDEA
- Maven
- Git/Github
___

## Run Project
### Tools to run backend part of application locally:
- Intellij IDEA
- MySql Workbench
1. Clone this project with IntelliJ IDEA
2. Create DB in MySQL Workbench
3. Run in MySQL Workbench all sql scripts from resources package in settled order to fill the DB
4. Configure your properties in application.properties
5. Run the project in IntelliJ IDEA
6. Open your web-browser and go to http://localhost:8080/ to test any of presented endpoints
