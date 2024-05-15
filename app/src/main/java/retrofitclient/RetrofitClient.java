package retrofitclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.github.com/";
    private static final String USER = "Devalle99";
    private static final String TOKEN = "Bearer <token>";
    private static final String API_VERSION = "2022-11-28";
    private static final String CONTENT_TYPE = "application/vnd.github+json";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static String getUser() { return USER; }
    public static String getToken() { return TOKEN; }
    public static String getApiVersion() { return API_VERSION; }
    public static String getContentType() { return CONTENT_TYPE; }
}
