package database.users

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import database.MongoConnection
import app.common.encryption.Encryptor
import database.movies.attributes.Actor
import database.movies.attributes.Director
import database.movies.attributes.Genre
import database.movies.attributes.Studio
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.MalformedParametersException
import java.util.*

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

    fun findByLogin(login: String): User? {
        return usersCollection.find(eq("login", login)).first()
    }

    fun findByAuthorization(value: String): User? {
        val credentials: List<String>
        try {
            val authString = value.substring(6)
            val decodedAuthString = String(Base64.getDecoder().decode(authString))
            credentials = decodedAuthString.split(":")
        } catch (exception: Exception) {
            throw MalformedParametersException("Basic auth string is malformed")
        }

        return findByLogin(credentials[0])
    }

    fun update(
        id: String,
        password: String?,
        firstName: String?,
        lastName: String?,
        email: String?
    ) {
        val updateFields = mutableListOf<Bson>()
        password?.let { updateFields.add(Updates.set("password", Encryptor.encrypt(password))) }
        firstName?.let { updateFields.add(Updates.set("firstName", firstName)) }
        lastName?.let { updateFields.add(Updates.set("lastName", lastName)) }
        email?.let { updateFields.add(Updates.set("email", email)) }

        usersCollection.updateOne(eq("_id", ObjectId(id)), Updates.combine(updateFields))
    }

    fun updateRole(id: String, role: String): Boolean {
        when (role) {
            "USER" -> {
                usersCollection.updateOne(eq("_id", ObjectId(id)), Updates.set("role", "USER"))
                return true
            }

            "MOD" -> {
                usersCollection.updateOne(eq("_id", ObjectId(id)), Updates.set("role", "MOD"))
                return true
            }

            "ADMIN" -> {
                usersCollection.updateOne(eq("_id", ObjectId(id)), Updates.set("role", "ADMIN"))
                return true
            }

            else -> {
                return false
            }
        }
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

    fun delete(id: String) {
        usersCollection.deleteOne(eq("_id", ObjectId(id)))
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
