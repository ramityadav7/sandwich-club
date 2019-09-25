package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import com.udacity.sandwichclub.utils.ParseUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mIngredientsIv;
    private LinearLayout mOriginLl;
    private TextView mOriginTv;
    private LinearLayout mAlsoKnownLl;
    private TextView mAlsoKnownTv;
    private LinearLayout mIngredientLl;
    private TextView mIngredientTv;
    private LinearLayout mDescriptionLl;
    private TextView mDescriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        initializeView();

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);


        setTitle(sandwich.getMainName());
    }

    private void initializeView() {
        mIngredientsIv = findViewById(R.id.image_iv);
        mOriginLl = findViewById(R.id.origin_ll);
        mOriginTv = findViewById(R.id.origin_tv);
        mAlsoKnownLl = findViewById(R.id.also_known_ll);
        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mIngredientLl = findViewById(R.id.ingredients_ll);
        mIngredientTv = findViewById(R.id.ingredients_tv);
        mDescriptionLl = findViewById(R.id.description_ll);
        mDescriptionTv = findViewById(R.id.description_tv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        if(sandwich != null) {
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(mIngredientsIv);


            populateView(mOriginLl, mOriginTv, sandwich.getPlaceOfOrigin());
            populateView(mAlsoKnownLl, mAlsoKnownTv, ParseUtils.parseStringArray(sandwich.getAlsoKnownAs()));
            populateView(mIngredientLl, mIngredientTv, ParseUtils.parseStringArray(sandwich.getIngredients()));
            populateView(mDescriptionLl, mDescriptionTv, sandwich.getDescription());

        }
    }

    private void populateView(LinearLayout linearLayout, TextView textView, String value) {
        if(!TextUtils.isEmpty(value)) {
            textView.setText(value);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }
}
