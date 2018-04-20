package com.upc.agnosticsix.qrhere;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import adapters.StudentsRecyclerAdapter;
import helpers.InputValidation;
import model.Alumno;
import model.AlumnoEvento;
import sql.DatabaseHelper;

public class MatriculaActivity extends AppCompatActivity {

    private AppCompatActivity activity = MatriculaActivity.this;
    private DatabaseHelper databaseHelper;
    private List<AlumnoEvento> stuList;
    private List<Alumno> studentList;
    private StudentsRecyclerAdapter studentsRecyclerAdapter;
    private int idEventoFromIntent, idEvento, idAlumno;
    public AppCompatTextView textViewNombreS;
    public AppCompatTextView textViewCarrera;
    public AppCompatTextView textViewMatricula;
    private TextInputEditText matriculaTxt;
    private String test;
    private AlumnoEvento alumnoEvento;
    private Alumno alumno;
    private TextInputLayout textInputLayoutMatricula;
    private Button button;

    private InputValidation inputValidation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();
    }

    private void initViews(){
        textViewMatricula = (AppCompatTextView) findViewById(R.id.textViewMatricula);
        textViewNombreS = (AppCompatTextView) findViewById(R.id.textViewNombreS);
        textViewCarrera = (AppCompatTextView) findViewById(R.id.textViewCarrera);
        matriculaTxt = (TextInputEditText) findViewById(R.id.matriculatxt);
        textInputLayoutMatricula = (TextInputLayout) findViewById(R.id.textInputLayoutMatricula);
        button = (Button) findViewById(R.id.regismatribtn);
        button.setClickable(false);
    }

    private void initObjects(){
        stuList = new ArrayList<>();
        studentList = new ArrayList<>();
        studentsRecyclerAdapter = new StudentsRecyclerAdapter(studentList);
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

        idEventoFromIntent = getIntent().getIntExtra("idevento", idEvento);
        alumno = new Alumno();
        alumnoEvento = new AlumnoEvento();

        getDataFromSQLite();

        //getDataFromSQLite();
    }

    public void onClick2(View view){
        switch (view.getId()){
            case R.id.buscarmatribtn:
                verifyFromSQLite();
                break;

            case R.id.regismatribtn:
                registrarAlumno();
                break;

        }

    }

    private void verifyFromSQLite() {
        test = matriculaTxt.getText().toString().trim();
        if(!inputValidation.isInputEditTextFilled(matriculaTxt, textInputLayoutMatricula, "Escriba una matricula valida")){
            return;
        }
        if(databaseHelper.checkMatricula(test)){
            studentList.clear();
            studentList.addAll(databaseHelper.getAlumnoM(test));

            textViewMatricula.setText(studentList.get(0).getMatricula());
            textViewNombreS.setText(studentList.get(0).getNombre());
            textViewCarrera.setText(studentList.get(0).getCarrera());
            button.setClickable(true);
        }
    }

    private void registrarAlumno(){
        test = matriculaTxt.getText().toString();
        //Log.d("eya", "" + test);
        String a = databaseHelper.getAlumno(test);

        idAlumno = Integer.parseInt(a);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        alumnoEvento.setIdalumno(idAlumno);
        alumnoEvento.setIdevento(idEventoFromIntent);

        databaseHelper.addAlumnoEvento(alumnoEvento);
        builder.setTitle("Success!");
        builder.setMessage("Alumno " + test + " registrado");
        AlertDialog alert1 = builder.create();
        alert1.show();



        emptyInputEditText();
    }

    private void emptyInputEditText() {
        button.setClickable(false);
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                studentList.clear();
                studentList.addAll(databaseHelper.getAllStudents(idEventoFromIntent));
                //Log.d("ey", "" + idEventoFromIntent);


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
