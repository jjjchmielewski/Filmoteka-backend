package users

import comments.Comment
import comments.CommentDTO
import movies.attributes.Actor
import movies.attributes.Director
import movies.attributes.Genre
import movies.attributes.Studio

class User(
    var id: Int,
    var login: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var role: Role,
    var favouriteGenre: List<Genre>,
    var observedMovie: List<String>,
    var observedActor: List<Actor>,
    var observedStudio: List<Studio>,
    var observedDirector: List<Director>,
    var commentCollection: List<Comment>
) {
    fun mapToDTO(): UserDTO {
        val commentsDTO = mutableListOf<CommentDTO?>()
        commentCollection.forEach {
            commentsDTO.add(it.mapToDTO())
        }

        return UserDTO(
            id,
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
