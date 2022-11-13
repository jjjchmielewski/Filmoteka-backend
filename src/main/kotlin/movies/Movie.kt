package movies

import comments.Comment
import comments.CommentDTO

class Movie(
    var id: Int,
    var name: String,
    var mark: Int,
    var year: Int,
    var director: Map<String, String>,
    var studioName: String,
    var description: String,
    var actors: List<Map<String, String>>,
    var genre: List<Map<String, String>>,
    var comments: List<Comment>,
    var picture: String
) {
    fun mapToDTO(): MovieDTO {
        val commentsDTO = mutableListOf<CommentDTO>()
        comments.forEach {
            commentsDTO.add(it.mapToDTO())
        }

        return MovieDTO(
            id,
            name,
            mark,
            year,
            director,
            studioName,
            description,
            actors,
            genre,
            commentsDTO,
            picture
        )
    }
}
