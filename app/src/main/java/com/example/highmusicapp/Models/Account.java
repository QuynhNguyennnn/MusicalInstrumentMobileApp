package com.example.highmusicapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Account {
    public Account() {}
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "PeopleID")
    private int PeopleID;

    @ColumnInfo(name = "Role")
    private int Role;

    @ColumnInfo(name = "Email")
    private String Email;

    @ColumnInfo(name = "Username")
    private String Username;

    @ColumnInfo(name = "Password")
    private String Password;

    @ColumnInfo(name = "Status")
    private Boolean Status;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPeopleID() {
        return PeopleID;
    }

    public void setPeopleID(int peopleID) {
        PeopleID = peopleID;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
