package users

import comments.Comment
import comments.CommentDTO
import encryption.Encryptor
import movies.attributes.Actor
import movies.attributes.Director
import movies.attributes.Genre
import movies.attributes.Studio
import org.bson.types.ObjectId

class User() {
    var _id: ObjectId? = null
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
        email: String,
        role: String
    ) : this() {
        this._id = ObjectId.get()
        this.login = login
        this.password = Encryptor.encrypt(password)
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.role = role
    }

    fun mapToDTO(): UserDTO {
        val commentsDTO = mutableListOf<CommentDTO?>()
        commentCollection?.forEach {
            commentsDTO.add(it.mapToDTO())
        }

        return UserDTO(
            _id,
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
