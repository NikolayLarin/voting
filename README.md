### curl samples (application deployed in application context `voting`).
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
