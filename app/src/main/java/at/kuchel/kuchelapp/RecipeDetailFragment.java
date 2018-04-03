package at.kuchel.kuchelapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.controller.RecipeApi;
import at.kuchel.kuchelapp.service.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    /**
     * The dummy content this fragment is presenting.
     */
//    private DummyContent.RecipeItem mItem;
    private Recipe recipe;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            handleAsyncCallAndRedirect(this.getActivity(), getArguments().getString(ARG_ITEM_ID));
        }
    }

    private void handleAsyncCallAndRedirect(final FragmentActivity activity, String id) {
        Call<Recipe> call = ServiceGenerator.createService(RecipeApi.class).getRecipe(id);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                recipe = response.body();

            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (recipe != null) {
            //todo find the purpose for this
//            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(recipeEntity.getName());
        }

        return rootView;
    }
}
