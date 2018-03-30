package at.kuchel.kuchelapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import at.kuchel.kuchelapp.api.Image;
import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.dto.BitmapImage;
import at.kuchel.kuchelapp.repository.KuchelDatabase;
import at.kuchel.kuchelapp.service.RecipeServiceDb;
import at.kuchel.kuchelapp.service.RecipeServiceRest;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private static RecipeListActivity mInstance;
    private boolean mTwoPane;
    private List<Recipe> recipes = new ArrayList<>();
    private boolean loadOnlyFromDb = true;

    public static final String KUCHEL = "kuchel";
    private RecipeServiceDb recipeServiceDb = new RecipeServiceDb(this);
    private RecipeServiceRest recipeServiceRest = new RecipeServiceRest(this);
    private KuchelDatabase database;
    private List<BitmapImage> images = new ArrayList<>();

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        drawer = (DrawerLayout) findViewById(R.id.activity_main_list);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //database related stuff
        createDatabase();
        //getApplicationContext().deleteDatabase("kuchel");

        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }

        //try to use somehting like the "isOnline" method below
        if (loadOnlyFromDb) {      //todo check if internet is ok and speed good - for now simulate db read or rest
            recipeServiceRest.retrieveRecipes();
        } else {
            recipeServiceDb.retrieveRecipes(database);
        }
    }

    //database related connection create
    private void createDatabase() {
        database = Room.databaseBuilder(getApplicationContext(), KuchelDatabase.class, KUCHEL).build();
    }

    public void handleRetrievedRecipesFromRest(List<Recipe> recipes) {
        this.recipes = recipes;
        showRecipesInOverview();
        recipeServiceDb.storeNewAndUpdateExistingRecipes(recipes, database);
    }

    public void retrievedRecipesFromDatabase(List<Recipe> recipesFromDb) {
        recipes = recipesFromDb;
        showRecipesInOverview();
    }

    public void retrievedImageBitmap(BitmapImage bitmapImage) {
        this.images.add(bitmapImage);
//        todo maybe notify only and load via url
//        todo method to show images needed - refresh
//        showImagesInOverview();
    }

    private void showRecipesInOverview(){
        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, recipes, mTwoPane));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_dashboard) {  //go back to mainpage
            startActivity(new Intent(this, Dashboard.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            drawer.closeDrawers();
            return true;
        }
        if (id == R.id.nav_recipes) {  //go to all recipes
            drawer.closeDrawers();
            return true;
        }
        if (id == R.id.nav_myrecipes) {  //go to my recipes
            startActivity(new Intent(this, RecipeListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            drawer.closeDrawers();
            return true;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final RecipeListActivity mParentActivity;
        private final List<Recipe> recipes;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe item = (Recipe) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, String.valueOf(item.getId()));
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, String.valueOf(item.getId()));

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(RecipeListActivity parent, List<Recipe> recipes, boolean twoPane) {
            this.recipes = recipes;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(recipes.get(position).getId()));
            holder.mNameView.setText(recipes.get(position).getName());
            holder.mDurationView.setText(recipes.get(position).getDuration());
            holder.mDifficultyView.setText(recipes.get(position).getDifficulty());

            holder.itemView.setTag(recipes.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return recipes.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView mIdView;
            final TextView mNameView;
            final TextView mDurationView;
            final TextView mDifficultyView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mNameView = (TextView) view.findViewById(R.id.content_text);
                mDurationView = (TextView) view.findViewById(R.id.duration_text);
                mDifficultyView = (TextView) view.findViewById(R.id.difficulty_text);
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
