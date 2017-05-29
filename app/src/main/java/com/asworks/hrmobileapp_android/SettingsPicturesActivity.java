package com.asworks.hrmobileapp_android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.EmployeeImage;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.PictureAdapter;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.asworks.hrmobileapp_android.model.SessionManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mehmetugurgural on 20/05/2017.
 */

public class SettingsPicturesActivity extends AppCompatActivity {

    private Employee currentUser;
    private SessionManager currentSession;
    private RecyclerView recyclerView = null;
    private PictureAdapter adapter;
    private List<String> pictureList;
    private String uploadPicture;
    private String uploadPictureType;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_pictures);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Fotoğraflarım");

        currentSession = new SessionManager(getApplicationContext());
        currentUser = currentSession.GetCurrentUser();

        recyclerView = (RecyclerView) findViewById(R.id.picture_recycler_view);

        if (currentUser.getPicture() != null)
        {
            List<String> currentUserPictures = new ArrayList<String>();
            currentUserPictures.addAll(Arrays.asList(currentUser.getPicture().split(",")));

            if (currentUserPictures.size() > 0)
            {
                pictureList = currentUserPictures;
            }

            adapter = new PictureAdapter(getApplicationContext(), pictureList);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }

        final ImageButton btnAddNewPicture = (ImageButton) findViewById(R.id.btnAddNewPicture);
        btnAddNewPicture.setOnClickListener(btnAddNewPictureClick);

    }


    private View.OnClickListener btnAddNewPictureClick = new View.OnClickListener() {
        public void onClick(View v) {

            if (ContextCompat.checkSelfPermission(SettingsPicturesActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SettingsPicturesActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            else
            {
                Intent galleryItent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryItent.setType("image/*");
                startActivityForResult(galleryItent, 1);
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent galleryItent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryItent.setType("image/*");
                    startActivityForResult(galleryItent, 1);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();

            try {

                uploadPictureType = getContentResolver().getType(selectedImage);
                uploadPicture = getStringImage(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage), uploadPictureType);

                final IApiService employeeService = IApiService.retrofit.create(IApiService.class);

                final Employee registerEmployee = currentUser;

                EmployeeImage employeeImage = new EmployeeImage();
                employeeImage.setImageUrl(uploadPicture);
                employeeImage.setImageType(uploadPictureType);

                Call<ResponseBase<String>> employeeRequest = employeeService.saveImage(employeeImage);

                employeeRequest.enqueue(new Callback<ResponseBase<String>>() {
                    @Override
                    public void onResponse(Call<ResponseBase<String>> call, Response<ResponseBase<String>> response) {

                        if (response.body().success)
                        {
                            registerEmployee.setPicture(registerEmployee.getPicture() + "," + response.body().data);
                            registerEmployee.setPassword("");
                            Call<ResponseBase<Employee>> registerRequest = employeeService.register(registerEmployee);
                            registerEmployee.setPassword(currentUser.getPassword());

                            registerRequest.enqueue(new Callback<ResponseBase<Employee>>() {
                                @Override
                                public void onResponse(Call<ResponseBase<Employee>> call, Response<ResponseBase<Employee>> response) {
                                    ResponseBase<Employee> registerResponse = response.body();
                                    if (registerResponse.success)
                                    {
                                        currentSession.SetCurrentUser(registerEmployee);
                                        Intent mainPage = new Intent(SettingsPicturesActivity.this, SettingsActivity.class);
                                        startActivity(mainPage);
                                        Toast.makeText(getApplicationContext(), "Fotoğraf Profilinize Kaydedildi.", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), registerResponse.message, Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBase<Employee>> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Hata Oluştu, Lütfen Tekrar Deneyiniz!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Hata Oluştu, Lütfen Tekrar Deneyiniz!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBase<String>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Hata Oluştu, Lütfen Tekrar Deneyiniz!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            catch (Exception ex)
            {

            }

        }
    }

    public String getStringImage(Bitmap bmp, String imageType){

        Bitmap.CompressFormat imageFormat = Bitmap.CompressFormat.JPEG;

        if (imageType.contains("png"))
        {
            imageFormat = Bitmap.CompressFormat.PNG;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(imageFormat, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
