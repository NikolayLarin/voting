## Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository.

It should contain the code and **README.md with API documentation and curl commands to get data for voting and vote.**

------

## Implementation.

### Curl samples (application deployed in application context `voting`).
> For windows use `Git Bash`

#### get All Restaurants
`curl -s http://localhost:8080/voting/restaurants`

#### create new User
`curl -s -X POST -d '{"email":"new@gmail.com","password":"password","roles":["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8'  http://localhost:8080/voting/profile/register`

#### get All Today Menus
`curl -s http://localhost:8080/voting/restaurants/menus --user new@gmail.com:password`

#### create Vote of current user for Restaurant
`curl -s -X POST -d '{"restaurantId":100002}' -H 'Content-Type:application/json;charset=UTF-8'  http://localhost:8080/voting/votes --user new@gmail.com:password`

#### update Vote of current user for Restaurant
`curl -s -X PUT -d '{"id":100032,"restaurantId":100003}' -H 'Content-Type:application/json;charset=UTF-8'  http://localhost:8080/voting/votes/100032 --user new@gmail.com:password`

#### get Vote
`curl -s http://localhost:8080/voting/votes/100032 --user new@gmail.com:password`

#### get Vote not found
`curl -s -v http://localhost:8080/voting/votes/1 --user new@gmail.com:password`

#### delete Vote
`curl -s -X DELETE http://localhost:8080/voting/votes/100032 --user new@gmail.com:password`
