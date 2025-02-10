package basic.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;

public class User {
    private long id;
    private String name;
    private String email;

    public User(long l, String john, String mail) {
        this.id = l;
        this.name = john;
        this.email = mail;
    }

    // standard getters/setters/constructors

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int) id;
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        hash = 31 * hash + (email == null ? 0 : email.hashCode());
        System.out.println("hashCode() called - Computed hash: " + hash);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id
                && (name.equals(user.name))
                && (email.equals(user.email));
    }

    // getters and setters here

}
