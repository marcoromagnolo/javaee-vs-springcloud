package jeevsspring.wildfly.backoffice.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
@Entity
@Cacheable
@Table(name="OPERATOR")
public class OperatorEntity implements Serializable {

    @Id
    private String id;

    @OneToOne(fetch=FetchType.LAZY, mappedBy = "operator")
    private OperatorSessionEntity session;

    @Column("USERNAME")
    private String username;

    @Column("PASSWORD")
    private String password;

    @Column("FIRST_NAME")
    private String firstName;

    @Column("LAST_NAME")
    private String lastName;

    @Column("EMAIL")
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OperatorSessionEntity getSession() {
        return session;
    }

    public void setSession(OperatorSessionEntity session) {
        this.session = session;
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
}
