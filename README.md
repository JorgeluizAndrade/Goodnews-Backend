# Goodnews-Backend

## Project for the Gospel.
Jesus is King!
## Endpoints

### POST /register
Register new User
**URL:** `http://localhost:8080/api/auth/register`

**Body (raw JSON):**
```json
{
    "name": "Jorge",
    "lastname": "Andrade",
    "email": "jorge1718@gmail.com",
    "password": "1234",
    "role": "ADMIN"
}
```

## POST /login
Faz login de um usuário.

**URL:** http://localhost:8080/api/auth/login

**Body (raw JSON):**
```json

    {
    "email": "jorge1718@gmail.com",
    "password": "1234"


}
```

## GET /user/{id}
Find user By id

**URL:** http://localhost:8080/api/user/480c742a-02bd-4d66-ab81-3ce513394e0b

## DELETE /user/{id}

Delete User by id
**URL:** http://localhost:8080/api/user/480c742a-02bd-4d66-ab81-3ce513394e0b


**Authorization:** Bearer Token

## PUT /user/{id}
Update User by id
**URL:** http://localhost:8080/api/user/480c742a-02bd-4d66-ab81-3ce513394e0b
**Authorization:** Bearer Token


# Post 

## GET /posts

return all posts

**URL:** http://localhost:8080/api/posts

## POST /posts
Create new post

**Body (raw JSON):**
```json
{
   {
    "title": "Jesus é o Caminho",
    "text": "O Verbo que se fez carne veio ao mundo, não para condenar o mundo, mas para que seja salvo por Ele.",
    "user": {
        "id": "2e612609-358d-4642-a850-d0c062722fe8"
    }
}
}
```

**URL:** http://localhost:8080/api/posts

**Authorization:** Bearer Token

## DELETE /posts/{id}

Delete post by ID

**URL:** http://localhost:8080/api/posts/d45e8db5-2fa5-49cf-bc9a-29632b6a764a]
**Authorization:** Bearer Token


##  PUT /posts - will be change soon.
Update Post 

**Body (raw JSON):**
```json
{
   {
    "title": "Jesus é a vida",
    "text": "Santo, Santo, Santo é o Senhor!",
    "user": {
        "id": "2e612609-358d-4642-a850-d0c062722fe8"
    }
}
}
```

**URL:** http://localhost:8080/api/posts/d45e8db5-2fa5-49cf-bc9a-29632b6a764a
**Authorization:** Bearer Token

## Autores

- [@JorgeluizAndrade](https://github.com/JorgeluizAndrade)

