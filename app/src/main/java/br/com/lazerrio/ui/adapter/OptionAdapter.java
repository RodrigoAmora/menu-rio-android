package br.com.lazerrio.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.lazerrio.R;
import br.com.lazerrio.model.Option;
import br.com.lazerrio.ui.listener.OnItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

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
        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
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

    public class OptionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.description)
        CircleImageView imageView;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.view_details)
        TextView viewDetails;

        public OptionViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }

        public void setValues(Option option) {
            if (option.getPhoto() == null || option.getPhoto().isEmpty()) {
                Picasso.get().load(R.drawable.no_photo).placeholder(R.mipmap.ic_launcher_round).into(imageView);
            } else {
                Picasso.get().load(option.getPhoto()).placeholder(R.mipmap.ic_launcher_round).into(imageView);
            }

            name.setText(option.getName());
        }
    }

}
