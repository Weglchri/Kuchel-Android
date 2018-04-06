package at.kuchel.kuchelapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.dto.BitmapImage;
import at.kuchel.kuchelapp.service.ImageService;
import at.kuchel.kuchelapp.service.RecipeServiceDb;
import at.kuchel.kuchelapp.service.utils.PermissionHandler;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AbstractRecipeActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_REQUEST_PERMISSION = 1800;
    private String recipeId;
    private Activity recipeListActivity;
    private final PermissionHandler permissionHandler = new PermissionHandler();
    private RecipeServiceDb recipeServiceDb = new RecipeServiceDb(this);
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        this.recipeListActivity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detailed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html


        FloatingActionButton cameraButton = (FloatingActionButton) this.findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (permissionHandler.askForPermissionCamera(recipeListActivity) == true) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            }
        });

        recipeServiceDb.retrieveRecipes();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageService imageService = new ImageService();
            imageService.uploadImage(photo, recipeId);

            //just background related
            BitmapDrawable background = new BitmapDrawable(getResources(), photo);
            AppBarLayout barLayout = (AppBarLayout) findViewById(R.id.app_bar_detailed);
            barLayout.setBackground(background);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void handleRecipesFromRest(List<Recipe> recipes) {

    }

    @Override
    public void handleRecipesFromDb(List<Recipe> recipes) {

        // Create the detail fragment and add it to the activity
        // using a fragment transaction.
//        Bundle arguments = new Bundle();
//        arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID));
        String recipeId = getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID);


        for (Recipe recipe : recipes) {
            if (String.valueOf(recipe.getId()).equals(recipeId)) {
                this.recipe = recipe;
                if (recipe.getImages().size() > 0) {
                    recipeServiceDb.loadImages(recipe.getImages().get(0).getId());
                } else {
                    //todo call direct
                }

//                arguments.putParcelable("recipe", recipe);
//                RecipeDetailFragment fragment = new RecipeDetailFragment();
//                fragment.setArguments(arguments);
//                getSupportFragmentManager().beginTransaction().add(R.id.recipe_detail_container, fragment).commit();
            }
        }
    }

    @Override
    public void handleImageResponse(BitmapImage bitmapImage) {
        Bundle arguments = new Bundle();
        arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID));
        arguments.putParcelable("recipe", recipe);
        arguments.putParcelable("bitmap", bitmapImage.getImage());


        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().add(R.id.recipe_detail_container, fragment).commit();
    }
}

