package br.com.lazerrio.service;

import java.util.List;

import br.com.lazerrio.model.Option;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ListOpitonsService {

    @GET("sport.php")
    public Call<List<Option>> listAllSports();

    @GET("shopping.php")
    public Call<List<Option>> listAllShoppings();

}
