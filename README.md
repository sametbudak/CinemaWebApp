# Cinema Web App

Features of cinema's webapp:
* viewing information about movies currently shown in the cinema 
   - start page in case of localhost deployment: http://localhost:8085/
* search of movie screenings, showing and booking of tickets
* authentication for cinema's staff: 
   - administrators (login page in case of localhost deployment: http://localhost:8085/admin/login ) 
   - ticket clerks (login page in case of localhost deployment: http://localhost:8085/clerk/login ) 
* administrators can add, edit, delete movies; can schedule movies for screenings
* ticket clerks can search movie screenings, ticket bookings; can sell tickets

## Demo data

Stored under *theater* folder.

Administrator login: **admin3**, password: **123**.

Ticket clerk login: **clerk3**, password: **123**.

## Used tools

Backend:
* Java
* Spring Boot
* Spring MVC
* Spring JpaRepository
* Spring Security
* Maven
* MySql (v.5)
* IntelliJ IDEA
* Google e-mail account for sending messages

Frontend:
* Bootstrap
* JSP, JSTL

## Some Snapshots

Visitor's pages example:

![visitor_pages_examples](https://user-images.githubusercontent.com/27282099/31722048-b29e2bae-b423-11e7-8f34-3a29062faeb6.jpg)


Administrator's pages examples:

![admin_pages_examples](https://user-images.githubusercontent.com/27282099/31723121-aa516df0-b426-11e7-8d7f-64955550db69.jpg)


Ticket Clerk's pages examples:

![clerk_pages_examples](https://user-images.githubusercontent.com/27282099/31724087-6eddc4a0-b429-11e7-8bc0-45bdcbcc923d.jpg)

## Deployment Notes

To start the application you have to change mysql user and password to match yours (file *application.properties* under *src/main/resources* directory). 



