package database.comments

data class CommentDTO(
    val commentId: String?,
    val commentDescription: String?,
    val commentMark: Int?,
    val commentMovieId: String?,
    val commentUser: String?,
    val commentDateTime: String?
)
