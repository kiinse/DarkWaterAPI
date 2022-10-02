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

package kiinse.plugins.darkwaterapi.core.utilities.cryptography;

import kiinse.plugins.darkwaterapi.api.utilities.cryptography.RSADarkWater;
import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class DarkRSA implements RSADarkWater {

    private final Map<KeyType, Key> keys = new HashMap<>();

    public DarkRSA() throws Exception {
        generateKeys();
    }

    @Override
    public @NotNull RSADarkWater generateKeys() throws Exception {
        var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        var keyPair = keyPairGenerator.generateKeyPair();
        var privateKey = keyPair.getPrivate();
        var publicKey = keyPair.getPublic();
        keys.put(KeyType.PRIVATE, privateKey);
        keys.put(KeyType.PUBLIC, publicKey);
        return this;
    }

    @Override
    public @NotNull PublicKey getPublicKey() {
        return (PublicKey) keys.get(KeyType.PUBLIC);
    }

    @Override
    public @NotNull JSONObject getPublicKeyJson() {
        var key = (RSAPublicKey) getPublicKey();
        var json = new JSONObject();
        json.put("exponent", key.getPublicExponent());
        json.put("modulus", key.getModulus());
        return json;
    }

    @Override
    public @NotNull String decryptMessage(@NotNull String encryptedText) throws Exception {
        var cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, keys.get(KeyType.PRIVATE));
        return new String(cipher.doFinal(Base64.decodeBase64(encryptedText)));
    }

    @Override
    public @NotNull String encryptMessage(@NotNull String plainText, @NotNull PublicKey publicKey) throws Exception {
        var cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(plainText.getBytes()));
    }

    @Override
    public @NotNull PublicKey recreatePublicKey(@NotNull String exponent, @NotNull String modulus) throws Exception {
        return recreatePublicKey(new BigInteger(exponent), new BigInteger(modulus));
    }

    @Override
    public @NotNull PublicKey recreatePublicKey(@NotNull BigInteger exponent, @NotNull BigInteger modulus) throws Exception {
        return KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
    }

    private enum KeyType {
        PUBLIC,
        PRIVATE
    }

}
