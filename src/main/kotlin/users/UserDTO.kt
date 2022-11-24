package users

import comments.CommentDTO
import movies.attributes.Actor
import movies.attributes.Director
import movies.attributes.Genre
import movies.attributes.Studio
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
