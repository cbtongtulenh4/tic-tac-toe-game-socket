package javakit.minhphuc.dto;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class CryptionDTO extends BaseDTO<CryptionDTO>{
    private SecretKey key;
    private IvParameterSpec iv;
    private String rawString;
    private String algorithm;


    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public IvParameterSpec getIv() {
        return iv;
    }

    public void setIv(IvParameterSpec iv) {
        this.iv = iv;
    }

    public String getRawString() {
        return rawString;
    }

    public void setRawString(String rawString) {
        this.rawString = rawString;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
