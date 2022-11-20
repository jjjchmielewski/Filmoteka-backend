package encryption

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class Encryptor {
    companion object {
        fun encrypt(password: String): String {
            val random = SecureRandom()
            val salt = ByteArray(16)
            random.nextBytes(salt)

            val spec = PBEKeySpec(password.toCharArray(), salt, 2137, 128)
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

            return String(factory.generateSecret(spec).encoded)
        }
    }
}
