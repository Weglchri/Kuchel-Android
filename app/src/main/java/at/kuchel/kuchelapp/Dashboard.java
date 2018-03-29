package at.kuchel.kuchelapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import at.kuchel.kuchelapp.repository.KuchelDatabase;



public class Dashboard extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    public static final String KUCHEL = "kuchel";

    private KuchelDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            //onSaveInstanceState(savedInstanceState);
        }

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //database related stuff
        createDatabase();
        //getApplicationContext().deleteDatabase("kuchel");
    }

    /*                  user handling relevant
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("Wegl", user);
        Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override //options menu right hand side
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /* todo: do action */
        return super.onOptionsItemSelected(item);
    }

    @Override //drawer menu left hand side
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_dashboard) {  //go back to mainpage
            startActivity(new Intent(this, Dashboard.class));
            return true;
        }
        if (id == R.id.nav_recipes) {  //go to all recipes
            startActivity(new Intent(this, RecipeListActivity.class));
            return true;
        }
        if (id == R.id.nav_myrecipes) {  //go to my recipes
            startActivity(new Intent(this, RecipeListActivity.class));
            return true;
        }
        return true;
    }


    //database related connection create
    private void createDatabase() {
        database = Room.databaseBuilder(getApplicationContext(), KuchelDatabase.class, KUCHEL).build();
    }

}

