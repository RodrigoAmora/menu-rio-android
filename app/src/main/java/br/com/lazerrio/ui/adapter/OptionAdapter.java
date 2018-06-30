package br.com.lazerrio.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.lazerrio.R;
import br.com.lazerrio.model.Option;
import br.com.lazerrio.ui.listener.OnItemClickListener;
import br.com.lazerrio.ui.viewholder.OptionViewHolder;
import butterknife.Unbinder;

public class OptionAdapter extends RecyclerView.Adapter<OptionViewHolder> {

    private Activity context;
    private List<Option> optionList;

    private Unbinder unbinder;

    private OnItemClickListener onItemClickListener;

    public OptionAdapter(Activity context, List<Option> optionList) {
        this.context = context;
        this.optionList = optionList;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_option, parent, false);
        OptionViewHolder holder = new OptionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, final int position) {
        holder.setValues(optionList.get(position));
        holder.getViewDetails().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(optionList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
