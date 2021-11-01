package domain;

public class PIFtoken {
    private String token;
    private Position position;

    public PIFtoken(String token, Position position) {
        this.token = token;
        this.position = position;
    }

    public String getToken() {
        return token;
    }

    public Position getPosition() {
        return position;
    }
}
