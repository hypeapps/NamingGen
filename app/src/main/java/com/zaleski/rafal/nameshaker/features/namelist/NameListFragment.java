package com.zaleski.rafal.nameshaker.features.namelist;

import android.arch.paging.PagedList;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zaleski.rafal.nameshaker.App;
import com.zaleski.rafal.nameshaker.R;
import com.zaleski.rafal.nameshaker.di.components.DaggerFragmentComponent;
import com.zaleski.rafal.nameshaker.di.components.FragmentComponent;
import com.zaleski.rafal.nameshaker.model.Name;
import com.zaleski.rafal.nameshaker.model.NameFilter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NameListFragment extends Fragment implements NameListView {

    @Inject
    public NameListPresenter presenter;

    @BindView(R.id.name_list)
    public RecyclerView recyclerView;

    @BindView(R.id.gender)
    public TextView gender;

    private NameListRecyclerAdapter nameListRecyclerAdapter;

    private AlertDialog alertDialog;

    private NameFilter submitNameFilter = new NameFilter(false, true);

    public FragmentComponent getComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(((App) getActivity().getApplication()).getComponent())
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_name_list, container, false);
        ButterKnife.bind(this, view);
        getComponent().inject(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onAttachView(this);
        presenter.onSubmitFilter(submitNameFilter);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetachView();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        super.onDestroyView();
    }

    @Override
    public void initRecyclerAdapter() {
        nameListRecyclerAdapter = new NameListRecyclerAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.decorator_recycler_item));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(nameListRecyclerAdapter);
    }

    @Override
    public void onNextItemList(PagedList<Name> pagedList) {
        nameListRecyclerAdapter.submitList(pagedList);
    }

    @Override
    public void clearListRecycler() {
        if (nameListRecyclerAdapter.getCurrentList() != null) {
            this.nameListRecyclerAdapter = null;
            this.initRecyclerAdapter();
        }
    }

    @OnClick(R.id.filter_icon)
    public void onFilterIconClick() {
        presenter.onFilterButtonClick(submitNameFilter);
    }

    @Override
    public void openFilterDialog(NameFilter nameFilter) {
        final CharSequence[] options = getResources().getStringArray(R.array.gender);
        int checkedSex = 0;
        if (nameFilter.isMale() && nameFilter.isFemale()) {
            checkedSex = 2;
        } else if (nameFilter.isFemale()) {
            checkedSex = 1;
        } else if (nameFilter.isMale()) {
            checkedSex = 0;
        }
        alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Wybierz płeć imion")
                .setIcon(R.drawable.ic_female_male)
                .setSingleChoiceItems(options, checkedSex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            submitNameFilter = new NameFilter(false, true);
                        } else if (which == 1) {
                            submitNameFilter = new NameFilter(true, false);
                        } else {
                            submitNameFilter = new NameFilter(true, true);
                        }
                    }
                })
                .setPositiveButton("Wybierz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onSubmitFilter(submitNameFilter);
                    }
                }).setNegativeButton("Anuluj", null)
                .create();
        alertDialog.show();
    }

    @Override
    public void invalidateFilterText(int which) {
        gender.setText(getResources().getStringArray(R.array.gender)[which]);
    }
}
