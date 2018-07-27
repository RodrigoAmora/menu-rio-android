package br.com.lazerrio.callback;

import java.util.List;

import br.com.lazerrio.delegate.OptionDelegate;
import br.com.lazerrio.model.Option;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOptionsCallback implements Callback<List<Option>> {

    private OptionDelegate optionDelegate;

    public ListOptionsCallback(OptionDelegate optionDelegate) {
        this.optionDelegate = optionDelegate;
    }

    @Override
    public void onResponse(Call<List<Option>> call, Response<List<Option>> response) {
        if (response.isSuccessful()) {
            List<Option> result = response.body();
            if (result != null) {
                this.optionDelegate.success(result);
            } else {
                this.optionDelegate.error();
            }
        } else {
            this.optionDelegate.error();
        }
    }

    @Override
    public void onFailure(Call<List<Option>> call, Throwable t) {
        this.optionDelegate.error();
    }

}
