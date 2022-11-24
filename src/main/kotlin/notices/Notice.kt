package notices

import org.bson.types.ObjectId

class Notice() {
    var _id: ObjectId? = null
    var title: String? = null
    var description: String? = null
    var picture: String? = null

    constructor(
        title: String,
        description: String,
        picture: String
    ) : this() {
        this._id = ObjectId.get()
        this.title = title
        this.description = description
        this.picture = picture
    }

    fun mapToDTO(): NoticeDTO? {
        return _id?.let {
            NoticeDTO(
                it,
                title,
                description,
                picture
            )
        }
    }
}
    
