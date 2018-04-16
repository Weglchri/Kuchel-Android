package at.kuchel.kuchelapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import at.kuchel.kuchelapp.builder.GlobalParamBuilder;
import at.kuchel.kuchelapp.model.GlobalParamEntity;
import at.kuchel.kuchelapp.service.GlobalParamService;
import at.kuchel.kuchelapp.service.UserService;
import at.kuchel.kuchelapp.service.utils.DatabaseManager;

import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.USERNAME;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseManager.initDatabase(getApplicationContext());
        //getApplicationContext().deleteDatabase("kuchel");

        Button button_recipes = (Button)findViewById(R.id.button_recipes);
        button_recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecipeListActivity.class);
                intent.putExtra("title", "Alle Rezepte");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.nothing);
            }
        });
        Button button2 = (Button)findViewById(R.id.button_myrecipes);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GlobalParamService.isUserSet()) {
                    Intent intent = new Intent(getApplicationContext(), RecipeListActivity.class);
                    intent.putExtra("title",  "Meine Rezepte");
                    intent.putExtra("username", GlobalParamService.retrieveGlobalParam(USERNAME).getValue());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in, R.anim.nothing);
                } else {
                    Log.i("Meine Rezepte", "Nicht eingeloggt");
                    Snackbar.make(view, "Loggen Sie sich ein um ihre Rezepte zu sehen!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.activity_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override //options menu right hand side
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
                                Snackbar
                                        .make(findViewById(R.id.activity_main), "Unexpected logout error occured", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null)
                                        .show();
                            } else {
                                Snackbar
                                        .make(findViewById(R.id.activity_main), "Erfolgreich ausgeloggt", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null)
                                        .show();
                            }
                        }
                    }, 1000);

                } else {
                    Snackbar
                            .make(findViewById(R.id.activity_main), "Nicht eingeloggt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                }
                return true;

            default:
                return false;
        }
    }

    @Override //drawer menu left hand side
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_dashboard) {  //go back to mainpage
            drawer.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.nav_recipes) {  //go to all recipes
            Intent intent = new Intent(getApplicationContext(), RecipeListActivity.class);
            intent.putExtra("title", "Alle Rezepte");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.nothing);
        }
        if (id == R.id.nav_myrecipes) {  //go to my recipes
            if(GlobalParamService.isUserSet()) {
                Intent intent = new Intent(getApplicationContext(), RecipeListActivity.class);
                intent.putExtra("title",  "Meine Rezepte");
                intent.putExtra("username", GlobalParamService.retrieveGlobalParam(USERNAME).getValue());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.nothing);
            } else {
                Log.i("Meine Rezepte", "Nicht eingeloggt");
                Snackbar.make(findViewById(R.id.activity_main), "Loggen Sie sich ein um ihre Rezepte zu sehen!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.slide_out);
    }

}

