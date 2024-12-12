import java.io.Serializable;

public class User implements StrSerializable,Serializable {
    protected String username;
    protected String password;
    public enum utype implements Serializable{admin,client};
    protected utype userType;

    public User(String username, String password, User.utype userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public utype getUserType() {
        return userType;
    }
    public void setUserType(utype userType) {
        this.userType = userType;
    }
    @Override
    public String toString() {
        return username+"\t"+password+"\t"+userType;
    }
    // @Override
    public StrSerializable fromString(String s) throws IllegalArgumentException {
        String[] parts=s.split("\t");
        if(parts.length>=3){
            switch (parts[2]) {
                case "admin":
                    return new User(parts[0], parts[1], utype.admin);
                case "client":
                    return new User(parts[0], parts[1], utype.client);
                case "null":
                    return new User(parts[0], parts[1], null);
                default:
                    throw new IllegalArgumentException("unknown value "+parts[2]);
            }
        }
        else throw new IllegalArgumentException("not enough arguments");
    }
    public boolean canlogin(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }
}
