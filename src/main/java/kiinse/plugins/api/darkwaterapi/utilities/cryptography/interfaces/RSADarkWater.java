package kiinse.plugins.api.darkwaterapi.utilities.cryptography.interfaces;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.PublicKey;

@SuppressWarnings("UnusedReturnValue")
public interface RSADarkWater {

    @NotNull RSADarkWater generateKeys() throws Exception;

    @NotNull PublicKey getPublicKey();

    @NotNull JSONObject getPublicKeyJson();

    @NotNull String decryptMessage(@NotNull String encryptedText) throws Exception;

    @NotNull String encryptMessage(@NotNull String plainText, @NotNull PublicKey publicKey) throws Exception;

    @NotNull PublicKey recreatePublicKey(@NotNull String exponent, @NotNull String modulus) throws Exception;

    @NotNull PublicKey recreatePublicKey(@NotNull BigInteger exponent, @NotNull BigInteger modulus) throws Exception;
}
