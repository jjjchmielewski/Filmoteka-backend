package comments

import java.time.Instant

data class CommentDTO(
    val id: Int,
    val description: String,
    val mark: Int,
    val movieId: Int,
    val dateTime: Instant
)
