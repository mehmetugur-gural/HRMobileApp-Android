package com.asworks.hrmobileapp_android;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.ResponseBase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_layout);
        SetCustomTitle(getString(R.string.login_title));

        ImageButton btnLogin = (ImageButton)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(BtnLoginClick);

    }

    private View.OnClickListener BtnLoginClick = new View.OnClickListener() {
        public void onClick(View v) {

            EditText txtLoginUser = (EditText)findViewById(R.id.txtLoginUserName);
            EditText txtLoginPass = (EditText)findViewById(R.id.txtLoginPass);

            if (!txtLoginUser.getText().equals("") && !txtLoginPass.getText().equals("") )
            {

                IApiService loginService = IApiService.retrofit.create(IApiService.class);
                Call<ResponseBase<Employee>> employeeRequest = loginService.login(txtLoginUser.getText().toString(), txtLoginPass.getText().toString());

                employeeRequest.enqueue(new Callback<ResponseBase<Employee>>() {
                    @Override
                    public void onResponse(Call<ResponseBase<Employee>> call, Response<ResponseBase<Employee>> response) {
                        ResponseBase<Employee> currentEmployee = response.body();

                        if (currentEmployee.data != null)
                        {
                            Intent mainPage = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainPage);
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

    void SetCustomTitle(String title)
    {
        TextView textViewTitle = (TextView) findViewById(R.id.title_text);
        textViewTitle.setText(title);
    }
}