package kel2.ddplc.inventoryfaris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RekapLayout extends AppCompatActivity {
    DatabaseHelper2 mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap_layout);
        mydb = new DatabaseHelper2(this);
    }
}
