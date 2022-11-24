package database.notices

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import database.MongoConnection
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class NoticeDAO {
    private val noticesCollection = MongoConnection.getDatabase().getCollection("notice", Notice::class.java)

    fun findById(id: String): Notice? {
        return noticesCollection.find(eq("_id", ObjectId(id))).first()
    }

    fun all(): List<Notice> {
        return noticesCollection.find().into(mutableListOf())
    }

    fun add(notice: Notice) {
        noticesCollection.insertOne(notice)
    }

    fun update(
        id: String,
        title: String?,
        description: String?,
        picture: String?
    ) {
        val updateFields = mutableListOf<Bson>()
        title?.let { updateFields.add(Updates.set("title", title)) }
        description?.let { updateFields.add(Updates.set("description", description)) }
        picture?.let { updateFields.add(Updates.set("picture", picture)) }

        noticesCollection.updateOne(eq("_id", ObjectId(id)), Updates.combine(updateFields))
    }

    fun delete(id: String) {
        noticesCollection.deleteOne(eq("_id", ObjectId(id)))
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
