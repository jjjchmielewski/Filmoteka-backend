package database.movies.attributes

class Actor() {
    var actorFirstName: String? = null
    var actorLastName: String? = null

    constructor(firstName: String, lastName: String): this() {
        actorFirstName = firstName
        actorLastName = lastName
    }
}
