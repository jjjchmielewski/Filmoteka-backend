package database.comments

import org.bson.types.ObjectId

class Comment() {
    var commentId: String? = null
    var commentDescription: String? = null
    var commentMark: Int? = null
    var commentUser: String? = null
    var commentMovieId: String? = null
    var commentDateTime: String? = null

    fun completeComment(movieId: String) {
        this.commentId = ObjectId.get().toString()
        this.commentMovieId = movieId
    }

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
