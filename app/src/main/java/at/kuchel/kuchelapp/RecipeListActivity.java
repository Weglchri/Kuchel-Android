package at.kuchel.kuchelapp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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
import at.kuchel.kuchelapp.service.GlobalParamService;
import at.kuchel.kuchelapp.service.RecipeServiceDb;
import at.kuchel.kuchelapp.service.RecipeServiceRest;

import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.USERNAME;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AbstractRecipeActivity {

    private boolean mTwoPane;
    private List<Recipe> recipes = new ArrayList<>();
    public static final String KUCHEL = "kuchel";
    private String recipeCreator;
    private String pageTitle;

    private RecipeServiceDb recipeServiceDb = new RecipeServiceDb(this);
    private RecipeServiceRest recipeServiceRest = new RecipeServiceRest(this);
    private List<BitmapImage> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //if user is set it comes from ALLRECIPES or MYRECIPES list
        if (getIntent().getExtras() != null) {
            Log.i("RecipeListActivity", "getExtras");
            this.recipeCreator = getIntent().getExtras().getString("username");
            this.pageTitle = getIntent().getExtras().getString("title");
        }

        setTitle(pageTitle);

        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }
        recipeServiceRest.retrieveRecipes();
    }


    //right hand side navigation menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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
                                Snackbar
                                        .make(findViewById(R.id.activity_recipe_list), "Unexpected logout error occured", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null)
                                        .show();
                            } else {
                                Snackbar
                                        .make(findViewById(R.id.activity_recipe_list), "Erfolgreich ausgeloggt", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null)
                                        .show();
                            }
                        }
                    }, 1000);

                } else {
                    Snackbar
                            .make(findViewById(R.id.activity_recipe_list), "Nicht eingeloggt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                }
                return true;

            default:
                return false;
        }
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
    public void handleRecipesFromRest(List<Recipe> recipes) {
        Log.i("retrieve_recipe_rest", String.format("Retrieved %s recipes from rest", recipes == null ? "0" : recipes.size()));
        this.recipes.addAll(recipes);
        showRecipesInOverview();

        //todo load only not loaded until now
        recipeServiceDb.retrieveRecipes();
        recipeServiceDb.storeNewAndUpdateExistingRecipes(recipes);
    }

    @Override
    public void handleRecipesFromDb(List<Recipe> recipes) {
        Log.i("retrieve_recipe_db", String.format("Retrieved %s recipes from db", recipes.size()));
        for (Recipe recipeDb : recipes) {
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

                if (recipeCreator == null || recipeCreator.equals(recipeDb.getUsername())) {
                    this.recipes.add(recipeDb);
                }
            }
        }
        showRecipesInOverview();
    }

    @Override
    public void handleImageResponse(BitmapImage bitmapImage) {
        if (bitmapImage != null) {
            this.images.add(bitmapImage);
            View recyclerView = findViewById(R.id.recipe_list);
            setupRecyclerView((RecyclerView) recyclerView);
        }
    }

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

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
                        .replace(R.id.recipe_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, String.valueOf(item.getId()));
                startActivity(intent);
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
            for (Image image : recipes.get(position).getImages())
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
        overridePendingTransition(R.anim.nothing, R.anim.slide_out);
    }

    /*
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    */
}
