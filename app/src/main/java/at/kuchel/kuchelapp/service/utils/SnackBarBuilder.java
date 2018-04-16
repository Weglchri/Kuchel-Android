package at.kuchel.kuchelapp.service.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import at.kuchel.kuchelapp.R;

public class SnackBarBuilder {

    private String message;
    private View view;

    public SnackBarBuilder messages(String... messages) {
        StringBuilder sb = new StringBuilder();
        for(String message : messages ) {
            sb.append(message);
        }
        return this;
    }
    
    public SnackBarBuilder setView(View view) {
        this.view = view;
        return this;
    }

    public SnackBarBuilder text(String message) {
       this.message = message;
        return this;
    }

    public void fireSnackMessage() {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    
}
