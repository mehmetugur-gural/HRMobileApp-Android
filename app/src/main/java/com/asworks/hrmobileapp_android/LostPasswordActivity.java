package com.asworks.hrmobileapp_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.asworks.hrmobileapp_android.model.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostPasswordActivity extends AppCompatActivity {

    private SessionManager currentSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentSession = new SessionManager(getApplicationContext());
        setContentView(R.layout.activity_lostpassword);
        getSupportActionBar().setTitle("Şifremi Unuttum");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton btnSendLostPassword = (ImageButton)findViewById(R.id.btnSendLostPassword);
        btnSendLostPassword.setOnClickListener(BtnSendLostPasswordClick);
    }

    private View.OnClickListener BtnSendLostPasswordClick = new View.OnClickListener() {
        public void onClick(View v) {

            EditText txtLostPasswordMail = (EditText)findViewById(R.id.txtLostPasswordMail);

            if (!TextUtils.isEmpty(txtLostPasswordMail.getText().toString().trim()))
            {

                IApiService lostPasswordService = IApiService.retrofit.create(IApiService.class);
                Call<ResponseBase<Employee>> employeeRequest = lostPasswordService.lostPassword(txtLostPasswordMail.getText().toString());

                employeeRequest.enqueue(new Callback<ResponseBase<Employee>>() {
                    @Override
                    public void onResponse(Call<ResponseBase<Employee>> call, Response<ResponseBase<Employee>> response) {
                        ResponseBase<Employee> currentEmployee = response.body();

                        if (currentEmployee.success)
                        {
                            Intent mainPage = new Intent(LostPasswordActivity.this, LoginActivity.class);
                            startActivity(mainPage);
                            Toast.makeText(getApplicationContext(), currentEmployee.message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBase<Employee>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Hata Oluştu, Lütfen Tekrar Deneyiniz!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Lütfen Tüm Bilgilerinizi Eksiksiz Giriniz!", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}