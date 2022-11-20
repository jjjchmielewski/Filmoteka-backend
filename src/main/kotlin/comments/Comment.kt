package comments

import org.bson.types.ObjectId
import java.time.Instant

class Comment {
    var id: ObjectId? = null
    var description: String? = null
    var mark: Int? = null
    var movieId: Int? = null
    var dateTime: Instant? = null

    fun mapToDTO(): CommentDTO? {
        return id?.let {
            CommentDTO(
                it,
                description,
                mark,
                movieId,
                dateTime
            )
        }
    }
}

