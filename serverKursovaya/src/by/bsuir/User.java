package by.bsuir;


public class User {
    private Integer idusers;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    public User(Integer idusers, String firstName, String lastName, String email, String username, String password) {
        this.idusers = idusers;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User() {}


    public Integer getID() {
        return idusers;
    }

    public void setID(int idusers) {
        this.idusers = idusers;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

