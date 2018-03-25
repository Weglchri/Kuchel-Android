package at.kuchel.kuchelapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import at.kuchel.kuchelapp.api.Image;
import at.kuchel.kuchelapp.api.Recipe;
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
public class RecipeListActivity extends AppCompatActivity {

    private static RecipeListActivity mInstance;
    private boolean mTwoPane;
    private List<Recipe> recipes = new ArrayList<>();
    private boolean loadOnlyFromDb = false;

    public static final String KUCHEL = "kuchel";
    private RecipeServiceDb recipeServiceDb = new RecipeServiceDb(this);
    private RecipeServiceRest recipeServiceRest = new RecipeServiceRest(this);
    private KuchelDatabase database;
    private List<Bitmap> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        createDatabase();
        //todo use if db has to be resetted
//        getApplicationContext().deleteDatabase("kuchel");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        if (!loadOnlyFromDb) {      //todo check if internet is ok and speed good - for now simulate db read or rest
            recipeServiceRest.retrieveRecipes();
        } else {
            recipeServiceDb.retrieveRecipes(database);
        }
    }

    public void retrievedRecipesFromRest(List<Recipe> recipes) {
        this.recipes = recipes;
        showRecipesInOverview();
        recipeServiceDb.storeNewAndUpdateExistingRecipes(recipes, database);
    }

    public void retrievedRecipesFromDatabase(List<Recipe> recipesFromDb) {
        recipes = recipesFromDb;
        showRecipesInOverview();
    }

    public void retrievedImageBitmapsFromDatabase(Bitmap bitmap) {
        this.images.add(bitmap);
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

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

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

        SimpleItemRecyclerViewAdapter(RecipeListActivity parent,
                                      List<Recipe> recipes,
                                      boolean twoPane) {
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

    private void createDatabase() {
        database = Room.databaseBuilder(getApplicationContext(), KuchelDatabase.class, KUCHEL).build();
    }
}
