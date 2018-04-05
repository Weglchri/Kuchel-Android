package at.kuchel.kuchelapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import at.kuchel.kuchelapp.api.Image;
import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.dto.BitmapImage;
import at.kuchel.kuchelapp.service.RecipeServiceDb;
import at.kuchel.kuchelapp.service.RecipeServiceRest;
import at.kuchel.kuchelapp.service.utils.DatabaseManager;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static RecipeListActivity mInstance;
    private boolean mTwoPane;
    private List<Recipe> recipes = new ArrayList<>();

    public static final String KUCHEL = "kuchel";
    private RecipeServiceDb recipeServiceDb = new RecipeServiceDb(this);
    private RecipeServiceRest recipeServiceRest = new RecipeServiceRest(this);
    private List<BitmapImage> images = new ArrayList<>();

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //database related stuff
        DatabaseManager.getDatabase(getApplicationContext());
//        getApplicationContext().deleteDatabase("kuchel");

        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }

//        UserService userService = new UserService();
//        userService.loadUserProfileViaRest("bernhard", "pass");

        //try to use somehting like the "isOnline" method below
        //todo check if internet is ok and speed good - for now simulate db read or rest
        recipeServiceRest.retrieveRecipes();
    }

    public void handleRetrievedRecipesFromRest(List<Recipe> recipes) {
        Log.i("retrieve_recipe_rest", String.format("Retrieved %s recipes from rest", recipes.size()));
        this.recipes.addAll(recipes);
        showRecipesInOverview();

        //todo load only not loaded until now
        recipeServiceDb.retrieveRecipes();
        recipeServiceDb.storeNewAndUpdateExistingRecipes(recipes, DatabaseManager.getDatabase(getApplicationContext()));
    }

    public void retrievedRecipesFromDatabase(List<Recipe> recipesFromDb) {
        Log.i("retrieve_recipe_db", String.format("Retrieved %s recipes from db", recipesFromDb.size()));
        for (Recipe recipeDb : recipesFromDb) {
            boolean alreadyAdded = false;
            for (Recipe alreadyReturnedRecipe : this.recipes) {
                if (Objects.equals(alreadyReturnedRecipe.getId(), recipeDb.getId())) {
                    alreadyAdded = true;
                }
            }
            if (!alreadyAdded) {
                Log.i("retrieve_recipe_db_add", String.format("recipe with id %s is added from db to list", recipeDb.getId()));
                if (recipeDb.getImages().size() > 0)
                    recipeServiceDb.loadImages(recipeDb.getImages().get(0).getId());
                this.recipes.add(recipeDb);
            }
        }

        showRecipesInOverview();
    }

    public void retrievedImageBitmap(BitmapImage bitmapImage) {
        this.images.add(bitmapImage);
        View recyclerView = findViewById(R.id.recipe_list);
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void showRecipesInOverview() {
        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, recipes, mTwoPane, images));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_dashboard) {  //go back to mainpage
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.slide_in, R.anim.nothing);
            drawer.closeDrawers();
            return true;
        }
        if (id == R.id.nav_recipes) {  //go to all recipes
            drawer.closeDrawers();
            return true;
        }
        if (id == R.id.nav_myrecipes) {  //go to my recipes
            startActivity(new Intent(this, RecipeListActivity.class));
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
        private List<BitmapImage> mImages;
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
                            .setCustomAnimations(R.anim.slide_in, R.anim.nothing)
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

        SimpleItemRecyclerViewAdapter(RecipeListActivity parent, List<Recipe> recipes, boolean twoPane, List<BitmapImage> images) {
            this.recipes = recipes;
            mParentActivity = parent;
            mTwoPane = twoPane;
            mImages = images;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(recipes.get(position).getId()));
            holder.mNameView.setText(recipes.get(position).getName());
            holder.mDurationView.setText(recipes.get(position).getDuration());
            holder.mDifficultyView.setText(recipes.get(position).getDifficulty());

            for (BitmapImage bitmapImage : mImages) {
                for(Image image:recipes.get(position).getImages())
                if (bitmapImage.getImageId().equals(image.getId())) {
                    holder.mImageView.setImageBitmap(bitmapImage.getImage());
                }
            }

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
            final ImageView mImageView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mNameView = (TextView) view.findViewById(R.id.content_text);
                mDurationView = (TextView) view.findViewById(R.id.duration_text);
                mDifficultyView = (TextView) view.findViewById(R.id.difficulty_text);
                mImageView = (ImageView) view.findViewById(R.id.imageView);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.slide_out);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
