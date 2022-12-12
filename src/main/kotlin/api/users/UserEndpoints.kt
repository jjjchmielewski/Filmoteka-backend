package api.users

import app.common.Authorization
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import database.movies.attributes.Actor
import database.movies.attributes.Director
import database.movies.attributes.Genre
import database.movies.attributes.Studio
import database.users.User
import database.users.UserDAO
import org.bson.types.ObjectId
import org.eclipse.jetty.http.HttpHeader
import spark.Spark.*

class UserEndpoints {
    companion object {
        private val userDAO = UserDAO()

        fun initialize() {

            post("/login") { request, response ->
                if (Authorization.validateUser(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                    response.status(200)
                    return@post Gson().toJson(userDAO.findByAuthorization(request.headers(HttpHeader.AUTHORIZATION.asString())))
                } else {
                    response.status(401)
                    "Bad credentials"
                }
            }

            post("/register") { request, response ->
                if (request.body() != null) {
                    val user = Gson().fromJson(request.body(), User::class.java)
                    if (user.login?.let { userDAO.findByLogin(it) } == null) {
                        user.completeUserProfile()
                        user.encryptPassword()
                        userDAO.add(user)
                        response.status(201)
                        return@post "User created"
                    } else {
                        response.status(403)
                        return@post "User with this login already exists"
                    }
                } else {
                    response.status(400)
                    return@post "Empty request"
                }
            }

            path("/users") {

                get("") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        Gson().toJson(userDAO.all().map { it.mapToDTO() })
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                put("/update") { request, response ->
                    if (request.body() != null) {
                        val type = object : TypeToken<Map<String, String>>() {}.type
                        val userParamsMap = Gson().fromJson<Map<String, String>>(request.body(), type)
                        if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))
                            || Authorization.getValidatedUser(request.headers(HttpHeader.AUTHORIZATION.asString()))?.id == ObjectId(
                                userParamsMap["id"]
                            )
                        ) {
                            userDAO.update(
                                id = userParamsMap["id"] as String,
                                password = userParamsMap["password"],
                                firstName = userParamsMap["firstName"],
                                lastName = userParamsMap["lastName"],
                                email = userParamsMap["email"]
                            )
                        } else {
                            response.status(401)
                            "Unauthorized to process this operation"
                        }
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                put("/favourite/genre") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))
                        || Authorization.getValidatedUser(request.headers(HttpHeader.AUTHORIZATION.asString()))?.id == ObjectId(
                            request.queryParams("id")
                        )) {
                        userDAO.addFavouriteGenre(request.queryParams("id"), Genre(request.queryParams("genre")))
                        response.status(200)
                        "Genre added to favourite"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                put("/observed/movie") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))
                        || Authorization.getValidatedUser(request.headers(HttpHeader.AUTHORIZATION.asString()))?.id == ObjectId(
                            request.queryParams("id")
                        )) {
                        userDAO.addObservedMovie(request.queryParams("id"), request.queryParams("movie_id"))
                        response.status(200)
                        "Movie added to observed"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                put("/observed/actor") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))
                        || Authorization.getValidatedUser(request.headers(HttpHeader.AUTHORIZATION.asString()))?.id == ObjectId(
                            request.queryParams("id")
                        )) {
                        userDAO.addObservedActor(
                            request.queryParams("id"),
                            request.queryParams("actor").split(" ").let {
                                Actor(it[0], it[1])
                            }
                        )
                        response.status(200)
                        "Actor added to observed"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                put("/observed/director") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))
                        || Authorization.getValidatedUser(request.headers(HttpHeader.AUTHORIZATION.asString()))?.id == ObjectId(
                            request.queryParams("id")
                        )) {
                        userDAO.addObservedDirector(
                            request.queryParams("id"),
                            request.queryParams("director").split(" ").let {
                                Director(it[0], it[1])
                            }
                        )
                        response.status(200)
                        "Director added to observed"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                put("/observed/studio") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))
                        || Authorization.getValidatedUser(request.headers(HttpHeader.AUTHORIZATION.asString()))?.id == ObjectId(
                            request.queryParams("id")
                        )) {
                        userDAO.addObservedStudio(request.queryParams("id"), Studio(request.queryParams("studio")))
                        response.status(200)
                        "Studio added to observed"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                put("/role") { request, response ->
                    if (Authorization.validateAdmin(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        userDAO.updateRole(request.queryParams("id"), request.queryParams("role"))
                        response.status(200)
                        "Role updated"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                delete("/delete/:id") { request, response ->
                    if (Authorization.validateAdmin(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        userDAO.delete(request.params("id"))
                        response.status(200)
                        "User deleted"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }
            }
        }
    }
}
