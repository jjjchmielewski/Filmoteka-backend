package app.common

import app.common.encryption.Encryptor
import database.users.User
import database.users.UserDAO
import java.lang.reflect.MalformedParametersException
import java.util.*

class Authorization {
    companion object {
        private val userDAO = UserDAO()

        fun validateUser(value: String): Boolean {
            val user = getUserFromBasicAuth(value)

            return user != null

        }

        fun validateModerator(value: String): Boolean {
            val user = getUserFromBasicAuth(value)

            return if (user == null) {
                false
            } else {
                user.role == "MOD" || user.role == "ADMIN"
            }
        }

        fun validateAdmin(value: String): Boolean {
            val user = getUserFromBasicAuth(value)

            return if (user == null) {
                false
            } else {
                user.role == "ADMIN"
            }
        }

        fun getValidatedUser(value: String): User? {
            return getUserFromBasicAuth(value)
        }

        private fun getUserFromBasicAuth(value: String): User? {
            val credentials: List<String>
            try {
                val authString = value.substring(6)
                val decodedAuthString = String(Base64.getDecoder().decode(authString))
                credentials = decodedAuthString.split(":")
            } catch (exception: Exception) {
                throw MalformedParametersException("Basic auth string is malformed")
            }

            val user = userDAO.findByLogin(credentials[0])

            return if (user != null) {
                val encodedPass = Encryptor.encrypt(credentials[1])
                if (user.login == credentials[0] && user.password == encodedPass) {
                    return user
                } else {
                    return null
                }
            } else {
                null
            }
        }
    }
}
