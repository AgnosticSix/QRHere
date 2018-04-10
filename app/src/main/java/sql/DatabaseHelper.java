package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.AlumnoEvento;
import model.Events;
import model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateo on 20/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "QrHereDB.db";

    // User table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_ALUMNOEVENTO = "alumnoevento";


    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_EVENT_ID = "event_id";
    private static final String COLUMN_EVENT_NAME = "event_name";
    private static final String COLUMN_EVENT_DESCRIPTION = "event_description";
    private static final String COLUMN_EVENT_CUPO = "event_cupo";
    private static final String COLUMN_ALUMNO_ID = "student_id";
    private static final String COLUMN_ALUMNO_MATRICULA = "student_matri";
    private static final String COLUMN_ALUMNO_NOMBRE = "student_name";
    private static final String COLUMN_ALUMNO_IDEVENTO = "student_idevento";
    private static final String COLUMN_ALUMNO_CARRERA = "student_career";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
            + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_EVENT_NAME + " TEXT,"
            + COLUMN_EVENT_DESCRIPTION + " TEXT," + COLUMN_EVENT_CUPO + " TEXT" + ")";

    private String CREATE_ALUMNOEVENTO_TABLE = "CREATE TABLE " + TABLE_ALUMNOEVENTO + "("
            + COLUMN_ALUMNO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ALUMNO_MATRICULA + " TEXT,"
            + COLUMN_ALUMNO_NOMBRE + " TEXT," + COLUMN_ALUMNO_IDEVENTO + " INTEGER," + COLUMN_ALUMNO_CARRERA + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_EVENTS;
    private String DROP_ALUMNOEVENTO_TABLE = "DROP TABLE IF EXISTS " + TABLE_ALUMNOEVENTO;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_ALUMNOEVENTO_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_EVENTS_TABLE);
        db.execSQL(DROP_ALUMNOEVENTO_TABLE);

        // Create tables again
        onCreate(db);

    }

    public void addAlumnoEvento(AlumnoEvento alumnoEvento) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ALUMNO_MATRICULA, alumnoEvento.getMatricula());
        values.put(COLUMN_ALUMNO_NOMBRE, alumnoEvento.getNombre());
        values.put(COLUMN_ALUMNO_CARRERA, alumnoEvento.getCarrera());
        values.put(COLUMN_ALUMNO_IDEVENTO, alumnoEvento.getIdEvento());

        // Inserting Row
        db.insert(TABLE_ALUMNOEVENTO, null, values);
        db.close();
    }

    public void updateAlummnoEvento(AlumnoEvento alumnoEvento) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ALUMNO_MATRICULA, alumnoEvento.getMatricula());
        values.put(COLUMN_ALUMNO_NOMBRE, alumnoEvento.getNombre());
        values.put(COLUMN_ALUMNO_IDEVENTO, alumnoEvento.getIdEvento());
        values.put(COLUMN_ALUMNO_CARRERA, alumnoEvento.getCarrera());

        // updating row
        db.update(TABLE_ALUMNOEVENTO, values, COLUMN_ALUMNO_ID + " = ?",
                new String[]{String.valueOf(alumnoEvento.getId())});
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<AlumnoEvento> getAllStudents(String idevento) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ALUMNO_ID,
                COLUMN_ALUMNO_MATRICULA,
                COLUMN_ALUMNO_NOMBRE,
                COLUMN_ALUMNO_IDEVENTO,
                COLUMN_ALUMNO_CARRERA
        };
        // sorting orders
        String sortOrder =
                COLUMN_ALUMNO_NOMBRE + " ASC";
        List<AlumnoEvento> studentsList = new ArrayList<AlumnoEvento>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_ALUMNOEVENTO, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AlumnoEvento alumnoEvento = new AlumnoEvento();
                alumnoEvento.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_ID))));
                alumnoEvento.setMatricula(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_MATRICULA)));
                alumnoEvento.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_NOMBRE)));
                alumnoEvento.setIdEvento(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_IDEVENTO))));
                alumnoEvento.setCarrera(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_CARRERA)));
                // Adding user record to list
                studentsList.add(alumnoEvento);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return students list
        return studentsList;
    }

    public List<Events> getAllEvents() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_EVENT_ID,
                COLUMN_EVENT_NAME,
                COLUMN_EVENT_DESCRIPTION,
                COLUMN_EVENT_CUPO
        };
        // sorting orders
        String sortOrder =
                COLUMN_EVENT_NAME + " ASC";
        List<Events> eventsList = new ArrayList<Events>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_EVENTS, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Events events = new Events();
                events.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_ID))));
                events.setEvento(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_NAME)));
                events.setDescripcion(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_DESCRIPTION)));
                events.setCupo(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_CUPO)));
                // Adding event record to list
                eventsList.add(events);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return eventsList;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_NAME + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
