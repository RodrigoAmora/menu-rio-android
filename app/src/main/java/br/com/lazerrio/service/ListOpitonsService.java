package br.com.lazerrio.service;

import java.util.List;

import br.com.lazerrio.model.Option;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ListOpitonsService {

    @GET("praias.php")
    public Call<List<Option>> listAllBeahes();

    @GET("leisure.php")
    public Call<List<Option>> listAllLeisures();

    @GET("hotel.php")
    public Call<List<Option>> listAllHotels();

    @GET("movie.php")
    public Call<List<Option>> listAllMovie();

    @GET("sport.php")
    public Call<List<Option>> listAllSports();

    @GET("shopping.php")
    public Call<List<Option>> listAllShoppings();

    @GET("teather.php")
    public Call<List<Option>> listAllTeathers();
    
}
