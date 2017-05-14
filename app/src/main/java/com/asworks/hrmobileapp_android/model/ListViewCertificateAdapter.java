package com.asworks.hrmobileapp_android.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.asworks.hrmobileapp_android.R;

import java.util.List;

public class ListViewCertificateAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<EmployeeCertificateAndLanguage> certificateList;

    public ListViewCertificateAdapter(Activity activity, List<EmployeeCertificateAndLanguage> certificate) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        certificateList = certificate;
    }

    @Override
    public int getCount() {
        return certificateList.size();
    }

    @Override
    public EmployeeCertificateAndLanguage getItem(int position) {
        return certificateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.settings_listview, null);
        TextView title = (TextView) satirView.findViewById(R.id.lblListViewTitle);
        TextView description = (TextView) satirView.findViewById(R.id.lblListViewDescription);
        TextView alternateDescription = (TextView) satirView.findViewById(R.id.lblListViewAlternateDescription);

        EmployeeCertificateAndLanguage certificate = certificateList.get(position);

        title.setText(certificate.getTitle().toString());
        description.setText(certificate.getOrganizationName().toString());
        alternateDescription.setText("");
        return satirView;
    }
}