package movies.attributes

class Director() {
    var directorFirstName: String? = null
    var directorLastName: String? = null

    constructor(firstName: String, lastName: String): this() {
        directorFirstName = firstName
        directorLastName = lastName
    }
}
