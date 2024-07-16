package models;

import com.google.gson.annotations.SerializedName;

public class RepositoryRequest {
    @SerializedName("name")
    private String repositoryName;
    @SerializedName("description")
    private String getRepositoryDescription;

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getGetRepositoryDescription() {
        return getRepositoryDescription;
    }

    public void setGetRepositoryDescription(String getRepositoryDescription) {
        this.getRepositoryDescription = getRepositoryDescription;
    }

    public RepositoryRequest(String repositoryName, String getRepositoryDescription) {
        this.repositoryName = repositoryName;
        this.getRepositoryDescription = getRepositoryDescription;
    }
}
