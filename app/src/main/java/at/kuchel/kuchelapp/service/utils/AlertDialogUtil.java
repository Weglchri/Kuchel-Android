package at.kuchel.kuchelapp.service.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.CountDownTimer;


public class AlertDialogUtil {

    private final Context context;

    public AlertDialogUtil(Context context) {
        this.context = context;
    }

    public void process(String title, String message) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setTitle(title).setMessage(
                        message);

        final AlertDialog alert = dialog.create();
        alert.show();

        new CountDownTimer(6000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                alert.dismiss();
            }
        }.start();
    }
}
