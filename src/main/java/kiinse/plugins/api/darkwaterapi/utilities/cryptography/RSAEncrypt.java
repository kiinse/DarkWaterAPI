package kiinse.plugins.api.darkwaterapi.utilities.cryptography;

import kiinse.plugins.api.darkwaterapi.utilities.cryptography.interfaces.RSADarkWater;
import org.apache.commons.codec.binary.Base64;
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
    public RSADarkWater generateKeys() throws Exception {
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
    public PublicKey getPublicKey() {
        return (PublicKey) keys.get(KeyType.PUBLIC);
    }

    @Override
    public JSONObject getPublicKeyJson() {
        var key = (RSAPublicKey) getPublicKey();
        var json = new JSONObject();
        json.put("exponent", key.getPublicExponent());
        json.put("modulus", key.getModulus());
        return json;
    }

    @Override
    public String decryptMessage(String encryptedText) throws Exception {
        var cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, keys.get(KeyType.PRIVATE));
        return new String(cipher.doFinal(Base64.decodeBase64(encryptedText)));
    }

    @Override
    public String encryptMessage(String plainText, PublicKey publicKey) throws Exception {
        var cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(plainText.getBytes()));
    }

    @Override
    public PublicKey recreatePublicKey(String exponent, String modulus) throws Exception {
        return recreatePublicKey(new BigInteger(exponent), new BigInteger(modulus));
    }

    @Override
    public PublicKey recreatePublicKey(BigInteger exponent, BigInteger modulus) throws Exception {
        return KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
    }

}
