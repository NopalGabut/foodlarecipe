package com.example.foodlarecipe;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodlarecipe.ItemAdapter;
import com.example.foodlarecipe.ItemData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchFood extends AsyncTask<String,Void,String> {
    private ArrayList<ItemData> values;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private Context context;

    public FetchFood(ArrayList<ItemData>values,ItemAdapter itemAdapter,RecyclerView recyclerView,Context context){
        this.values=values;
        this.itemAdapter=itemAdapter;
        this.recyclerView=recyclerView;
        this.context=context;
    }
    @Override
    protected String doInBackground(String... strings) {
        String queryString=strings[0];
        BufferedReader reader=null;
        String foodJSONString="";
        String FOOD_URL="https://www.themealdb.com/api/json/v1/1/search.php";
        String QUERY_PARAM="s";
        HttpURLConnection urlConnection=null;
        Uri builtUri=Uri.parse(FOOD_URL).buildUpon().appendQueryParameter(QUERY_PARAM, queryString).build();
        URL requestURL= null;
        try {
            requestURL = new URL(builtUri.toString());
            urlConnection=(HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream=urlConnection.getInputStream();
            StringBuilder builder=new StringBuilder();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine())!=null){
                builder.append(line+"\n");
            }
            if (builder.length()==0){
                return null;
            }
            foodJSONString=builder.toString();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return foodJSONString;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        values=new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray mealsArray = jsonObject.getJSONArray("meals");
            String name, asl, thumbnail;
            for (int i = 0; i < mealsArray.length(); i++) {
                JSONObject meal = mealsArray.getJSONObject(i);
                name = meal.getString("strMeal");
                asl = meal.getString("strArea");
                thumbnail = meal.getString("strMealThumb");
                ItemData itemData = new ItemData();
                itemData.itemName = name;
                itemData.itemAsl = asl;
                itemData.itemThumbnail = thumbnail;
                values.add(itemData);
            }
        } catch (JSONException e) {
            // Tangani kesalahan
            e.printStackTrace();
        }
        this.itemAdapter = new ItemAdapter(context, values);
        this.recyclerView.setAdapter(this.itemAdapter);
    }
}
