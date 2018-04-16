package at.kuchel.kuchelapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import at.kuchel.kuchelapp.service.UserService;

public class LoginDialogFragment extends DialogFragment {

    private  UserService userService = new UserService();
    private DialogInterface dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i("DialogBox","onCreateDialog");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_signin, null);

        builder.setTitle("Login");
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setView(view);

        final EditText username =(EditText) view.findViewById(R.id.username);
        final EditText password =(EditText) view.findViewById(R.id.password);

        builder
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginDialogFragment.this.getDialog().cancel();
                    }
                })

                .setPositiveButton("Anmelden", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginDialogFragment.this.dialog = dialog;
                        userService.loadUserProfileViaRest((AbstractRecipeActivity) getActivity(), username.getText().toString(), password.getText().toString());
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static DialogFragment newInstance() {
        return new LoginDialogFragment();
    }
}
