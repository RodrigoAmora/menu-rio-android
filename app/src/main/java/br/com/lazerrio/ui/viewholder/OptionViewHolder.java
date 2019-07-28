package br.com.lazerrio.ui.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.lazerrio.R;
import br.com.lazerrio.model.Option;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OptionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image)
    CircleImageView imageView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.view_details)
    TextView viewDetails;

    public OptionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setValues(Option option) {
        if (option.getPhoto() == null || option.getPhoto().isEmpty()) {
            Picasso.get().load(R.drawable.no_photo).placeholder(R.mipmap.ic_launcher_round).into(imageView);
        } else {
            Picasso.get().load(option.getPhoto()).placeholder(R.mipmap.ic_launcher_round).into(imageView);
        }
        name.setText(option.getName());
    }

    public TextView getViewDetails() {
        return viewDetails;
    }

}
