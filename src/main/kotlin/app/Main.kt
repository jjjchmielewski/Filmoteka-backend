import app.UserDao
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import spark.Spark.*

fun main(args: Array<String>) {
    get("/hello") {req, res -> "Hello World!"}

    val userDao = UserDao()

    path("/users") {

        get("") { req, res ->
            jacksonObjectMapper().writeValueAsString(userDao.users)
        }

        get("/:id") { req, res ->
            userDao.findById(req.params("id").toInt())
        }

        get("/email/:email") { req, res ->
            userDao.findByEmail(req.params("email"))
        }

        post("/create") { req, res ->
            userDao.save(name = req.queryParams("name"), email = req.queryParams("email"))
            res.status(201)
            "ok"
        }

        patch("/update/:id") { req, res ->
            userDao.update(
                id = req.params("id").toInt(),
                name = req.queryParams("name"),
                email = req.queryParams("email")
            )
            "ok"
        }

        delete("/delete/:id") { req, res ->
            userDao.delete(req.params("id").toInt())
            "ok"
        }

    }
}
