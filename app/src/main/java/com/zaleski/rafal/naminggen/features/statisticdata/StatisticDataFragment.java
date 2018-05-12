package com.zaleski.rafal.naminggen.features.statisticdata;

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

import com.zaleski.rafal.naminggen.App;
import com.zaleski.rafal.naminggen.R;
import com.zaleski.rafal.naminggen.dataprovider.datasource.NameFilter;
import com.zaleski.rafal.naminggen.di.components.DaggerFragmentComponent;
import com.zaleski.rafal.naminggen.di.components.FragmentComponent;
import com.zaleski.rafal.naminggen.model.Name;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatisticDataFragment extends Fragment implements StatisticDataView {

    @Inject
    public StatisticDataPresenter presenter;

    @BindView(R.id.name_list)
    public RecyclerView recyclerView;

    @BindView(R.id.sex)
    public TextView sexText;

    @BindView(R.id.voivodeship)
    public TextView voivodeship;

    private StatisticDataRecyclerAdapter recyclerAdapter;

    private AlertDialog chooseGenderDialog;

    private AlertDialog chooseVoivodeshipDialog;

    private NameFilter submitNameFilter = new NameFilter(true, true, "Wszystkie");

    public FragmentComponent getComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(((App) getActivity().getApplication()).getComponent())
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_statistic_data, container, false);
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
        if (chooseGenderDialog != null && chooseGenderDialog.isShowing()) {
            chooseGenderDialog.dismiss();
        }
        if (chooseVoivodeshipDialog != null && chooseVoivodeshipDialog.isShowing()) {
            chooseVoivodeshipDialog.dismiss();
        }
        super.onDestroyView();
    }

    @Override
    public void initRecyclerAdapter() {
        recyclerAdapter = new StatisticDataRecyclerAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.decorator_recycler_item));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onNextItemList(PagedList<Name> pagedList) {
        recyclerAdapter.submitList(pagedList);
    }

    @Override
    public void clearListRecycler() {
        if (recyclerAdapter.getCurrentList() != null) {
            this.recyclerAdapter = null;
            this.initRecyclerAdapter();
        }
    }

    @OnClick(R.id.filter_icon)
    public void onFilterIconClick() {
        presenter.onFilterButtonClick(submitNameFilter);
    }

    private NameFilter newFilter = new NameFilter();

    @Override
    public void openFilterGenderDialog(NameFilter nameFilter) {
        final CharSequence[] options = getResources().getStringArray(R.array.gender);
        int checkedGender = 0;
        if (nameFilter.isMale() && nameFilter.isFemale()) {
            checkedGender = 2;
        } else if (nameFilter.isFemale()) {
            checkedGender = 1;
        } else if (nameFilter.isMale()) {
            checkedGender = 0;
        }
        chooseGenderDialog = new AlertDialog.Builder(getContext())
                .setTitle("Wybierz płeć imion")
                .setIcon(R.drawable.ic_female_male)
                .setSingleChoiceItems(options, checkedGender, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            newFilter = new NameFilter(false, true);
                        } else if (which == 1) {
                            newFilter = new NameFilter(true, false);
                        } else {
                            newFilter = new NameFilter(true, true);
                        }
                    }
                })
                .setPositiveButton("Wybierz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newFilter.setVoivodeship(submitNameFilter.getVoivodeship());
                        openFilterDialogVoivodeship(newFilter);
                    }
                }).setNegativeButton("Anuluj", null)
                .create();
        chooseGenderDialog.show();
    }

    public void openFilterDialogVoivodeship(final NameFilter nameFilter) {
        final String[] options = getResources().getStringArray(R.array.voivodeship);
        int checkedItem = 0;
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(submitNameFilter.getVoivodeship())) {
                checkedItem = i;
            }
        }
        chooseVoivodeshipDialog = new AlertDialog.Builder(getContext())
                .setTitle("Wybierz województwo")
                .setIcon(R.drawable.ic_voivodeship)
                .setSingleChoiceItems(options, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nameFilter.setVoivodeship(options[which]);
                    }
                })
                .setPositiveButton("Wybierz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submitNameFilter = nameFilter;
                        presenter.onSubmitFilter(submitNameFilter);
                    }
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }

    @Override
    public void invalidateFilterText(int whichGender, String voivodeship) {
        sexText.setText(getResources().getStringArray(R.array.gender)[whichGender]);
        this.voivodeship.setText(voivodeship);
    }
}
