package at.kuchel.kuchelapp;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.dto.BitmapImage;
import at.kuchel.kuchelapp.service.utils.SnackBarUtil;

/**
 * Created by bernhard on 06.04.2018.
 */

public abstract class AbstractActivity extends AppCompatActivity {

    public void callSnackBarPopup(String message){
        SnackBarUtil.fireSnackMessage(getView(),message);
    }

    protected abstract View getView();
}
