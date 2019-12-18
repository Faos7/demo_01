package com.faos7.demo.utils;

import java.security.SecureRandom;

import javax.annotation.PostConstruct;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtils {

    private static final SecureRandom random = new SecureRandom();
    byte[] key;
    byte[] iv;


    @PostConstruct
    public void init(){
            iv = new byte[16];
            random.nextBytes(iv);
            key = new byte[32];
            random.nextBytes(key);
    }


    private byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data) throws Exception {
        int minSize = cipher.getOutputSize(data.length);
        byte[] outBuf = new byte[minSize];
        int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
        int length2 = cipher.doFinal(outBuf, length1);
        int actualLength = length1 + length2;
        if (actualLength < minSize) {
            byte[] tmp = new byte[actualLength];
            System.arraycopy(outBuf, 0, tmp, 0, actualLength );
            outBuf = tmp;
        }
        return outBuf;
    }

    private byte[] decrypt(byte[] cipher, byte[] key, byte[] iv) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new ZeroBytePadding());
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
        aes.init(false, ivAndKey);
        return cipherData(aes, cipher);
    }

    private byte[] encrypt(byte[] plain, byte[] key, byte[] iv) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine())/*, new ZeroBytePadding()*/);
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
        aes.init(true, ivAndKey);
        return cipherData(aes, plain);
    }

    public String encode(String plainText) throws Exception {
        return new String(Base64.encode(encrypt(plainText.getBytes("UTF-8"), key, iv))).trim();
    }

    public String decode(String encodedText) throws Exception {
        return new String(decrypt(Base64.decode(encodedText.getBytes("UTF-8")), key, iv)).trim();
    }

}
