package kel2.ddplc.inventoryfaris;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

class LoginLayout extends AppCompatActivity {

    private static final String TAG = "Login";
    private Toast backtoast;
    // UI references.
    private EditText usernameView;
    private EditText passwordView;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // Set up the login form.
        usernameView = (EditText) findViewById(R.id.txtusername);
        passwordView = (EditText) findViewById(R.id.txtpassword);
    }

    public void login(View view) {
        Profile user = new Profile();
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();
        //dummy credential
        user.setUsername("admin");
        user.setPassword("admin");

        Log.d(TAG, "Login");
        if (!validate()) {
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginLayout.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();


        // TODO: Implement your own authentication logic here.
        if (username.contains(user.getUsername()) && password.contains(user.getPassword())) {
            new Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            loginSukses();
                            progressDialog.dismiss();
                        }
                    }, 3000);
        } else {
            new Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            aksesDitolak();
                            progressDialog.dismiss();
                        }
                    }, 3000);
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
        Intent intent = new Intent(this, MenuProdukLayout.class);
        btnLogin.setEnabled(true);
        startActivity(intent);
        this.finish();
    }

    public void aksesDitolak() {
        Toast.makeText(getBaseContext(), "Gagal sign in. Username/Password salah", Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
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
}