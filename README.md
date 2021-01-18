# Zoza hotel
### Project on heroku
This project is available on the heroku platform at: https://zoza-hotel.herokuapp.com/.
### Authentication 
Admin account:
* username: admin,
* email: admin@gmail.com,
* password: admin.

Manager account:
* username: manager,
* email: manager@gmail.com,
* password: manager. 

User account:
* username: user,
* email: user@gmail.com,
* password: user. 

The admin account differs from the manager in that the manager cannot delete users. Accounts created by signing in have access as normal users by default. 
### Database layer
The PostgreSQL database was used in this project.
Created tables: users, customer, employee, employee contact, reservation, room.
Relations between tables: 
* users OneToOne customer,
* customer OneToMany reservation,
* reservation ManyToMany room,
* employee OneToOne employeeContact.
