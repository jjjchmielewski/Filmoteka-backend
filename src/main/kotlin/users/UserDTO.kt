package users

import comments.CommentDTO

data class UserDTO (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role,
    val favouriteGenre: List<Map<String, String>>,
    val observedMovie: List<Map<String, String>>,
    val observedActor: List<Map<String, String>>,
    val observedStudio: List<Map<String, String>>,
    val observedDirector: List<Map<String, String>>,
    val commentCollection: List<CommentDTO>
)
