package com.zaleski.rafal.nameshaker.features.randomname;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jpardogo.android.googleprogressbar.library.GoogleMusicDicesDrawable;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.zaleski.rafal.nameshaker.App;
import com.zaleski.rafal.nameshaker.R;
import com.zaleski.rafal.nameshaker.di.components.DaggerFragmentComponent;
import com.zaleski.rafal.nameshaker.di.components.FragmentComponent;
import com.zaleski.rafal.nameshaker.model.NameFilter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RandomNameFragment extends Fragment implements RandomNameView, SensorEventListener {

    @Inject
    public RandomNamePresenter presenter;

    private NameFilter submitNameFilter = new NameFilter(false, true);

    private AlertDialog alertDialog;

    @BindView(R.id.gender)
    public TextView gender;

    @BindView(R.id.randomize_animation)
    public GoogleProgressBar googleProgressBar;

    @BindView(R.id.random_name)
    public TextView randomName;

    @BindView(R.id.card_view_name)
    public CardView cardView;

    @BindView(R.id.cube_icon)
    public ImageView cubeIcon;

    @BindView(R.id.button_randomize)
    public Button randomizeButton;

    private SensorManager sensorManager;

    private Sensor accelerometer;

    private boolean isRolling;

    public FragmentComponent getComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(((App) getActivity().getApplication()).getComponent())
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_random_name, container, false);
        ButterKnife.bind(this, view);
        getComponent().inject(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onAttachView(this);
        Drawable drawable = new GoogleMusicDicesDrawable.Builder().build();
        googleProgressBar.setIndeterminateDrawable(drawable);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
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

    @OnClick(R.id.filter_icon)
    public void onFilterIconClick() {
        presenter.onFilterButtonClick(submitNameFilter);
    }

    @Override
    public void invalidateFilterText(int which) {
        gender.setText(getResources().getStringArray(R.array.gender)[which]);
    }

    @Override
    public void setRandomName(String name) {
        randomName.setText(name);
        YoYo.with(Techniques.ZoomIn)
                .onStart(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        cardView.setVisibility(View.VISIBLE);
                    }
                })
                .playOn(cardView);
    }

    @Override
    public void startCubeAnimation() {
        googleProgressBar.setVisibility(View.VISIBLE);
        cubeIcon.setVisibility(View.INVISIBLE);
        randomizeButton.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.INVISIBLE);
        isRolling = true;
    }

    @Override
    public void stopCubeAnimation() {
        randomizeButton.setVisibility(View.VISIBLE);
        googleProgressBar.setVisibility(View.INVISIBLE);
        cubeIcon.setVisibility(View.VISIBLE);
        isRolling = false;
    }

    private long lastUpdate = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        if (gForce > 2.7f) {
            final long now = System.currentTimeMillis();
            // ignore shake events too close to each other (500ms)
            if (lastUpdate + 500 > now) {
                return;
            }
            if (!isRolling) {
                presenter.randomizeName(submitNameFilter);
            }
            lastUpdate = now;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @OnClick(R.id.button_randomize)
    public void onRandomizeButtonClick() {
        presenter.randomizeName(submitNameFilter);
    }
}
