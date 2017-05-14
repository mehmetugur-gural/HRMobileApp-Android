package com.asworks.hrmobileapp_android.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.asworks.hrmobileapp_android.R;

import java.util.List;

public class ListViewJobAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<EmployeeJobExperience> jobExperienceList;

    public ListViewJobAdapter(Activity activity, List<EmployeeJobExperience> jobExperience) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        jobExperienceList = jobExperience;
    }

    @Override
    public int getCount() {
        return jobExperienceList.size();
    }

    @Override
    public EmployeeJobExperience getItem(int position) {
        return jobExperienceList.get(position);
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

        EmployeeJobExperience jobExperience = jobExperienceList.get(position);

        title.setText(jobExperience.getCompanyName());
        description.setText(jobExperience.getTitle());
        alternateDescription.setText(jobExperience.getWorkYear() + " Yıl " + jobExperience.getWorkMonth() + " Ay");
        return satirView;
    }
}
