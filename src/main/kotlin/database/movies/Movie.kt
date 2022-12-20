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
    var actor: List<Actor>? = null
    var genre: List<Genre>? = null
    var comment: List<Comment>? = null
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
        this.actor = actors
        this.genre = genre
        this.picture = picture
        completeMovieObject()
    }

    fun completeMovieObject() {
        this.comment = this.comment ?: mutableListOf()
        this.mark = this.mark ?: 0
        this.year = this.year ?: 0
        this.director = this.director ?: Director()
        this.studio = this.studio ?: Studio()
        this.description = this.description ?: ""
        this.actor = this.actor ?: mutableListOf()
        this.genre = this.genre ?: mutableListOf()
        this.picture = this.picture ?: ""
    }

    fun mapToDTO(): MovieDTO {
        val commentsDTO = mutableListOf<CommentDTO?>()
        comment?.forEach {
            commentsDTO.add(it.mapToDTO())
        }

        return MovieDTO(
            id.toString(),
            name,
            mark,
            year,
            director,
            studio,
            description,
            actor,
            genre,
            commentsDTO,
            picture
        )
    }
}
