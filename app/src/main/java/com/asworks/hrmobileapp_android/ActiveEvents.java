package com.asworks.hrmobileapp_android;

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

import com.asworks.hrmobileapp_android.model.Event;
import com.asworks.hrmobileapp_android.model.EventAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActiveEvents extends Fragment {

    private RecyclerView recyclerView = null;
    private EventAdapter adapter;
    private List<Event> eventList;

    public ActiveEvents () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_active_events, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        eventList = new ArrayList<>();
        adapter = new EventAdapter(getContext(), eventList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareEventList();
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void prepareEventList() {
        String[] covers = new String[]{
                "https://cms.aslabs.in/Dosyalar/Resimler/blog1.jpg",
                "https://cms.aslabs.in/Dosyalar/Resimler/Event-management.png",
                "https://cms.aslabs.in/Dosyalar/Resimler/maxresdefault.jpg"};

        Event a = new Event();
        a.setName("Etkinlik 1");
        a.setDescription("Bir takım çeşitli olaylar");
        a.setEventDocument(covers[0]);

        Event b = new Event();
        b.setName("Etkinlik 2");
        b.setDescription("Bir takım çeşitli olaylar");
        b.setEventDocument(covers[1]);

        Event c = new Event();
        c.setName("Etkinlik 3");
        c.setDescription("Bir takım çeşitli olaylar");
        c.setEventDocument(covers[2]);
        eventList.add(a);
        eventList.add(b);
        eventList.add(c);

        adapter.notifyDataSetChanged();
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