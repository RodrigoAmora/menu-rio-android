package br.com.lazerrio.callback;

import java.util.List;

import br.com.lazerrio.delegate.Delegate;
import br.com.lazerrio.model.Option;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOptionsCallback implements Callback<List<Option>> {

    private Delegate delegate;
    private List<Option> options;

    public ListOptionsCallback(Delegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onResponse(Call<List<Option>> call, Response<List<Option>> response) {
        if (response.isSuccessful()) {
            this.options = response.body();
            this.delegate.success();
        } else {
            this.delegate.error();
        }
    }

    @Override
    public void onFailure(Call<List<Option>> call, Throwable t) {
        this.delegate.error();
    }

    public List<Option> getOptions() {
        return options;
    }

}
