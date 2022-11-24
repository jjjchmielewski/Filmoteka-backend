package api.common

import encryption.Encryptor
import users.UserDAO
import java.lang.reflect.MalformedParametersException
import java.util.*

class Authorization {
    companion object {
        private val userDAO = UserDAO()

        fun validateUser(value: String): Boolean {
            val credentials: List<String>
            try {
                val authString = value.substring(6)
                val decodedAuthString = String(Base64.getDecoder().decode(authString))
                credentials = decodedAuthString.split(":")
            } catch (exception: Exception) {
                throw MalformedParametersException("Basic auth string is malformed")
            }

            val user = userDAO.findByLogin(credentials[0])
            return if(user != null) {
                val encodedPass = Encryptor.encrypt(credentials[1])
                user.login == credentials[0] && user.password == encodedPass

            } else {
                false
            }
        }
    }
}
