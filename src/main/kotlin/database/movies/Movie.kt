package database.movies

import database.comments.Comment
import database.comments.CommentDTO
import database.movies.attributes.Actor
import database.movies.attributes.Director
import database.movies.attributes.Genre
import database.movies.attributes.Studio
import org.bson.types.ObjectId

class Movie() {
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

    constructor(
        name: String,
        mark: Int,
        year: Int,
        director: Director,
        studio: Studio,
        description: String,
        actors: List<Actor>,
        genre: List<Genre>,
        picture: String
    ): this() {
        this.name = name
        this.mark = mark
        this.year = year
        this.director = director
        this.studio = studio
        this.description = description
        this.actors = actors
        this.genre = genre
        this.picture = picture
    }

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
