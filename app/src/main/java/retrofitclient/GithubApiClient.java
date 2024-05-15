package retrofitclient;

import java.util.List;

import models.Repository;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GithubApiClient {
    @GET("/user/repos")
    Call<List<Repository>> getRepos(
        @Header("accept") String contentType,
        @Header("authorization") String authorization,
        @Header("X-GitHub-Api-Version") String apiVersion
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
