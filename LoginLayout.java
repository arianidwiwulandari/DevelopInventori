package kel2.ddplc.inventoryfaris;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

class LoginLayout extends AppCompatActivity {

    private static final String TAG = "Login";
    private Toast backtoast;
    Profile user = new Profile();
    // UI references.
    private EditText usernameView;
    private TextInputEditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // Set up the login form.
        usernameView = (EditText) findViewById(R.id.txtusername);
        passwordView = (TextInputEditText) findViewById(R.id.txtpassword);
    }

    public void login(View view) {
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        Log.d(TAG, "Login");
        if (!validate()) {
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(LoginLayout.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();


        // TODO: Implement your own authentication logic here.
        if (username.contains(user.getUsername()) && password.contains(user.getPassword())) {
            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {
                    // You don't need anything here
                }

                public void onFinish() {
                    progressDialog.dismiss();
                    loginSukses();
                }
            }.start();
        } else {
            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {
                    // You don't need anything here
                }

                public void onFinish() {
                    progressDialog.dismiss();
                    aksesDitolak();
                }
            }.start();
        }
    }

    long lastPress;
    Toast backpressToast;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastPress > 5000){
            backpressToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_LONG);
            backpressToast.show();
            lastPress = currentTime;
        } else {
            if (backpressToast != null)
                backpressToast.cancel();
            super.onBackPressed();
        }
    }

    public void loginSukses() {
        Intent intent = new Intent(this, MenuLayout.class);
        startActivity(intent);
    }

    public void aksesDitolak() {
        Toast.makeText(getBaseContext(), "Gagal login. Username/Password salah", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        if (username.isEmpty()) {
            usernameView.setError("masukkan username");
            valid = false;
        } else {
            usernameView.setError(null);
        }

        if (password.isEmpty()) {
            passwordView.setError("masukkan password");
            valid = false;
        } else {
            passwordView.setError(null);
        }

        return valid;
    }
    public void ubahPass(View view){
        FragmentManager fm = getSupportFragmentManager();
        ChangePassFragment changepass_DialogFragment = ChangePassFragment.newInstance("Some Title");
        changepass_DialogFragment.show(fm, "ChangePassFragment");
    }
}