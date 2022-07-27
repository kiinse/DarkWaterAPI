package kiinse.plugins.api.darkwaterapi.utilities.cryptography;

import kiinse.plugins.api.darkwaterapi.utilities.cryptography.interfaces.RSADarkWater;
import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAEncrypt implements RSADarkWater {

    private final Map<KeyType, Key> keys = new HashMap<>();

    private enum KeyType {
        PUBLIC,
        PRIVATE
    }

    public RSAEncrypt() throws Exception {
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

}
