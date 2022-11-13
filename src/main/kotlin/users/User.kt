package users

import comments.Comment
import comments.CommentDTO

class User(
    var id: Int,
    var login: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var role: Role,
    var favouriteGenre: List<Map<String, String>>,
    var observedMovie: List<Map<String, String>>,
    var observedActor: List<Map<String, String>>,
    var observedStudio: List<Map<String, String>>,
    var observedDirector: List<Map<String, String>>,
    var commentCollection: List<Comment>
) {
    fun mapToDTO(): UserDTO {
        val commentsDTO = mutableListOf<CommentDTO>()
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
