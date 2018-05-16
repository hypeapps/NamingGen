package com.zaleski.rafal.nameshaker.features.statisticdata;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zaleski.rafal.nameshaker.R;
import com.zaleski.rafal.nameshaker.model.Name;

public class StatisticDataRecyclerAdapter extends PagedListAdapter<Name, StatisticDataViewHolder> {

    private final LayoutInflater layoutInflater;

    public StatisticDataRecyclerAdapter(Context context) {
        super(Name.DIFF_CALLBACK);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StatisticDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_stats_list, parent, false);
        return new StatisticDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticDataViewHolder holder, int position) {
        Name name = getItem(position);
        holder.bind(name);
    }

}
