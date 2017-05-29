package com.asworks.hrmobileapp_android.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.EventDetailActivity;
import com.asworks.hrmobileapp_android.R;
import com.asworks.hrmobileapp_android.SettingsActivity;
import com.asworks.hrmobileapp_android.SettingsPicturesActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.MyViewHolder> {


    private Context mContext;
    private List<String> employeePictureList;
    private Employee currentUser;
    Employee registerEmployee;
    private SessionManager currentSession;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView employeePicture;
        public ImageButton deleteEmployeePicture;

        public MyViewHolder(View view) {
            super(view);
            employeePicture = (ImageView) view.findViewById(R.id.employeePicture);
            deleteEmployeePicture = (ImageButton) view.findViewById(R.id.deleteEmployeePicture);
        }
    }


    public PictureAdapter(Context mContext, List<String> employeePicture) {
        this.mContext = mContext;
        this.employeePictureList = employeePicture;
        currentSession = new SessionManager(mContext);
        currentUser = currentSession.GetCurrentUser();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_card, parent, false);

        ImageButton btnDeleteEmployeePicture = (ImageButton) itemView.findViewById(R.id.deleteEmployeePicture);
        btnDeleteEmployeePicture.setOnClickListener(btndeleteEmployeePictureClick);

        return new MyViewHolder(itemView);
    }

    private View.OnClickListener btndeleteEmployeePictureClick = new View.OnClickListener() {
        public void onClick(View v) {

            final View itemView = v;
            registerEmployee = currentUser;

            new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Resmi Sil")
                    .setMessage("Resmi Silmek İstediğinizden Emin misiniz?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            if (itemView.getTag() != null)
                            {
                                int picturePosition = Integer.parseInt(itemView.getTag().toString());
                                employeePictureList.remove(picturePosition);
                                registerEmployee.setPicture(TextUtils.join(",", employeePictureList));

                                final IApiService employeeService = IApiService.retrofit.create(IApiService.class);
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
                                            Log.e("resimler", currentSession.GetCurrentUser().getPicture());
                                            Intent mainPage = new Intent(itemView.getContext(), SettingsActivity.class);
                                            itemView.getContext().startActivity(mainPage);
                                            Toast.makeText(itemView.getContext(), "Fotoğraf Profilinizden Silindi.", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(itemView.getContext(), registerResponse.message, Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBase<Employee>> call, Throwable t) {
                                        Toast.makeText(itemView.getContext(), "Hata Oluştu, Lütfen Tekrar Deneyiniz!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }})
                    .setNegativeButton("Hayır", null).show();
        }
    };

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String employeePicture = employeePictureList.get(position);
        holder.deleteEmployeePicture.setTag(position);
        Glide.with(mContext).load(IApiService.apiUrl + employeePicture).into(holder.employeePicture);
    }

    @Override
    public int getItemCount() {
        return employeePictureList.size();
    }
}