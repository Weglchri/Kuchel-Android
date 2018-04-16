package at.kuchel.kuchelapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.Objects;

import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.dto.BitmapImage;
import at.kuchel.kuchelapp.service.GlobalParamService;
import at.kuchel.kuchelapp.service.ImageService;
import at.kuchel.kuchelapp.service.RecipeServiceDb;
import at.kuchel.kuchelapp.service.utils.PermissionHandler;

import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.USERNAME;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AbstractRecipeActivity {

    private RecipeServiceDb recipeServiceDb = new RecipeServiceDb(this);
    private final PermissionHandler permissionHandler = new PermissionHandler();

    private final Activity recipeListActivity = this;

    private String recipeId;
    private Recipe recipe;
    private FloatingActionButton cameraButton;

    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detailed);
        setSupportActionBar(toolbar);

        recipeId = getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.cameraButton = (FloatingActionButton) this.findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissionHandler.askForPermissionCamera(recipeListActivity)) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            }
        });
        recipeServiceDb.retrieveRecipes();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
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
       // overridePendingTransition(R.anim.nothing, R.anim.slide_out);
    }

    @Override
    public void handleRecipesFromRest(List<Recipe> recipes) {}

    @Override
    public void handleRecipesFromDb(List<Recipe> recipes) {
        String recipeId = getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID);

        for (Recipe recipe : recipes) {
            if (String.valueOf(recipe.getId()).equals(recipeId)) {
                this.recipe = recipe;
                if (recipe.getImages().size() > 0) {
                    recipeServiceDb.loadImages(recipe.getImages().get(0).getId());
                } else {
                    handleImageResponse(null);
                }
            }
        }
    }

    @Override
    public void handleImageResponse(BitmapImage bitmapImage) {
        Bundle arguments = new Bundle();
        arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID));

        RecipeDetailFragment fragment = new RecipeDetailFragment();
        if (bitmapImage != null) {
            BitmapDrawable background = new BitmapDrawable(getResources(), bitmapImage.getImage());
            AppBarLayout barLayout = (AppBarLayout) findViewById(R.id.app_bar_detailed);
            barLayout.setBackground(background);
            fragment.setImage(bitmapImage.getImage());

        }

        fragment.setRecipe(recipe);
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().add(R.id.recipe_detail_container, fragment).commit();

        if(GlobalParamService.isUserSet() && GlobalParamService.retrieveGlobalParam(USERNAME).getValue().equals(recipe.getUsername())) {
            cameraButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void callSnackBarPopup(String message) {}

}

