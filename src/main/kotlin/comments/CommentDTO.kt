package comments

import org.bson.types.ObjectId
import java.time.Instant

data class CommentDTO(
    val id: ObjectId?,
    val description: String?,
    val mark: Int?,
    val movieId: Int?,
    val dateTime: Instant?
)
