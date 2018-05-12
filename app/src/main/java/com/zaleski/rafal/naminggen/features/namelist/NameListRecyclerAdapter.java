package com.zaleski.rafal.naminggen.features.namelist;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zaleski.rafal.naminggen.R;
import com.zaleski.rafal.naminggen.model.Name;

public class NameListRecyclerAdapter extends PagedListAdapter<Name, NameListViewHolder> {

    private final LayoutInflater layoutInflater;

    public NameListRecyclerAdapter(Context context) {
        super(Name.DIFF_CALLBACK);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NameListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_list_name, parent, false);
        return new NameListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NameListViewHolder holder, int position) {
        Name name = getItem(position);
        holder.bind(name);
    }
}
