package comments

import java.time.Instant

class Comment {
    var commentId: Int? = null
    var commentDescription: String? = null
    var commentMark: Int? = null
    var commentUser: Int? = null
    var commentMovieId: Int? = null
    var commentDateTime: String? = null

    fun mapToDTO(): CommentDTO {
        return CommentDTO(
            commentId,
            commentDescription,
            commentMark,
            commentUser,
            commentMovieId,
            commentDateTime
        )
    }
}
