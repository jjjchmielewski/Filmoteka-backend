package notices

import org.bson.types.ObjectId

data class NoticeDTO(
    val id: ObjectId?,
    val title: String?,
    val description: String?,
    val picture: String?
)
