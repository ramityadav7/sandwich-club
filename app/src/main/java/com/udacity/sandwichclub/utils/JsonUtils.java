package com.udacity.sandwichclub.utils;

import android.text.TextUtils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_OBJECT = "name";
    private static final String MAIN_NAME_OBJECT = "mainName";
    private static final String ALSO_KNOWN_AS_OBJECT = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_OBJECT = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        if(!TextUtils.isEmpty(json)) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                sandwich = new Sandwich();

                parseNameObject(jsonObject, sandwich);

                sandwich.setPlaceOfOrigin(jsonObject.optString(PLACE_OF_ORIGIN_OBJECT));
                sandwich.setDescription(jsonObject.optString(DESCRIPTION));
                sandwich.setImage(jsonObject.optString(IMAGE));

                parseIngredient(jsonObject, sandwich);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return sandwich;
    }

    private static void parseNameObject(JSONObject jsonObject, Sandwich sandwich) throws JSONException {
        JSONObject nameObject = jsonObject.optJSONObject(NAME_OBJECT);
        if(nameObject != null) {
            sandwich.setMainName(nameObject.optString(MAIN_NAME_OBJECT));

            JSONArray jsonArray = nameObject.optJSONArray(ALSO_KNOWN_AS_OBJECT);
            if(jsonArray != null) {
                List<String> alsoKnownAs = new ArrayList<>();
                for(int ite = 0; ite < jsonArray.length(); ite++) {
                    alsoKnownAs.add(jsonArray.optString(ite));
                }
                sandwich.setAlsoKnownAs(alsoKnownAs);
            }
        }
    }

    private static void parseIngredient(JSONObject jsonObject, Sandwich sandwich) {
        JSONArray jsonArray = jsonObject.optJSONArray(INGREDIENTS);
        if (jsonArray != null) {
            List<String> ingredients = new ArrayList<>();
            for(int ite = 0; ite < jsonArray.length(); ite++) {
                ingredients.add(jsonArray.optString(ite));
            }
            sandwich.setIngredients(ingredients);
        }
    }
}
