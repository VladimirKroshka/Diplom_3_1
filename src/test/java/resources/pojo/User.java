package resources.pojo;

public class User {
    private String name;
    private String email;
    private String password;
    private String accessToken;

    // Конструктор
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accessToken = this.accessToken;

    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return accessToken;
    }

    public void setToken(String token) {
        this.accessToken = token;
    }
}
