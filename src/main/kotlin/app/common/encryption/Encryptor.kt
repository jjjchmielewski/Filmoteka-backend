package app.common.encryption

import java.util.Base64

class Encryptor {
    companion object {
        fun encrypt(password: String): String {

            val pass = Base64.getEncoder().encode(password.toByteArray())
            val encodedPass = ByteArray(pass.size)

            for (character in pass) {
                encodedPass[pass.indexOf(character)] = (character / 2).toByte()
            }

            return String(encodedPass)

        }
    }
}
