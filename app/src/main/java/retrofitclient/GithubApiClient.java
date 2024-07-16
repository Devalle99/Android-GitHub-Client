package retrofitclient;

import java.util.List;

import models.Repository;
import models.RepositoryRequest;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GithubApiClient {
    @GET("/user/repos")
    Call<List<Repository>> getRepos(
        @Header("accept") String contentType,
        @Header("authorization") String authorization,
        @Header("X-GitHub-Api-Version") String apiVersion
    );

    @POST("/user/repos")
    Call<Repository> createRepo(
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion,
            @Body RepositoryRequest repo
    );

    @DELETE("/repos/devalle99/{repo}")
    Call<Void> deleteRepo(
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion,
            @Path("repo") String repo
    );

    @PATCH("/repos/devalle99/{repo}")
    Call<Void> updateRepo(
            @Header("accept") String contentType,
            @Header("authorization") String authorization,
            @Header("X-GitHub-Api-Version") String apiVersion,
            @Path("repo") String repoName,
            @Body RepositoryRequest repo
    );

    public static GithubApiClient getGithubApiClient() {
        Retrofit retrofit = RetrofitClient.getClient();
        return retrofit.create(GithubApiClient.class);
    }

    public static String getUser() { return RetrofitClient.getUser(); }
    public static String getToken() { return RetrofitClient.getToken(); }
    public static String getApiVersion() { return RetrofitClient.getApiVersion(); }
    public static String getContentType() { return RetrofitClient.getContentType(); }
}
