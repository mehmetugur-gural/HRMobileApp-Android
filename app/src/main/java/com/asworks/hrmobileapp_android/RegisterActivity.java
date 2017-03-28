package com.asworks.hrmobileapp_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.ResponseBase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Yeni Kayıt");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton btnRegister = (ImageButton)findViewById(R.id.btnCompleteRegister);
        btnRegister.setOnClickListener(btnRegisterClick);

    }

    private View.OnClickListener btnRegisterClick = new View.OnClickListener() {
        public void onClick(View v) {

            EditText firstName = (EditText)findViewById(R.id.txtRegisterName);
            EditText lastName = (EditText)findViewById(R.id.txtRegisterSurname);
            EditText mailAddress = (EditText)findViewById(R.id.txtRegisterMail);
            EditText password = (EditText)findViewById(R.id.txtRegisterPassword);

            if (validateRegisterForm())
            {
                IApiService registerService = IApiService.retrofit.create(IApiService.class);

                Employee registerEmployee = new Employee();
                registerEmployee.setFirstName(firstName.getText().toString());
                registerEmployee.setLastName(lastName.getText().toString());
                registerEmployee.setMailAddress(mailAddress.getText().toString());
                registerEmployee.setPassword(password.getText().toString());

                Call<ResponseBase<Employee>> registerRequest = registerService.register(registerEmployee);

                registerRequest.enqueue(new Callback<ResponseBase<Employee>>() {
                    @Override
                    public void onResponse(Call<ResponseBase<Employee>> call, Response<ResponseBase<Employee>> response) {
                        ResponseBase<Employee> registerResponse = response.body();

                        if (registerResponse.success)
                        {
                            Intent mainPage = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(mainPage);
                            Toast.makeText(getApplicationContext(), "Üyeliğiniz Yöneticilerimiz Tarafından Onaylandıktan Sonra Aktif Olacaktır.", Toast.LENGTH_LONG).show();
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
        }
    };

    public boolean validateRegisterForm()
    {
        EditText firstName = (EditText)findViewById(R.id.txtRegisterName);
        EditText lastName = (EditText)findViewById(R.id.txtRegisterSurname);
        EditText mailAddress = (EditText)findViewById(R.id.txtRegisterMail);
        EditText password = (EditText)findViewById(R.id.txtRegisterPassword);
        EditText password2 = (EditText)findViewById(R.id.txtRegisterPassword2);

        boolean result = true;

        if(firstName.getText().toString().length() == 0 )
        {firstName.setError( "Lütfen Adınızı Giriniz!" );
         result = false;}

        if(lastName.getText().toString().length() == 0 )
        {lastName.setError( "Lütfen Soyadınızı Giriniz!" );
            result = false;}

        if(mailAddress.getText().toString().length() == 0 )
        {mailAddress.setError( "Lütfen Mail Adresinizi Giriniz!" );
            result = false;}

        if(password.getText().toString().length() == 0 )
        {password.setError( "Lütfen Şifrenizi Giriniz!" );
            result = false;}

        if(password2.getText().toString().length() == 0 )
        {password2.setError( "Lütfen Şifrenizi Tekrar Giriniz!" );
            result = false;}

        if (!password.getText().toString().equals(password2.getText().toString()))
        {
            password2.setError( "Lütfen Şifrenizi Aynı Olacak Şekilde Giriniz!" );
            result = false;
        }

        return  result;
    }

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