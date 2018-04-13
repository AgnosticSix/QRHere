package com.upc.agnosticsix.qrhere;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import adapters.EventsRecyclerAdapter;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import model.Events;
import sql.DatabaseHelper;

public class EventsListActivity extends AppCompatActivity{

    private AppCompatActivity activity = EventsListActivity.this;
    private AppCompatTextView textViewNombre;
    private RecyclerView recyclerViewEvents;
    private List<Events> eventsList;
    private EventsRecyclerAdapter eventsRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private ZXingScannerView mScannerView;
    private String test;
    private Events events;
    private String tag;
    public int postId;
    //private EventsRecyclerAdapter.OnItemClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();
    }

    private void initViews() {
        textViewNombre = (AppCompatTextView) findViewById(R.id.textViewNombre);
        recyclerViewEvents = (RecyclerView) findViewById(R.id.recyclerViewEvents);
    }

    private void initObjects() {
        eventsList = new ArrayList<>();
        //eventsRecyclerAdapter = new EventsRecyclerAdapter(eventsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewEvents.setLayoutManager(mLayoutManager);
        recyclerViewEvents.setItemAnimator(new DefaultItemAnimator());
        recyclerViewEvents.setHasFixedSize(true);
        eventsRecyclerAdapter = new EventsRecyclerAdapter(getApplicationContext(), eventsList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                //Log.d(tag,"clicked position: " + eventsList.get(i).getId());
                postId = eventsList.get(i).getId();
                Intent intent = new Intent(activity, StudentsListActivity.class);
                intent.putExtra("idevento", postId);
                startActivity(intent);
            }
        });

        recyclerViewEvents.setAdapter(eventsRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);
        //events = new Events();

        //String nameFromIntent = getIntent().getStringExtra("event_name");
        //textViewNombre.setText(nameFromIntent);

        getDataFromSQLite();
    }



    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                eventsList.clear();
                eventsList.addAll(databaseHelper.getAllEvents());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                eventsRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
