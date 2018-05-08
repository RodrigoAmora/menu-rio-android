package br.com.lazerrio.ui.adapter;

import android.app.Activity;
import android.os.Bundle;
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
import br.com.lazerrio.ui.fragment.DetailsFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private Activity context;
    private List<Option> optionList;

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
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", Double.parseDouble(optionList.get(position).getLat()));
                bundle.putDouble("lng", Double.parseDouble(optionList.get(position).getLng()));
                bundle.putString("desc", optionList.get(position).getDescription());
                bundle.putString("name", optionList.get(position).getName());
                bundle.putString("photo", optionList.get(position).getPhoto());

                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);

                context.getFragmentManager().beginTransaction().replace(R.id.conatiner, detailsFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public class OptionViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imageView;
        private TextView name, viewDetails;

        public OptionViewHolder(View itemView) {
            super(itemView);
            viewDetails = itemView.findViewById(R.id.view_details);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
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
