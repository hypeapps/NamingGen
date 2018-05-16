package com.zaleski.rafal.nameshaker.features.namelist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zaleski.rafal.nameshaker.R;
import com.zaleski.rafal.nameshaker.model.Name;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NameListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_list_name)
    public TextView name;


    public NameListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Name name) {
        String capitalizeSentence = name.getName().substring(0, 1) + name.getName().substring(1).toLowerCase();
        this.name.setText(capitalizeSentence);
    }
}
