package notices

class Notice(
    var id: Int,
    var title: String,
    var description: String,
    var picture: String
) {
    fun mapToDTO(): NoticeDTO {
        return NoticeDTO(
            id,
            title,
            description,
            picture
        )
    }
}
    
