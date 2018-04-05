package at.kuchel.kuchelapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import at.kuchel.kuchelapp.service.ImageService;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private String recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

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
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID));
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.recipe_detail_container, fragment).commit();
            recipeId = getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID);
        }

        FloatingActionButton cameraButton = (FloatingActionButton) this.findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageService imageService = new ImageService();
            imageService.uploadImage(photo,recipeId);
            BitmapDrawable background = new BitmapDrawable(getResources(), photo);

            AppBarLayout barLayout = (AppBarLayout) findViewById(R.id.app_bar_detailed);
            barLayout.setBackground(background);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
