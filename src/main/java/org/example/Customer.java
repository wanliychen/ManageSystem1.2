package org.example;

public class Customer {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String registrationDate; 
    private String userLevel;

    public Customer(){};
    
    public Customer(String username, String password, String email, String phone, String registrationDate, String userLevel) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.registrationDate = registrationDate;
        this.userLevel = userLevel;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", userLevel='" + userLevel + '\'' +
                '}';
    }
}
