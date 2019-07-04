package nikitaverma.example.com.spotify.helpers.api;

import nikitaverma.example.com.spotify.model.search_api.Search;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("search")
    Call<Search> accessToken(@Header("Authorization") String token, @Query("q") String q, @Query("type") String type);


}
