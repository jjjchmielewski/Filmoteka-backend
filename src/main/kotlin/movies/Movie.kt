package movies

import comments.Comment
import comments.CommentDTO
import movies.attributes.Actor
import movies.attributes.Director
import movies.attributes.Genre
import movies.attributes.Studio
import org.bson.types.ObjectId

class Movie {
    var id: ObjectId? = null
    var name: String? = null
    var mark: Int? = null
    var year: Int? = null
    var director: Director? = null
    var studio: Studio? = null
    var description: String? = null
    var actors: List<Actor>? = null
    var genre: List<Genre>? = null
    var comments: List<Comment>? = null
    var picture: String? = null
    fun mapToDTO(): MovieDTO {
        val commentsDTO = mutableListOf<CommentDTO?>()
        comments?.forEach {
            commentsDTO.add(it.mapToDTO())
        }

        return MovieDTO(
            id,
            name,
            mark,
            year,
            director,
            studio,
            description,
            actors,
            genre,
            commentsDTO,
            picture
        )
    }
}
