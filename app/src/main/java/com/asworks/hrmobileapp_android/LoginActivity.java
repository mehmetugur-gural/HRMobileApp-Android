package com.asworks.hrmobileapp_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.asworks.hrmobileapp_android.model.SessionManager;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;


public class LoginActivity extends AppCompatActivity {

    private SessionManager currentSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentSession = new SessionManager(getApplicationContext());
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Giriş");

        ImageButton btnLogin = (ImageButton)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(BtnLoginClick);

        ImageButton btnRegister = (ImageButton)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(BtnRegisterClick);

        ImageButton btnLostPassword = (ImageButton)findViewById(R.id.btnLostPassword);
        btnLostPassword.setOnClickListener(BtnLostPasswordClick);

    }

    private View.OnClickListener BtnRegisterClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(register);
        }
    };

    private View.OnClickListener BtnLostPasswordClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent lostPassword = new Intent(LoginActivity.this, LostPasswordActivity.class);
            startActivity(lostPassword);
        }
    };

    private View.OnClickListener BtnLoginClick = new View.OnClickListener() {
        public void onClick(View v) {

            EditText txtLoginUser = (EditText)findViewById(R.id.txtLoginUserName);
            EditText txtLoginPass = (EditText)findViewById(R.id.txtLoginPass);

            if (!TextUtils.isEmpty(txtLoginUser.getText().toString().trim()) || !TextUtils.isEmpty(txtLoginPass.getText().toString().trim()))
            {

                IApiService loginService = IApiService.retrofit.create(IApiService.class);
                Call<ResponseBase<Employee>> employeeRequest = loginService.login(txtLoginUser.getText().toString(), txtLoginPass.getText().toString());

                employeeRequest.enqueue(new Callback<ResponseBase<Employee>>() {
                    @Override
                    public void onResponse(Call<ResponseBase<Employee>> call, Response<ResponseBase<Employee>> response) {
                        ResponseBase<Employee> currentEmployee = response.body();

                        if (currentEmployee.data != null)
                        {
                            currentSession.SetCurrentUser(currentEmployee.data);
                            Intent mainPage = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainPage);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Hatalı Kullanıcı Adı Veya Şifre Girdiniz!", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "Lütfen Tüm Bilgilerinizi Eksiksiz Giriniz!", Toast.LENGTH_SHORT).show();
            }
        }
    };
}