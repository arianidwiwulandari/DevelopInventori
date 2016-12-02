package kel2.ddplc.inventoryfaris;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LENOVO G40-30 on 12/2/2016.
 */

public class DatabaseHelper2 extends SQLiteOpenHelper {
    public static final String DATABASE_PRODUK = "produk.db";
    public static final String TABEL_PRODUK = "tabel.produk";
    public static final String ID_PRODUK = "id";
    public static final String HARGA_PRODUK = "harga";


    public DatabaseHelper2(Context context) {
        super(context, DATABASE_PRODUK, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("buat tabel" +TABEL_PRODUK+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT, MARKS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABEL IF EXIST" +TABEL_PRODUK);
        onCreate(db);
    }
}
