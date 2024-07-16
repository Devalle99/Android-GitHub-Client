package models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("login")
    private String username;
    @SerializedName("name")
    private String fullName;
    @SerializedName("avatar_url")
    private String userImage;

    public User(String username, String fullName, String userImage) {
        this.username = username;
        this.fullName = fullName;
        this.userImage = userImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}