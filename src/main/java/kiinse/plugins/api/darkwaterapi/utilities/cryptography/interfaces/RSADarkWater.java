package kiinse.plugins.api.darkwaterapi.utilities.cryptography.interfaces;

import org.json.JSONObject;

import java.math.BigInteger;
import java.security.PublicKey;

@SuppressWarnings("UnusedReturnValue")
public interface RSADarkWater {

    RSADarkWater generateKeys() throws Exception;

    PublicKey getPublicKey();

    JSONObject getPublicKeyJson();

    String decryptMessage(String encryptedText) throws Exception;

    String encryptMessage(String plainText, PublicKey publicKey) throws Exception;

    PublicKey recreatePublicKey(String exponent, String modulus) throws Exception;

    PublicKey recreatePublicKey(BigInteger exponent, BigInteger modulus) throws Exception;
}
