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

public class ListViewEducationAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<EmployeeEducation> educationList;

    public ListViewEducationAdapter(Activity activity, List<EmployeeEducation> education) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        educationList = education;
    }

    @Override
    public int getCount() {
        return educationList.size();
    }

    @Override
    public EmployeeEducation getItem(int position) {
        return educationList.get(position);
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

        EmployeeEducation education = educationList.get(position);

        title.setText(education.getOrganizationName().toString());
        description.setText(education.getInstitute().toString());
        alternateDescription.setText("Derece : " + education.getDegree().toString());
        return satirView;
    }
}