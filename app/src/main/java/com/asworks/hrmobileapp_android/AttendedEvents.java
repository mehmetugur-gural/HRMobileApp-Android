package com.asworks.hrmobileapp_android;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.asworks.hrmobileapp_android.model.Employee;
import com.asworks.hrmobileapp_android.model.Event;
import com.asworks.hrmobileapp_android.model.EventAdapter;
import com.asworks.hrmobileapp_android.model.IApiService;
import com.asworks.hrmobileapp_android.model.ResponseBase;
import com.asworks.hrmobileapp_android.model.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendedEvents extends Fragment {

    private RecyclerView recyclerView = null;
    private EventAdapter adapter;
    private List<Event> eventList;
    private SessionManager currentSession;

    public AttendedEvents () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_attended_events, container, false);
        currentSession = new SessionManager(getContext());
        Employee currentUser = currentSession.GetCurrentUser();

        IApiService eventService = IApiService.retrofit.create(IApiService.class);
        Call<ResponseBase<List<Event>>> eventRequest = eventService.attendedEventList(currentUser.getID().toString());

        eventRequest.enqueue(new Callback<ResponseBase<List<Event>>>() {
            @Override
            public void onResponse(Call<ResponseBase<List<Event>>> call, Response<ResponseBase<List<Event>>> response) {
                ResponseBase<List<Event>> eventListResponse = response.body();

                if (eventListResponse.data != null)
                {
                    recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
                    eventList = eventListResponse.data;
                    adapter = new EventAdapter(getContext(), eventList);

                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);

                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.addItemDecoration(new AttendedEvents.GridSpacingItemDecoration(2, dpToPx(10), true));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseBase<List<Event>>> call, Throwable t) {
                Toast.makeText(getContext(), "Hata Oluştu, Lütfen Tekrar Deneyiniz!", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}