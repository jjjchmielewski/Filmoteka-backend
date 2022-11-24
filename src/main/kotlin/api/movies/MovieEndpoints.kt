package api.movies

import api.common.Authorization
import com.google.gson.Gson
import movies.MovieDAO
import org.eclipse.jetty.http.HttpHeader
import spark.Spark.*

class MovieEndpoints {
    companion object {
        private val movieDAO = MovieDAO()

        fun initialize() {

            path("/movies") {

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
                        Gson().toJson(movieDAO.search(
                            request.queryParams("name"),
                            request.queryParams("year").toInt(),
                            request.queryParams("director"),
                            request.queryParams("actor")
                        ).map { it.mapToDTO() })
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }
            }




        }
    }
}
