package app

import java.util.concurrent.atomic.AtomicInteger

class UserDao {
    val users = hashMapOf(
        0 to User(name = "Mark", email = "mark@alice.kt", id = 0),
        1 to User(name = "Bob", email = "bob@bob.kt", id = 1),
        2 to User(name = "Carol", email = "carol@carol.kt", id = 2),
        3 to User(name = "Dave", email = "dave@dave.kt", id = 3)
    )

    var lastId: AtomicInteger = AtomicInteger(users.size - 1)

    fun save(name: String, email: String) {
        val id = lastId.incrementAndGet()
        users.put(id, User(name = name, email = email, id = id))
    }

    fun findById(id: Int): User? {
        return users[id]
    }

    fun findByEmail(email: String): User? {
        return users.values.find { it.email == email }
    }

    fun update(id: Int, name: String, email: String) {
        users.put(id, User(name = name, email = email, id = id))
    }

    fun delete(id: Int) {
        users.remove(id)
    }

}
