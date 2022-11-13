package movies

import comments.CommentDTO

data class MovieDTO(
    val id: Int,
    val name: String,
    val mark: Int,
    val year: Int,
    val director: Map<String, String>,
    val studioName: String,
    val description: String,
    val actors: List<Map<String, String>>,
    val genre: List<Map<String, String>>,
    val comments: List<CommentDTO>,
    val picture: String
)
