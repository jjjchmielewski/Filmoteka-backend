package users

import com.mongodb.client.FindIterable
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import comments.Comment
import database.MongoConnection
import encryption.Encryptor
import movies.attributes.Actor
import movies.attributes.Director
import movies.attributes.Genre
import movies.attributes.Studio
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UserDAO {
    private val usersCollection = MongoConnection.getDatabase().getCollection("user", User::class.java)

    fun findById(id: String): User? {
        logger.info("getUserById")
        return usersCollection.find(eq("_id", ObjectId(id))).first()
    }

    fun all(): List<User> {
        return usersCollection.find().into(mutableListOf())
    }

    fun add(user: User) {
        usersCollection.insertOne(user)
    }

    fun update(
        id: String,
        login: String?,
        password: String?,
        firstName: String?,
        lastName: String?,
        email: String?,
        role: Role?
    ) {
        val updateFields = mutableListOf<Bson>()
        login?.let { updateFields.add(Updates.set("login", login)) }
        password?.let { updateFields.add(Updates.set("password", Encryptor.encrypt(password))) }
        firstName?.let { updateFields.add(Updates.set("firstName", firstName)) }
        lastName?.let { updateFields.add(Updates.set("lastName", lastName)) }
        email?.let { updateFields.add(Updates.set("email", email)) }
        role?.let { updateFields.add(Updates.set("role", role)) }

        usersCollection.updateOne(eq("_id", ObjectId(id)), Updates.combine(updateFields))
    }

    fun addFavouriteGenre(id: String, genre: Genre) {
        usersCollection.updateOne(eq("_id", ObjectId(id)), Updates.addToSet("favouriteGenre", genre))
    }

    fun addObservedMovie(id: String, movieId: String) {
        usersCollection.updateOne(eq("_id", ObjectId(id)), Updates.addToSet("observedMovie", movieId))
    }

    fun addObservedActor(id: String, actor: Actor) {
        usersCollection.updateOne(eq("_id", ObjectId(id)), Updates.addToSet("observedActor", actor))
    }

    fun addObservedStudio(id: String, studio: Studio) {
        usersCollection.updateOne(eq("_id", ObjectId(id)), Updates.addToSet("observedStudio", studio))
    }

    fun addObservedDirector(id: String, director: Director) {
        usersCollection.updateOne(eq("_id", ObjectId(id)), Updates.addToSet("observedDirector", director))
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
