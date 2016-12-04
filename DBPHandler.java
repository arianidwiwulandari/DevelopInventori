package kel2.ddplc.inventoryfaris;

/**
 * Created by Alpha on 28/11/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBPHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "rofiles.db";
    public static final String TABLE_NAME = "rofile";
    public static final String COLUMN_UNAME = "fariz";
    public static final String COLUMN_PWD = "TEH09nPue9iB7PDg5vWeFA==";

    public DBPHandler(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "CREATE TABLE rofile " +
                        "(uname TEXT PRIMARY KEY, pwd TEXT)"
        );
        db.execSQL("INSERT INTO rofile "
                + " (uname, pwd)"
                + " VALUES ('fariz', 'TEH09nPue9iB7PDg5vWeFA');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS rofile");
        onCreate(db);
    }

    public String getPwd() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur;
        cur =  db.rawQuery( "SELECT pwd FROM rofile;", null );
        cur.moveToFirst();
        String result="";
        if (cur != null) {
            // Loop through all Results
            do {
                result = cur.getString(cur.getColumnIndex("pwd"));
            }while(cur.moveToNext());
        }
        return result;
    }

    public void setPwd(String pwd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("uname", "fariz");
        cv.put("pwd", pwd);
        db.update("rofile", cv, null, null);
    }
}