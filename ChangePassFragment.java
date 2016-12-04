package kel2.ddplc.inventoryfaris;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;

/**
 * Created by Alpha on 28/11/2016.
 */

public class ChangePassFragment extends DialogFragment {

    public ChangePassFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ChangePassFragment newInstance(String title) {
        ChangePassFragment frag = new ChangePassFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final DBPHandler db = new DBPHandler(getActivity());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_change_pass, null))
                // Add action buttons
                .setPositiveButton(R.string.ubah_pass, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText oldpassView = (EditText) ((AlertDialog) dialog).findViewById(R.id.pass_lama);
                        EditText newpassView = (EditText) ((AlertDialog) dialog).findViewById(R.id.pass_baru);
                        EditText confirmnewpassView = (EditText) ((AlertDialog) dialog).findViewById(R.id.confirm_pas);

                        String oldpass = oldpassView.getText().toString();
                        String newpass = newpassView.getText().toString();
                        String confirmnewpass = confirmnewpassView.getText().toString();

                        DBPHandler db = new DBPHandler(getContext());

                        //Validating field
                        if (oldpass.isEmpty()) {
                            oldpassView.setError("masukkan password lama");
                            return;
                        } else {
                            oldpassView.setError(null);
                        }
                        if (newpass.isEmpty()) {
                            newpassView.setError("masukkan password baru");
                            return;
                        } else {
                            newpassView.setError(null);
                        }
                        if (!confirmnewpass.contains(newpass)) {
                            confirmnewpassView.setError("masukkan sesuai password baru");
                        } else {
                            confirmnewpassView.setError(null);
                        }

                        String pass = db.getPwd();
                        try {
                            Encryption aese = new Encryption("SHA1");
                            oldpass=aese.encrypt(oldpass);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(oldpass.contains(pass) && newpass.contains(confirmnewpass)){
                            try {
                                Encryption aese = new Encryption("SHA1");
                                newpass=aese.encrypt(newpass);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.d("new pass :", newpass);
                            db.setPwd(newpass);
                            //db.updateProfile(user);
                            db.close();
                        }
                        else{
                            oldpassView.setError("masukkan sesuai dengan password lama anda");
                        }
                    }
                })
                .setNegativeButton(R.string.batal, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangePassFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}