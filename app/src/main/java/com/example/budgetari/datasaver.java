package com.example.budgetari;

public class datasaver {
    String userphone, userEmail;

    public datasaver(String contact, String mail) {
        this.userphone = contact;
        this.userEmail = mail;
}  public String getContact() {
        return userphone;
    }

    public void setContact(String contact) {
        this.userphone = contact;
    }

    public String getMail() {
        return userEmail;
    }

    public void setMail(String mail) {
        this.userEmail = mail;
    }

}
