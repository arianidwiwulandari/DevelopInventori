package kel2.ddplc.inventoryfaris;

/**
 * Created by Alpha on 28/11/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DBPHandler extends SQLiteOpenHelper {
    private Context context;
    public SQLiteDatabase myDataBase;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static String DB_NAME = "rofiles.db";
    private static String DB_PATH ="/data/data/"+BuildConfig.APPLICATION_ID+"/databases/";
    // Contacts table name
    private static final String TABLE_NAME = "rofil";

    // Contacts Table Columns names
    private static final String KEY_UNM = "uname";
    private static final String KEY_PWD = "pwd";

    public DBPHandler (Context context) {
        super(context,DB_NAME,null,1);
        this.context=context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println("Database exists");
            opendatabase();
        } else {
            //System.out.println("Database doesn't exist");
            try{
                createdatabase();
            }
            catch(Exception e){

            }
        }
    }
    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(dbexist) {
            //System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            //System.out.println("Database doesn't exist");
        }
        return checkdb;
    }
    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }
    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
//        db.execSQL(
//                "create table rofil ("
//                        +KEY_UNM +" TEXT PRIMARY KEY, "
//                        +KEY_PWD +" TEXT" +")"
//        );
//        db.execSQL(
//                "insert into rofil values('hello!',10)"
//        );
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    //read profile
    public String getData(String tablename, String column) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String[] data = null;
        if (cursor.moveToFirst()) {
            do {
                // get  the  data into array,or class variable
            } while (cursor.moveToNext());
        }
        return selectQuery;
    }
    //update table
    public int updateProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UNM, profile.getUser());
        values.put(KEY_PWD, profile.getPass());

        // updating row
        return db.update(TABLE_NAME, values, KEY_UNM + " = ?", new String[] { String.valueOf(profile.getUser()) });
    }
}