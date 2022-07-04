package com.graduation.deliveryboot.Models;

public class SignUpModel {
    private String Name;
    private String Password;
    private String PhoneNumber;
    private String Email;
    private String ID;
    private String Wallet;
    private boolean Person;

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public boolean getbool() {
        return Person;
    }
    public String getID() {
        return ID;
    }
    public String getwallet() {
        return Wallet;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
    public void setWallet(String wallet) {
        Wallet = wallet;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public void setbool(boolean person) {
        Person = person;
    }
}
