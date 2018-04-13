package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import model.Alumno;
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
    public static final String SERVER_URL = "";

    // Database Name
    private static final String DATABASE_NAME = "QrHereDB.db";

    // User table name
    private static final String TABLE_USER = "user";
    public static final String TABLE_EVENTS = "events";
    private static final String TABLE_ALUMNOEVENTO = "alumnoevento";
    private static final String TABLE_ALUMNO = "alumno";


    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_EVENT_ID = "event_id";
    private static final String COLUMN_EVENT_NAME = "event_name";
    private static final String COLUMN_EVENT_DESCRIPTION = "event_description";
    private static final String COLUMN_EVENT_CUPO = "event_cupo";
    private static final String COLUMN_ALUMNOEVENTO_ID = "ae_id";
    private static final String COLUMN_ALUMNOEVENTO_IDALUMNO = "alumnoe_id";
    private static final String COLUMN_ALUMNOEVENTO_IDEVENTO = "event_id";
    private static final String COLUMN_ALUMNO_ID = "alumno_id";
    private static final String COLUMN_ALUMNO_MATRICULA = "alumno_matricula";
    private static final String COLUMN_ALUMNO_NOMBRE = "alumno_nombre";
    private static final String COLUMN_ALUMNO_CARRERA = "alumno_carrera";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
            + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_EVENT_NAME + " TEXT,"
            + COLUMN_EVENT_DESCRIPTION + " TEXT," + COLUMN_EVENT_CUPO + " TEXT" + ")";

    private String CREATE_ALUMNOEVENTO_TABLE = "CREATE TABLE " + TABLE_ALUMNOEVENTO + "("
            + COLUMN_ALUMNOEVENTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ALUMNOEVENTO_IDALUMNO + " INTEGER,"
            + COLUMN_ALUMNO_NOMBRE + " TEXT" + ")";

    private String CREATE_ALUMNO_TABLE = "CREATE TABLE " + TABLE_ALUMNO + "("
            + COLUMN_ALUMNO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ALUMNO_MATRICULA + " TEXT,"
            + COLUMN_ALUMNO_NOMBRE + " TEXT,"  + COLUMN_ALUMNO_CARRERA + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_EVENTS;
    private String DROP_ALUMNOEVENTO_TABLE = "DROP TABLE IF EXISTS " + TABLE_ALUMNOEVENTO;
    private String DROP_ALUMNO_TABLE = "DROP TABLE IF EXISTS " + TABLE_ALUMNO;

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
        db.execSQL(CREATE_ALUMNO_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_EVENTS_TABLE);
        db.execSQL(DROP_ALUMNOEVENTO_TABLE);
        db.execSQL(DROP_ALUMNO_TABLE);

        // Create tables again
        onCreate(db);

    }

    public void addAlumnoEvento(AlumnoEvento alumnoEvento) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ALUMNOEVENTO_IDALUMNO, alumnoEvento.getIdalumno());
        values.put(COLUMN_ALUMNOEVENTO_IDEVENTO, alumnoEvento.getIdevento());

        // Inserting Row
        db.insert(TABLE_ALUMNOEVENTO, null, values);
        db.close();
    }

    public void updateAlummnoEvento(AlumnoEvento alumnoEvento) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ALUMNOEVENTO_IDALUMNO, alumnoEvento.getIdalumno());
        values.put(COLUMN_ALUMNOEVENTO_IDEVENTO, alumnoEvento.getIdevento());

        // updating row
        db.update(TABLE_ALUMNOEVENTO, values, COLUMN_ALUMNOEVENTO_ID + " = ?",
                new String[]{String.valueOf(alumnoEvento.getId())});
        db.close();
    }

    public void deleteAlumnoEvento(AlumnoEvento alumnoEvento) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_ALUMNOEVENTO, COLUMN_ALUMNOEVENTO_ID + " = ?",
                new String[]{String.valueOf(alumnoEvento.getId())});
        db.close();
    }

    public String getAlumno(String matricula){
        // array of columns to fetch
        String[] columns = {
                COLUMN_ALUMNO_ID,
                COLUMN_ALUMNO_MATRICULA,
                COLUMN_ALUMNO_NOMBRE,
                COLUMN_ALUMNO_CARRERA
        };
        String query2 = null, b = null;
        // sorting orders

        List<Alumno> alumnoList = new ArrayList<Alumno>();

        SQLiteDatabase db = this.getReadableDatabase();


        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        Cursor cursor = db.query(TABLE_ALUMNO,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                b = cursor.getString(1);

                if(b.equals(matricula)){
                    query2 = cursor.getString(0);
                    break;
                }
            }
            while (cursor.moveToNext());



        }
        cursor.close();
        db.close();

        // return user list
        return query2;
    }



    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Alumno> getAllStudents(Alumno alumno) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ALUMNOEVENTO_IDALUMNO,
                COLUMN_ALUMNO_ID
        };

        // sorting orders
        String sortOrder =
                COLUMN_ALUMNOEVENTO_IDALUMNO + " ASC";
        List<Alumno> studentsList = new ArrayList<Alumno>();


        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from " + TABLE_ALUMNOEVENTO + " inner join " + TABLE_ALUMNO
                + " on " + COLUMN_ALUMNOEVENTO_IDALUMNO + " = " + COLUMN_ALUMNO_ID
                + " where " + COLUMN_ALUMNO_MATRICULA + " = " + alumno;

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.rawQuery(
                query,
                null
        );


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                alumno = new Alumno();
                alumno.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_ID))));
                alumno.setMatricula(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_MATRICULA)));
                alumno.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_NOMBRE)));
                alumno.setCarrera(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_CARRERA)));
                // Adding user record to list
                studentsList.add(alumno);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return students list
        return studentsList;
    }

    public List<Alumno> getAllStudents(int idevento) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ALUMNOEVENTO_IDALUMNO,
                COLUMN_ALUMNO_ID
        };

        // sorting orders
        String sortOrder =
                COLUMN_ALUMNOEVENTO_IDALUMNO + " ASC";
        List<Alumno> studentsList = new ArrayList<Alumno>();


        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from " + TABLE_ALUMNOEVENTO + " inner join " + TABLE_ALUMNO
                + " on " + COLUMN_ALUMNOEVENTO_IDALUMNO + " = " + COLUMN_ALUMNO_ID
                + " where " + COLUMN_ALUMNOEVENTO_IDEVENTO + " = " + idevento;

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.rawQuery(
                query,
                null
        );


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Alumno alumno = new Alumno();
                alumno.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_ID))));
                alumno.setMatricula(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_MATRICULA)));
                alumno.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_NOMBRE)));
                alumno.setCarrera(cursor.getString(cursor.getColumnIndex(COLUMN_ALUMNO_CARRERA)));
                // Adding user record to list
                studentsList.add(alumno);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return students list

        return studentsList;
    }

    public void addEvento(Events events) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, events.getEvento());
        values.put(COLUMN_EVENT_DESCRIPTION, events.getDescripcion());
        values.put(COLUMN_EVENT_CUPO, events.getCupo());

        // Inserting Row
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    public void updateEvent(Events events) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, events.getEvento());
        values.put(COLUMN_EVENT_DESCRIPTION, events.getDescripcion());
        values.put(COLUMN_EVENT_CUPO, events.getCupo());

        // updating row
        db.update(TABLE_EVENTS, values, COLUMN_EVENT_ID + " = ?",
                new String[]{String.valueOf(events.getId())});
        db.close();
    }

    public void deleteEvent(Events events) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_EVENTS, COLUMN_EVENT_ID + " = ?",
                new String[]{String.valueOf(events.getId())});
        db.close();
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

    public boolean checkAlumno(int idevento, String matricula) {

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from " + TABLE_ALUMNOEVENTO + " inner join " + TABLE_ALUMNO
                + " on " + COLUMN_ALUMNOEVENTO_IDALUMNO + " = " + COLUMN_ALUMNO_ID
                + " where " + COLUMN_ALUMNOEVENTO_IDEVENTO + " = " + idevento + " and "
                + COLUMN_ALUMNO_MATRICULA + " = " + matricula;

        // query alumno table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.rawQuery(
                query,                      //filter by row groups
                null
        );                      //The sort order


        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkEvent(String evento) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_EVENT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_EVENT_NAME + " = ?";

        // selection argument
        String[] selectionArgs = {evento};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_EVENTS, //Table to query
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
}
