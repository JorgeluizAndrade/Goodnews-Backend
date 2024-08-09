# Goodnews-Backend 

This document outlines the API endpoints for the Goodnews backend, a project focused on the Gospel.

**Supported Methods:**

* POST
* GET
* DELETE
* PUT

**Base URL:**

http://localhost:8080/api/

**Authentication:**

Certain endpoints require authorization using a Bearer token. The token itself is not provided here, but denoted by `<token>`.

## User Management

**Register User (POST):**

* Endpoint: http://localhost:8080/api/auth/register
* Body (raw JSON):

```
{
  "name":"Jorge",
  "lastname":"Andrade",  
  "email":"jorge1718@gmail.com",
  "password":"1234",
  "role":"ADMIN"
}
```

**Login User (POST):**

* Endpoint: http://localhost:8080/api/auth/login
* Body (raw JSON):

```
{
  "email":"jorge1718@gmail.com",
    "password":"1234"
}
```

**Get User Information (GET):**

* Endpoint: http://localhost:8080/api/user
* Requires Authorization (Bearer Token)

**Get User by ID (GET):**

* Endpoint: http://localhost:8080/api/user/<user_id> (Replace `<user_id>` with the actual user ID)
* Requires Authorization (Bearer Token)

**Update User Information (PUT):**

* Endpoint: http://localhost:8080/api/user/<user_id> (Replace `<user_id>` with the actual user ID)
* Requires Authorization (Bearer Token)
* Body (raw JSON):

```
{
  "name":"Jorge Luiz",
  "lastname":"Andrade"
}
```

**Delete User (DELETE):**

* Endpoint: http://localhost:8080/api/user/<user_id> (Replace `<user_id>` with the actual user ID)
* Requires Authorization (Bearer Token)

## Post Management

**Create Post (POST):**

* Endpoint: http://localhost:8080/api/posts
* Requires Authorization (Bearer Token)
* Body (raw JSON):

```
{
  "title": "Jesus é o Caminho",
  "text": "O Verbo que se fez carne veio ao mundo, não para codenar o mundo, mas para que seja salvo por Ele.",
  "user": {
    "id": "2e612609-358d-4642-a850-d0c062722fe8"
  }
}
```

**Get All Posts (GET):**

* Endpoint: http://localhost:8080/api/posts

**Get Post by ID (GET):**

* Endpoint: http://localhost:8080/api/posts/<post_id> (Replace `<post_id>` with the actual post ID)

**Delete Post (DELETE):**

* Endpoint: http://localhost:8080/api/posts/<post_id> (Replace `<post_id>` with the actual post ID)
* Requires Authorization (Bearer Token)

**Update Post (PUT):**

* Endpoint: http://localhost:8080/api/posts
* Requires Authorization (Bearer Token)
* Body (raw JSON):

```
{
  "title": "Jesus é a vida",
  "text": "Santo, Santo, Santo é o Senhor!",
  "user": {
    "id": "2e612609-358d-4642-a850-d0c062722fe8"
  }
}
```

This document provides a basic overview of the Goodnews backend API in raw format. 
