package org.example;

import java.sql.Date;

public class Customer {

    // 用户信息
    private String username;
    private String userpassword;
    private String useremail;
    private String phone;
    private Date registrationDate;
    private String userLevel;

    public Customer() {}

    public Customer(String username, String userpassword, String useremail, String phone, Date registrationDate, String userLevel) {
        this.username = username;
        this.userpassword = userpassword;
        this.useremail = useremail;
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

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
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
            ", userpassword='" + userpassword + '\'' +
            ", useremail='" + useremail + '\'' +
            ", phone='" + phone + '\'' +
            ", registrationDate=" + registrationDate +
            ", userLevel='" + userLevel + '\'' +
            '}';
    }

}
