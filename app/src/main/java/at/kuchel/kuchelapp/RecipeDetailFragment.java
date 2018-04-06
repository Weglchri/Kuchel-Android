package at.kuchel.kuchelapp;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.service.RecipeServiceDb;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private Recipe recipe = new Recipe();

    private RecipeServiceDb recipeServiceDb = new RecipeServiceDb(this);


    private String recipeId;

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            recipeId = String.valueOf(getArguments().get(ARG_ITEM_ID));
            recipeServiceDb.retrieveRecipes();

//            handleAsyncCallAndRedirect(this.getActivity(), getArguments().getString(ARG_ITEM_ID));
        }
    }

    public void handleRetrievedRecipesFromDb(List<Recipe> recipes) {
        //todo add method to service for only one recipe to load
        for (Recipe recipe : recipes){
            if(String.valueOf(recipe.getId()).endsWith(recipeId)){
                Log.i("retrieve_recipe_rest", String.format("Retrieved  recipe with id %s from db", recipe.getId()));

                //todo load only not loaded until now
                CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) this.getActivity().findViewById(R.id.toolbar_layout_detailed);
                if (appBarLayout != null) {
                    appBarLayout.setTitle(recipe.getName());
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (recipe != null) {
            //todo find the purpose for this
//            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(recipeEntity.getName());
        }

        return rootView;
    }
}
