package database.movies

import database.comments.CommentDTO
import database.movies.attributes.Actor
import database.movies.attributes.Director
import database.movies.attributes.Genre
import database.movies.attributes.Studio
import org.bson.types.ObjectId

data class MovieDTO(
    val id: ObjectId?,
    val name: String?,
    val mark: Int?,
    val year: Int?,
    val director: Director?,
    val studio: Studio?,
    val description: String?,
    val actors: List<Actor>?,
    val genre: List<Genre>?,
    val comments: List<CommentDTO?>?,
    val picture: String?
)
