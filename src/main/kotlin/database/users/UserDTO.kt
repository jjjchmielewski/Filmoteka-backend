package database.users

import database.comments.CommentDTO
import database.movies.attributes.Actor
import database.movies.attributes.Director
import database.movies.attributes.Genre
import database.movies.attributes.Studio
import org.bson.types.ObjectId

data class UserDTO (
    val id: ObjectId?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val role: String?,
    val favouriteGenre: List<Genre>?,
    val observedMovie: List<String>?,
    val observedActor: List<Actor>?,
    val observedStudio: List<Studio>?,
    val observedDirector: List<Director>?,
    val commentCollection: List<CommentDTO?>?
)
