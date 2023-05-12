package at.kaindorf.models;

public class Token {
    private String value;

    public Token(String token) {
        this.value = token;
    }

    public Token() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
