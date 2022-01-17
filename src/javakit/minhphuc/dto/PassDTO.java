package javakit.minhphuc.dto;

public class PassDTO extends BaseDTO<PassDTO>{

    private String originalPass;
    private String storedPass;

    public String getOriginalPass() {
        return originalPass;
    }

    public void setOriginalPass(String originalPass) {
        this.originalPass = originalPass;
    }

    public String getStoredPass() {
        return storedPass;
    }

    public void setStoredPass(String storedPass) {
        this.storedPass = storedPass;
    }
}
