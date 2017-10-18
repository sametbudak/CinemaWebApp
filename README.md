# Cinema Web App

Features of cinema's webapp:
* viewing information about movies currently shown in the cinema
* search of movie screenings, showing and booking of tickets
* authentication for cinema's staff: administrators, ticket clerks 
* administrators can add, edit, delete movies; can schedule movies for screenings
* ticket clerks can search movie screenings, ticket bookings; can sell tickets

## Demo data

Stored under *theater* folder.

Administrator login: **admin3**, password: **123**.

Ticket clerk login: **clerk3**, password: **123**.

## Snapshots

Any visitor pages:

![visitor_pages_examples](https://user-images.githubusercontent.com/27282099/31722048-b29e2bae-b423-11e7-8f34-3a29062faeb6.jpg)

![visitor_home](https://user-images.githubusercontent.com/27282099/31718268-f1220712-b417-11e7-8e49-c31b216f6527.jpg)

Administrator working pages:

Ticket Clerk working pages:

## Used tools

Backend:
* Java
* Spring Boot
* Spring MVC
* Spring JpaRepository
* Spring Security
* Maven
* MySql
* IntelliJ IDEA

Frontend:
* Bootstrap
* JSP, JSTL

## Deployment Notes

To start the application you have to change mysql user and password to match yours (file *application.properties* under *src/main/resources* directory)



