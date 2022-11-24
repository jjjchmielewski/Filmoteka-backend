package api.users

import app.common.Authorization
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
                if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                    response.status(200)
                    "Login confirmed"
                } else {
                    response.status(401)
                    "Bad credentials"
                }
            }

            post("/register") { request, response ->
                if (request.body() != null) {
                    val user = Gson().fromJson(request.body(), User::class.java)
                    if (user.login?.let { userDAO.findByLogin(it) } == null) {
                        userDAO.add(user)
                        response.status(201)
                        response.body("User created")
                    } else {
                        response.status(403)
                        response.body("User with this login already exists")
                    }
                }
            }

            path("/users") {

                get("") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        Gson().toJson(userDAO.all().forEach { it.mapToDTO() })
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                put("/update") { request, response ->
                    if (request.body() != null) {
                        val type = object: TypeToken<Map<String, String>>(){}.type
                        val userParamsMap = Gson().fromJson<Map<String, String>>(request.body(), type)
                        if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))
                            || Authorization.getValidatedUser(request.headers(HttpHeader.AUTHORIZATION.asString()))?._id == ObjectId(userParamsMap["id"])) {
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
            }
        }
    }
}
