package com.upc.agnosticsix.qrhere;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import helpers.InputValidation;
import sql.DatabaseHelper;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText usertxt;
    private TextInputEditText passtxt;

    private AppCompatButton iniciarbtn;

    private AppCompatTextView generaractbtn;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        usertxt = (TextInputEditText) findViewById(R.id.usertxt);
        passtxt = (TextInputEditText) findViewById(R.id.passtxt);

        iniciarbtn = (AppCompatButton) findViewById(R.id.iniciarbtn);

        generaractbtn = (AppCompatTextView) findViewById(R.id.generaractbtn);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        iniciarbtn.setOnClickListener(this);
        generaractbtn.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iniciarbtn:
                verifyFromSQLite();
                break;
            case R.id.generaractbtn:
                // Navigate to GenerateQRActivity
                Intent intentGenerator = new Intent(getApplicationContext(), GenerateQRActivity.class);
                startActivity(intentGenerator);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(usertxt, textInputLayoutUsername, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(passtxt, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(usertxt.getText().toString().trim()
                , passtxt.getText().toString().trim())) {


            Intent accountsIntent = new Intent(this, EventsListActivity.class);
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        usertxt.setText(null);
        passtxt.setText(null);
    }
}
