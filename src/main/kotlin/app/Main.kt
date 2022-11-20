import notices.Notice
import notices.NoticeDAO
import spark.Spark.*

fun main(args: Array<String>) {


    val notice = Notice("tytul", "opis", "obrazek")
    println(notice.id)

    get("/hello") {req, res -> NoticeDAO().all().iterator().next().description}



    path("/users") {

        get("") { req, res ->
            NoticeDAO().findById("63715b69522cc2ce18384abf")
        }

        get("/:id") { req, res ->
            //userDao.findById(req.params("id").toInt())
        }

        get("/email/:email") { req, res ->
            //userDao.findByEmail(req.params("email"))
        }

        post("/create") { req, res ->
            //userDao.save(name = req.queryParams("name"), email = req.queryParams("email"))
            //res.status(201)
            //"ok"
        }

        patch("/update/:id") { req, res ->
            //userDao.update(
            //    id = req.params("id").toInt(),
            //    name = req.queryParams("name"),
            //    email = req.queryParams("email")
            //)
            //"ok"
        }

        delete("/delete/:id") { req, res ->
            //userDao.delete(req.params("id").toInt())
            //"ok"
        }

    }
}
