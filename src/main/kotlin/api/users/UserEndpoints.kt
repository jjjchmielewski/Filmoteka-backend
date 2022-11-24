package api.users

import database.users.UserDAO

class UserEndpoints {
    companion object {
        private val userDAO = UserDAO()

        fun initialize() {

        }
    }
}
