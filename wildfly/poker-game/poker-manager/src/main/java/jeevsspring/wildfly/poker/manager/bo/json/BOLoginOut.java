package jeevsspring.wildfly.poker.manager.bo.json;

import jeevsspring.wildfly.poker.manager.util.JsonString;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * @author Marco Romagnolo
 */
public class BOLoginOut extends BOSessionOut {

    private String playerId;

    private String nickname;

    private long balance;

    private String firstName;

    private String lastName;

    private String email;

    private String age;

    private String sex;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "BOLoginOut{" +
                "playerId='" + playerId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", balance=" + balance +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                "} " + super.toString();
    }
}
