package database

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.util.Properties

class MongoConnection {

    companion object {
        private var database: MongoDatabase? = null

        private fun initiate() {
            logger.info("<<------------------Initiating database connection-------------------->>")
            val path = FileInputStream("src/main/resources/application.properties")
            val props = Properties()
            props.load(path)

            val pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build())
            val codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry)
            val clientSettings = MongoClientSettings.builder()
                .applyConnectionString(ConnectionString(props.getProperty("database.uri")))
                .codecRegistry(codecRegistry)
                .build()

            val mongoClient = MongoClients.create(clientSettings)
            database = mongoClient.getDatabase("filmotheque")
        }

        fun getDatabase(): MongoDatabase {
            return if (database != null)
                database as MongoDatabase
            else {
                initiate()
                database as MongoDatabase
            }
        }

        val logger = LoggerFactory.getLogger(this::class.java)
    }
}
