package movies

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import comments.Comment
import database.MongoConnection
import movies.attributes.Actor
import movies.attributes.Director
import movies.attributes.Genre
import movies.attributes.Studio
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
}