package at.kuchel.kuchelapp.service.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public abstract class SnackBarUtil {

    public static void fireSnackMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
