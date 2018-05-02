package at.kuchel.kuchelapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import at.kuchel.kuchelapp.api.Ingredient;
import at.kuchel.kuchelapp.api.Instruction;
import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.builder.RecipeBuilder;
import at.kuchel.kuchelapp.mapper.RecipeMapper;
import at.kuchel.kuchelapp.service.GlobalParamService;

import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.USERNAME;

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

    private Recipe recipe;
    private Bitmap image;


    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("retrieve_recipe_rest", String.format("Retrieved  recipe from db"));

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) this.getActivity().findViewById(R.id.toolbar_layout_detailed);
        if (appBarLayout != null) {
            appBarLayout.setTitle(recipe.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        if (recipe != null) {
            ((TextView) rootView.findViewById(R.id.textViewDifficulty)).setText(RecipeMapper.getDifficultyAsString(recipe.getDifficulty()));
            ((TextView) rootView.findViewById(R.id.textViewDuration)).setText(recipe.getDuration() + "min");
            ((TextView) rootView.findViewById(R.id.textViewCreator)).setText(recipe.getUsername());

            RecipeBuilder recipeBuilder = new RecipeBuilder(recipe);

            // add ingredients to view
            LinearLayout linearLayoutIngredients = ((LinearLayout) rootView.findViewById(R.id.ingredientsView));
            linearLayoutIngredients.addView(recipeBuilder.buildIngredients(getContext()));

            // add instructions to view
            LinearLayout linearLayoutInstructions = ((LinearLayout) rootView.findViewById(R.id.instructionsView));
            linearLayoutInstructions.addView(recipeBuilder.buildInstructions(getContext()));


        }

        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
