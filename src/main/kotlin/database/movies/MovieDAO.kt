package database.movies

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import database.comments.Comment
import database.MongoConnection
import database.movies.attributes.Actor
import database.movies.attributes.Director
import database.movies.attributes.Genre
import database.movies.attributes.Studio
import org.bson.conversions.Bson
import org.bson.types.ObjectId

class MovieDAO {
    private val moviesCollection = MongoConnection.getDatabase().getCollection("movie", Movie::class.java)

    fun findById(id: String): Movie? {
        return moviesCollection.find(Filters.eq("_id", ObjectId(id))).first()
    }

    fun all(): List<Movie> {
        return moviesCollection.find().into(mutableListOf())
    }

    fun add(movie: Movie) {
        moviesCollection.insertOne(movie)
    }

    fun findByName(name: String): List<Movie> {
        return moviesCollection.find(Filters.regex("name", ".*$name.*")).into(mutableListOf())
    }

    fun search(name: String, year: Int, director: String, actor: String): List<Movie> {
        val directorNames = director.split(" ")
        val actorNames = actor.split(" ")
        return moviesCollection.find(Filters.and(
            Filters.regex("name",".*$name.*"),
            Filters.eq("year", year),
            Filters.eq("director", Director(directorNames[0], directorNames[1])),
            Filters.elemMatch("actor", Filters.eq(Actor(actorNames[0], actorNames[1])))
        )).into(mutableListOf())
    }

    fun ranking(): List<Movie> {
        return all().sortedByDescending { it.mark }.subList(0, 8)
    }

    fun update(
        id: String,
        name: String?,
        mark: Int?,
        year: Int?,
        director: Director?,
        studio: Studio?,
        description: String?,
        actors: List<Actor>?,
        genre: List<Genre>?,
        picture: String?
        ) {
        val updateFields = mutableListOf<Bson>()
        name?.let { Updates.set("name", name) }
        mark?.let { Updates.set("mark", mark) }
        year?.let { Updates.set("year", year) }
        director?.let { Updates.set("director", director) }
        studio?.let { Updates.set("studio", studio) }
        description?.let { Updates.set("description", description) }
        actors?.let { Updates.set("actors", actors) }
        genre?.let { Updates.set("genre", genre) }
        picture?.let { Updates.set("picture", picture) }

        moviesCollection.updateOne(Filters.eq("_id", ObjectId(id)), Updates.combine(updateFields))
    }

    fun addComment(id: String, comment: Comment) {
        moviesCollection.updateOne(Filters.eq("_id", ObjectId(id)), Updates.addToSet("comment", comment))
    }

    fun allMovieComments(id: String): List<Comment>? {
        return moviesCollection.find(Filters.eq("_id", ObjectId(id))).first()?.comments
    }

    fun delete(id: String) {
        moviesCollection.deleteOne(Filters.eq("_id", ObjectId(id)))
    }
}
