package com.upc.agnosticsix.qrhere;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import adapters.StudentsRecyclerAdapter;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import model.AlumnoEvento;
import sql.DatabaseHelper;

public class StudentsListActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private AppCompatActivity activity = StudentsListActivity.this;
    private AppCompatTextView textViewNombre;
    private RecyclerView recyclerViewStudents;
    private List<AlumnoEvento> stuList;
    private StudentsRecyclerAdapter studentsRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();
    }

    private void initViews() {
        textViewNombre = (AppCompatTextView) findViewById(R.id.textViewNombre);
        recyclerViewStudents = (RecyclerView) findViewById(R.id.recyclerViewStudents);
    }

    private void initObjects() {
        stuList = new ArrayList<>();
        studentsRecyclerAdapter = new StudentsRecyclerAdapter(stuList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewStudents.setLayoutManager(mLayoutManager);
        recyclerViewStudents.setItemAnimator(new DefaultItemAnimator());
        recyclerViewStudents.setHasFixedSize(true);
        recyclerViewStudents.setAdapter(studentsRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        String nameFromIntent = getIntent().getStringExtra("idevento");
        textViewNombre.setText(nameFromIntent);

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
                stuList.clear();
                //stuList.addAll(databaseHelper.getAllStudents());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                studentsRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


}
