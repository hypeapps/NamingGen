package com.zaleski.rafal.nameshaker.features.sendinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.zaleski.rafal.nameshaker.App;
import com.zaleski.rafal.nameshaker.R;
import com.zaleski.rafal.nameshaker.di.components.ActivityComponent;
import com.zaleski.rafal.nameshaker.di.components.DaggerActivityComponent;
import com.zaleski.rafal.nameshaker.model.InfoName;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendInfoActivity extends AppCompatActivity implements SendInfoView {

    @Inject
    public SendInfoPresenter presenter;

    @BindView(R.id.voivodeship_spinner)
    public Spinner voivodeshipSpinner;

    @BindView(R.id.year_spinner)
    public Spinner yearSpinner;

    @BindView(R.id.edit_text_name)
    EditText name;

    private InfoName infoName = new InfoName();

    public ActivityComponent getComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(((App) getApplication()).getComponent())
                .build();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_info);
        ButterKnife.bind(this);
        getComponent().inject(this);
        presenter.onAttachView(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Wyślij dane");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetachView();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initSpinners() {
        ArrayAdapter<CharSequence> voivodeshipAdapter = ArrayAdapter.createFromResource(this,
                R.array.voivodeship_only, android.R.layout.simple_spinner_item);
        voivodeshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        voivodeshipSpinner.setAdapter(voivodeshipAdapter);
        voivodeshipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                infoName.setVoivodeship(getResources().getStringArray(R.array.voivodeship_only)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1900; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                infoName.setYear(Integer.parseInt(years.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.button_submit_info)
    public void onSubmitInfo() {
        infoName.setName(name.getText().toString().trim());
        presenter.onSubmitNameInfo(infoName);
    }

    @Override
    public void showSuccessfulSnackbar() {
        Snackbar.make(findViewById(R.id.send_info_parent), "Wysłano dane!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String text) {
        Snackbar.make(findViewById(R.id.send_info_parent), text, Snackbar.LENGTH_SHORT).show();
    }
}
