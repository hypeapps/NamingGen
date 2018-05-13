package com.zaleski.rafal.nameshaker.features.sendinfo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zaleski.rafal.nameshaker.model.InfoName;
import com.zaleski.rafal.nameshaker.presenter.Presenter;

import javax.inject.Inject;

public class SendInfoPresenter extends Presenter<SendInfoView> {

    private DatabaseReference database;

    @Inject
    public SendInfoPresenter() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onAttachView(SendInfoView view) {
        super.onAttachView(view);
        this.view.initSpinners();
    }

    @Override
    protected void onDetachView() {
        super.onDetachView();
    }

    public void onSubmitNameInfo(InfoName infoName) {
        if (infoName.getName() == null || infoName.getName().length() == 0) {
            this.view.showError("Wpisz poprawnie imię");
            return;
        } else if (infoName.getVoivodeship() == null) {
            this.view.showError("Wybierz województwo");
            return;
        } else if (infoName.getYear() == null) {
            this.view.showError("Wybierz rok urodzenia");
            return;
        }
        saveToDatabase(infoName);
    }

    private void saveToDatabase(InfoName infoName) {
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SendInfoPresenter.this.view.showSuccessfulSnackbar();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        database.child("names").child("id" + infoName.hashCode()).setValue(infoName);
    }
}
