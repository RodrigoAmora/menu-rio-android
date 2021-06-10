package br.com.lazerrio.service;

import java.util.List;

import br.com.lazerrio.model.Option;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ListOpitonsService {

    @GET("beach.php")
    Call<List<Option>> listAllBeaches();

    @GET("leisure.php")
    Call<List<Option>> listAllLeisure();

    @GET("hotel.php")
    Call<List<Option>> listAllHotels();

    @GET("movie.php")
    Call<List<Option>> listAllMovie();

    @GET("museum.php")
    Call<List<Option>> listAllMuseum();

    @GET("restaurant.php")
    Call<List<Option>> listAllRestaurants();

    @GET("sport.php")
    Call<List<Option>> listAllSports();

    @GET("shopping.php")
    Call<List<Option>> listAllShoppings();

    @GET("teather.php")
    Call<List<Option>> listAllTeathers();

}
