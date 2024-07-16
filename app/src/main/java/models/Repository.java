package models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

import retrofitclient.GithubApiClient;

public class Repository implements Serializable {
    private long id;
    @SerializedName("name")
    private String repositoryName;
    @SerializedName("language")
    private String repositoryLanguage;
    @SerializedName("owner")
    private User repositoryOwner;

    public Repository(long id, String repositoryName, String repositoryLanguage, User repositoryOwner) {
        this.id = id;
        this.repositoryName = repositoryName;
        this.repositoryLanguage = repositoryLanguage;
        this.repositoryOwner = repositoryOwner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getRepositoryLanguage() {
        return repositoryLanguage;
    }

    public void setRepositoryLanguage(String repositoryLanguage) {
        this.repositoryLanguage = repositoryLanguage;
    }

    public User getRepositoryOwner() {
        return repositoryOwner;
    }

    public void setRepositoryOwner(User repositoryOwner) {
        this.repositoryOwner = repositoryOwner;
    }
}