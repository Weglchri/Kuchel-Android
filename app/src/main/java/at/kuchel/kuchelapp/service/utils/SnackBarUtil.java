package at.kuchel.kuchelapp.service.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackBarUtil {

    private String message;
    private View view;

    public SnackBarUtil messages(String... messages) {
        StringBuilder sb = new StringBuilder();
        for(String message : messages ) {
            sb.append(message);
        }
        return this;
    }
    
    public SnackBarUtil setView(View view) {
        this.view = view;
        return this;
    }

    public SnackBarUtil text(String message) {
       this.message = message;
        return this;
    }

    public void fireSnackMessage() {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
