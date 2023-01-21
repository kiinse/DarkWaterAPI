// MIT License
//
// Copyright (c) 2022 kiinse
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
package kiinse.me.plugins.darkwaterapi.core.utilities.cryptography

import kiinse.me.plugins.darkwaterapi.api.utilities.cryptography.RSADarkWater
import org.apache.hc.client5.http.utils.Base64
import org.json.JSONObject
import java.math.BigInteger
import java.security.*
import java.security.interfaces.RSAPublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.crypto.Cipher

@Suppress("unused")
class DarkRSA : RSADarkWater {
    private val keys: MutableMap<KeyType, Key> = EnumMap(KeyType::class.java)

    init {
        generateKeys()
    }

    @Throws(Exception::class)
    override fun generateKeys(): RSADarkWater {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val keyPair: KeyPair = keyPairGenerator.generateKeyPair()
        val privateKey = keyPair.private
        val publicKey = keyPair.public
        keys[KeyType.PRIVATE] = privateKey
        keys[KeyType.PUBLIC] = publicKey
        return this
    }

    override val publicKey: PublicKey
        get() = keys[KeyType.PUBLIC] as PublicKey
    override val publicKeyJson: JSONObject
        get() {
            val key = publicKey as RSAPublicKey
            val json = JSONObject()
            json.put("exponent", key.publicExponent)
            json.put("modulus", key.modulus)
            return json
        }

    @Throws(Exception::class)
    override fun decryptMessage(encryptedText: String): String {
        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
        cipher.init(Cipher.DECRYPT_MODE, keys[KeyType.PRIVATE])
        return cipher.doFinal(Base64.decodeBase64(encryptedText)).contentToString()
    }

    @Throws(Exception::class)
    override fun encryptMessage(plainText: String, publicKey: PublicKey): String {
        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return Base64.encodeBase64String(cipher.doFinal(plainText.toByteArray()))
    }

    @Throws(Exception::class)
    override fun recreatePublicKey(exponent: String, modulus: String): PublicKey {
        return recreatePublicKey(BigInteger(exponent), BigInteger(modulus))
    }

    @Throws(Exception::class)
    override fun recreatePublicKey(exponent: BigInteger, modulus: BigInteger): PublicKey {
        return KeyFactory.getInstance("RSA").generatePublic(RSAPublicKeySpec(modulus, exponent))
    }

    private enum class KeyType {
        PUBLIC,
        PRIVATE
    }
}