package api.notices

import app.common.Authorization
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import database.notices.Notice
import database.notices.NoticeDAO
import org.eclipse.jetty.http.HttpHeader
import spark.Spark.*

class NoticeEndpoints {
    companion object {
        private val noticeDAO = NoticeDAO()

        fun initialize() {
            path("/notices") {

                get("") { request, response ->
                    if (Authorization.validateUser(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        Gson().toJson(noticeDAO.all().map { it.mapToDTO() })
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                get("/news") { request, response ->
                    Gson().toJson(noticeDAO.news().map { it.mapToDTO() })
                }

                post("/add") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        val notice = Gson().fromJson(request.body(), Notice::class.java)
                        noticeDAO.add(notice)
                        response.status(201)
                        "Notice added"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                put("/update") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        val type = object: TypeToken<Map<String, String>>(){}.type
                        val noticeParamsMap = Gson().fromJson<Map<String, String>>(request.body(), type)
                        noticeDAO.update(
                            id = noticeParamsMap["id"] as String,
                            title = noticeParamsMap["title"],
                            description = noticeParamsMap["description"],
                            picture = noticeParamsMap["picture"]
                        )
                        response.status(200)
                        "Notice updated"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }

                delete("/delete/:id") { request, response ->
                    if (Authorization.validateModerator(request.headers(HttpHeader.AUTHORIZATION.asString()))) {
                        noticeDAO.delete(request.params("id"))
                        response.status(200)
                        "Notice deleted"
                    } else {
                        response.status(401)
                        "Bad credentials"
                    }
                }
            }
        }
    }
}
