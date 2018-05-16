package com.zaleski.rafal.nameshaker.features.sharefacebook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;
import com.zaleski.rafal.nameshaker.App;
import com.zaleski.rafal.nameshaker.R;
import com.zaleski.rafal.nameshaker.di.components.ActivityComponent;
import com.zaleski.rafal.nameshaker.di.components.DaggerActivityComponent;
import com.zaleski.rafal.nameshaker.util.BitmapUtil;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FacebookShareActivity extends AppCompatActivity implements FacebookShareView, TextWatcher {

    private static final int CAMERA_RESULT = 1;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 722;
    private static final int MY_PERMISSIONS_WRITE = 788;

    @BindView(R.id.edit_text_name)
    public EditText name;

    @BindView(R.id.new_name_desc)
    public TextView textView;

    @BindView(R.id.photo)
    public ImageView photo;

    @Inject
    public FacebookSharePresenter presenter;

    public ActivityComponent getComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(((App) getApplication()).getComponent())
                .build();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_facebook);
        ButterKnife.bind(this);
        getComponent().inject(this);
        presenter.onAttachView(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Udostępnij");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        name.addTextChangedListener(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {
            File out = new File(getFilesDir(), "newImage.jpg");
            if (!out.exists()) {
                showError("Wystąpił błąd podczas wykonywania zdjęcia");
                return;
            }
            Bitmap scaledPhoto = BitmapUtil.decodeWithScaling(out.getAbsolutePath());
            presenter.takingPhotoSuccess(scaledPhoto);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        presenter.onTextChanged(s);
    }

    @Override
    public void setName(String name) {
        textView.setText(String.format(getString(R.string.format_choosen_name), name));
    }

    @OnClick(R.id.button_take_picture)
    public void onTakePicture() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE);
            }
        } else {
            dispatchTakePictureIntent();
        }

    }

    private void dispatchTakePictureIntent() {
        PackageManager pm = getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileContentProvider.CONTENT_URI);
            intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            startActivityForResult(intent, CAMERA_RESULT);
        } else {
            showError("Kamera jest niedostępna");
        }
    }

    private Bitmap photoBitmap;

    @Override
    public void setPhoto(Bitmap photo) {
        photoBitmap = photo;
        this.photo.setImageBitmap(photo);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(findViewById(R.id.share_parent), error, Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_share)
    @Override
    public void onShare() {
        Log.e("log", name.getText().toString().length() + "");
        if (name.getText() != null && name.getText().toString().length() != 0 && photoBitmap != null) {
            photoBitmap = BitmapUtil.setTextOnBitmap(photoBitmap, "Wybraliśmy imię dla dziecka: " + name.getText().toString().trim() + "!");
            ShareDialog shareDialog = new ShareDialog(this);
            ShareContent shareContent = new ShareMediaContent.Builder()
                    .addMedium(new SharePhoto.Builder().setBitmap(photoBitmap).build())
                    .build();
            shareDialog.show(shareContent);
        } else {
            showError("Musisz uzupełnić wszystkie dane");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
