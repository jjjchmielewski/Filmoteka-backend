package database.comments

data class CommentDTO(
    val commentId: Int?,
    val commentDescription: String?,
    val commentMark: Int?,
    val commentMovieId: Int?,
    val commentUser: Int?,
    val commentDateTime: String?
)
