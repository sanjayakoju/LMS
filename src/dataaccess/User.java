package dataaccess;

import java.io.Serializable;

final public class User implements Serializable {

    private static final long serialVersionUID = 5147265048973262104L;

    private final String id;

    private final String password;
    private final Auth authorization;

    User(String id, String pass, Auth auth) {
        this.id = id;
        this.password = pass;
        this.authorization = auth;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Auth getAuthorization() {
        return authorization;
    }

    @Override
    public String toString() {
        return "[" + id + ":" + password + ", " + authorization.toString() + "]";
    }

}
