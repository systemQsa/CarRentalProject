package com.myproject.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class User implements Comparable<User>, Serializable {
    private static final long serialUID = 3423344345465655654L;
    private long userId;
    private String name;
    private String surname;
    private String serialPassportNumber;
    private String login;
    private String userRole;
    private BigDecimal balance;
    private String isBanned;
    private String password;
    private String salt;
    private String phone;
    private int role;
    private LocalDateTime registerDate;

    public static class UserBuilder {
        private final User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder setUserId(long userId) {
            user.userId = userId;
            return this;
        }

        public UserBuilder setFirstName(String name) {
            user.name = name;
            return this;
        }

        public UserBuilder setLastName(String surname) {
            user.surname = surname;
            return this;
        }

        public UserBuilder setSerialPassportNumber(String serialPassportNumber) {
            user.serialPassportNumber = serialPassportNumber;
            return this;
        }

        public UserBuilder setHashPass(String hashPass) {
            user.password = hashPass;
            return this;
        }

        public UserBuilder setLogin(String login) {
            user.login = login;
            return this;
        }
        public UserBuilder setUserRole(String userRole){
            user.userRole = userRole;
            return this;
        }

        public UserBuilder setRole(int role){
            user.role = role;
            return this;
        }

        public UserBuilder setRegisterDate(LocalDateTime registerDate) {
            user.registerDate = registerDate;
            return this;
        }

        public UserBuilder setBalance(BigDecimal balance) {
            user.balance = balance;
            return this;
        }

        public UserBuilder setIsBanned(String isBanned) {
            user.isBanned = isBanned;
            return this;
        }

        public UserBuilder setPhone(String phone) {
            user.phone = phone;
            return this;
        }

        public User build() {
            return user;
        }

    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSerialPassportNumber() {
        return serialPassportNumber;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUserRole() {
        return userRole;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getIsBanned() {
        return isBanned;
    }

    public String getPhone() {
        return phone;
    }

    public int getRole() {
        return role;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUserId() == user.getUserId() && Objects.equals(getIsBanned(), user.getIsBanned())
                && Objects.equals(getName(), user.getName()) && Objects.equals(getSurname(), user.getSurname())
                && Objects.equals(getSerialPassportNumber(), user.getSerialPassportNumber())
                && Objects.equals(getLogin(), user.getLogin()) && Objects.equals(getUserRole(), user.getUserRole())
                && Objects.equals(getRegisterDate(), user.getRegisterDate()) && Objects.equals(getBalance(),
                user.getBalance()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getSalt(),
                user.getSalt()) && Objects.equals(getPhone(), user.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getName(), getSurname(), getSerialPassportNumber(), getLogin(),
                getUserRole(), getRegisterDate(), getBalance(), getIsBanned(), getPassword(), getSalt(), getPhone());
    }

    @Override
    public int compareTo(User anotherUser) {
        return login.compareTo(anotherUser.login);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + name + '\'' +
                ", LastName='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password=" + password +
                ", userRole=" + userRole +
                ", registerDate=" + registerDate +
                ", balance=" + balance +
                ", isBanned=" + isBanned +
                ", phone='" + phone + '\'' +
                '}';
    }
}

