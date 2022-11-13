package comments

import java.time.Instant

class Comment(
    var id: Int,
    var description: String,
    var mark: Int,
    var movieId: Int,
    var dateTime: Instant
) {
    fun mapToDTO(): CommentDTO {
        return CommentDTO(
            id,
            description,
            mark,
            movieId,
            dateTime
        )
    }
}
