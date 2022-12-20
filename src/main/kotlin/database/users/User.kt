package database.users

import database.comments.Comment
import database.comments.CommentDTO
import app.common.encryption.Encryptor
import database.movies.attributes.Actor
import database.movies.attributes.Director
import database.movies.attributes.Genre
import database.movies.attributes.Studio
import org.bson.types.ObjectId

class User() {
    var id: ObjectId? = null
    var login: String? = null
    var password: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var role: String? = null
    var favouriteGenre: List<Genre>? = null
    var observedMovie: List<String>? = null
    var observedActor: List<Actor>? = null
    var observedStudio: List<Studio>? = null
    var observedDirector: List<Director>? = null
    var commentCollection: List<Comment>? = null

    constructor(
        login: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String
    ) : this() {
        this.id = ObjectId.get()
        this.login = login
        this.password = Encryptor.encrypt(password)
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.completeUserProfile()
    }

    fun completeUserProfile() {
        this.role = "USER"
        this.favouriteGenre = mutableListOf()
        this.observedMovie = mutableListOf()
        this.observedActor = mutableListOf()
        this.observedStudio = mutableListOf()
        this.observedDirector = mutableListOf()
        this.commentCollection = mutableListOf()
    }

    fun encryptPassword() {
        val encryptedPass = password?.let { Encryptor.encrypt(it) }
        this.password = encryptedPass
    }

    fun mapToDTO(): UserDTO {
        val commentsDTO = mutableListOf<CommentDTO?>()
        commentCollection?.forEach {
            commentsDTO.add(it.mapToDTO())
        }

        return UserDTO(
            id.toString(),
            firstName,
            lastName,
            email,
            role,
            favouriteGenre,
            observedMovie,
            observedActor,
            observedStudio,
            observedDirector,
            commentsDTO
        )
    }
}
