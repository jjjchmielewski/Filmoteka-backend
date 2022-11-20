package users

import comments.CommentDTO
import movies.attributes.Actor
import movies.attributes.Director
import movies.attributes.Genre
import movies.attributes.Studio

data class UserDTO (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role,
    val favouriteGenre: List<Genre>,
    val observedMovie: List<String>,
    val observedActor: List<Actor>,
    val observedStudio: List<Studio>,
    val observedDirector: List<Director>,
    val commentCollection: List<CommentDTO?>
)
