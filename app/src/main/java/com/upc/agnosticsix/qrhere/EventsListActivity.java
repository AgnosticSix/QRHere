package com.upc.agnosticsix.qrhere;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class EventsListActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private AppCompatActivity activity = EventsListActivity.this;
    private AppCompatTextView textViewNombre;
    private RecyclerView recyclerViewEvents;
    private List<Events> eventsList;
    private EventsRecyclerAdapter eventsRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private ZXingScannerView mScannerView;
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
        eventsRecyclerAdapter = new EventsRecyclerAdapter(eventsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewEvents.setLayoutManager(mLayoutManager);
        recyclerViewEvents.setItemAnimator(new DefaultItemAnimator());
        recyclerViewEvents.setHasFixedSize(true);
        recyclerViewEvents.setAdapter(eventsRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        //String nameFromIntent = getIntent().getStringExtra("Nombre");
        //textViewNombre.setText(nameFromIntent);

        getDataFromSQLite();
    }

    public void QrScanner(View view){
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera
    }

    @Override
    public void onPause() {
        try {
            super.onPause();
            mScannerView.stopCamera();   // Stop camera on pause
        }
        catch (Exception e){
            return;
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.e("handler", rawResult.getText());
        Log.e("handler", rawResult.getBarcodeFormat().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();

    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                eventsList.clear();
                //eventsList.addAll(databaseHelper.getAllEvents());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                eventsRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
