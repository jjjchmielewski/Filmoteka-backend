package api.movies

import app.common.Authorization
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import database.comments.Comment
import database.movies.Movie
import database.movies.MovieDAO
import database.movies.attributes.Actor
import database.movies.attributes.Director
import database.movies.attributes.Genre
import database.movies.attributes.Studio
import org.eclipse.jetty.http.HttpHeader
import spark.Spark.*

class MovieEndpoints {
    companion object {
        private val movieDAO = MovieDAO()

        fun initialize() {

            path("/database/movies") {

                get("") { request, response ->
                    if (Authorization.validateUser(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        Gson().toJson(movieDAO.all().map { it.mapToDTO() })
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                get("/id/:id") { request, response ->
                    if (Authorization.validateUser(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        Gson().toJson(movieDAO.findById(request.params("id"))?.mapToDTO())
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                get("/news") { request, response ->
                    if (Authorization.validateUser(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        val allMovies = movieDAO.all()
                        Gson().toJson(allMovies.subList(allMovies.size - 3, allMovies.lastIndex).map { it.mapToDTO() })
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                get("/name/:name") { request, response ->
                    if (Authorization.validateUser(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        Gson().toJson(movieDAO.findByName(request.params("name")).map { it.mapToDTO() })
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                get("/search") { request, response ->
                    if (Authorization.validateUser(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        val result = movieDAO.search(
                            request.queryParams("name"),
                            request.queryParams("year").toInt(),
                            request.queryParams("director"),
                            request.queryParams("actor")
                        ).map { it.mapToDTO() }
                        if (result.isNotEmpty()) {
                            Gson().toJson(result)
                        } else {
                            response.status(404)
                            "Movies with requested params not found"
                        }

                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                get("/ranking") { request, response ->
                    if (Authorization.validateUser(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        Gson().toJson(movieDAO.ranking().map { it.mapToDTO() })
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                post("/add") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        val movie = Gson().fromJson(request.body(), Movie::class.java)
                        movieDAO.add(movie)
                        response.status(201)
                        "New movie added"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                put("/update/:id") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        val type = object:TypeToken<Map<String, Any>>(){}.type
                        val movieParamsMap = Gson().fromJson<Map<String, Any>>(request.body(), type)
                        movieDAO.update(
                            id = movieParamsMap["id"] as String,
                            name = movieParamsMap["name"] as String?,
                            mark = movieParamsMap["mark"] as Int?,
                            year = movieParamsMap["year"] as Int?,
                            director = movieParamsMap["director"] as Director?,
                            studio = movieParamsMap["actors"] as Studio?,
                            description = movieParamsMap["description"] as String?,
                            actors = movieParamsMap["actors"] as List<Actor>?,
                            genre = movieParamsMap["genre"] as List<Genre>?,
                            picture = movieParamsMap["picture"] as String?
                        )
                        response.status(201)
                        "New movie added"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                post("/comment/:id") { request, response ->
                    if (Authorization.validateUser(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        val comment = Gson().fromJson(request.body(), Comment::class.java)
                        movieDAO.addComment(request.params("id"), comment)
                        response.status(201)
                        "New comment added"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                delete("/delete/:id") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        movieDAO.delete(request.params("id"))
                        response.status(200)
                        "Movie deleted"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }
            }




        }
    }
}
