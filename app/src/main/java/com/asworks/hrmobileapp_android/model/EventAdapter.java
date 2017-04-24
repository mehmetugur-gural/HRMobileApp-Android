package com.asworks.hrmobileapp_android.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asworks.hrmobileapp_android.EventDetailActivity;
import com.asworks.hrmobileapp_android.R;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {


    private Context mContext;
    private List<Event> eventList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public EventAdapter(Context mContext, List<Event> eventList) {
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false);

        ImageView thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        TextView title = (TextView) itemView.findViewById(R.id.title);
        TextView count = (TextView) itemView.findViewById(R.id.count);

        thumbnail.setOnClickListener(thumbnailClick);
        title.setOnClickListener(titleClick);
        count.setOnClickListener(countClick);

        return new MyViewHolder(itemView);
    }

    private View.OnClickListener thumbnailClick = new View.OnClickListener() {
        public void onClick(View v) {
            navigateToDetail(v);
        }
    };

    private View.OnClickListener titleClick = new View.OnClickListener() {
        public void onClick(View v) {
            navigateToDetail(v);
        }
    };

    private View.OnClickListener countClick = new View.OnClickListener() {
        public void onClick(View v) {
            navigateToDetail(v);
        }
    };

    public void navigateToDetail(View v)
    {
        Intent eventDetail = new Intent(v.getContext(), EventDetailActivity.class);
        eventDetail.putExtra("eventID", v.getTag().toString());
        v.getContext().startActivity(eventDetail);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.title.setText(event.getName());
        holder.count.setText("Başlangıç : " + event.getBeginDate() + "\n" + "Bitiş : " + event.getEndDate());

        Glide.with(mContext).load(IApiService.apiUrl + event.getEventDocument()).into(holder.thumbnail);
        holder.thumbnail.setTag(event.getID());
        holder.title.setTag(event.getID());
        holder.count.setTag(event.getID());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}