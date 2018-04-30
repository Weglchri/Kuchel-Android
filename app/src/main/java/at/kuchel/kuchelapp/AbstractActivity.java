package at.kuchel.kuchelapp;

import android.app.DialogFragment;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.dto.BitmapImage;
import at.kuchel.kuchelapp.service.GlobalParamService;
import at.kuchel.kuchelapp.service.utils.DatabaseManager;
import at.kuchel.kuchelapp.service.utils.SnackBarUtil;

/**
 * Created by bernhard on 06.04.2018.
 */

public abstract class AbstractActivity extends AppCompatActivity {

    public void callSnackBarPopup(String message){
        SnackBarUtil.fireSnackMessage(getView(),message);
    }

    protected abstract View getView();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if(GlobalParamService.isUserSet()) {
            menu.findItem(R.id.login_button).setVisible(false);
            menu.findItem(R.id.logout_button).setVisible(true);
        } else {
            menu.findItem(R.id.login_button).setVisible(true);
            menu.findItem(R.id.logout_button).setVisible(false);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.slide_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.login_button:
                DialogFragment newFragment = LoginDialogFragment.newInstance();
                newFragment.show(getFragmentManager(), "dialog");
                return true;

            case R.id.logout_button:
                if (GlobalParamService.isUserSet()) {
                    GlobalParamService.clearUserdata();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (GlobalParamService.isUserSet()) {
                                callSnackBarPopup("Ein unerwarteter Fehler ist aufgetreten!");
                            } else {
                                callSnackBarPopup("Erfolgreich ausgeloggt");
                            }
                        }
                    }, 1000);

                } else {
                    callSnackBarPopup("Nicht eingeloggt");
                }
                return true;

            case R.id.clear_database_button:
                getApplicationContext().deleteDatabase("kuchel");
                Log.i("Datenbank", "Datenbank wurde zurückgesetzt");
                callSnackBarPopup("Datenbank wurde zurückgesetzt");
                DatabaseManager.initDatabase(getApplicationContext());
                return true;

            default:
                return false;
        }
    }

}
