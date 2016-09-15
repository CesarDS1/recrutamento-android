package com.test.cesar.palomitasmovile.Utils;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.test.cesar.palomitasmovile.models.Episode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Help to get the season episodes and rating
 * Created by Cesar on 14/09/2016.
 */
public class NetworkManager {

    private static NetworkManager instance = null;

    String url = "https://api.trakt.tv/shows/game-of-thrones/seasons/1";
    String seasonRate="https://api.trakt.tv/shows/game-of-thrones/ratings";
    String clientId="79bb91d19277986ab88c65013ea426971acfa5b0f4f09518191a7af2feee2ec0";

    public RequestQueue requestQueue;
    private ArrayList<Episode> myEpisodes;

    private NetworkManager(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());

    }

    public static synchronized NetworkManager getInstance(Context context)
    {
        if (null == instance)
            instance = new NetworkManager(context);
        return instance;
    }

    //this is so you don't need to pass context each time
    public static synchronized NetworkManager getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void getRatingSeason(final CustomRequestListener<String> listener)
    {
        JsonObjectRequest jsonRequestRating = new JsonObjectRequest
                (Request.Method.GET, seasonRate, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String rating = response.getString("rating");
                            DecimalFormat df=new DecimalFormat("0.00");
                            String formateRating = df.format(Double.parseDouble(rating));

                            listener.onSuccess(formateRating);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //ratingTv.setText("-");

                        if( error instanceof NetworkError) {
                            listener.onError(error.getMessage());

                        } else if( error instanceof ServerError) {
                            listener.onError(error.getMessage());

                        } else if( error instanceof AuthFailureError) {
                            listener.onError(error.getMessage());

                        } else if( error instanceof ParseError) {
                            listener.onError(error.getMessage());

                        } else if( error instanceof NoConnectionError) {
                            listener.onError(error.getMessage());

                        } else if( error instanceof TimeoutError) {
                            listener.onError(error.getMessage());
                        }
                    }
                }
                ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("trakt-api-version", "2");
                params.put("trakt-api-key", clientId);

                return params;
            }
        };
        requestQueue.add(jsonRequestRating);
    }

    public void getEpisodesFromSeason( final CustomRequestListener<ArrayList<Episode>> listener)
    {

        myEpisodes=new ArrayList<>();

        JsonArrayRequest jsonRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for(int n = 0; n < response.length(); n++)
                            {
                                JSONObject responseOne = response.getJSONObject(n);

                                String title = responseOne.getString("title");

                                int number= responseOne.getInt("number");

                                System.out.println("Title: "+title+"\nNumber: "+number);

                               myEpisodes.add(new Episode(number, title));
                            }
                            listener.onSuccess(myEpisodes);


                        } catch (JSONException e) {

                            e.printStackTrace();

                            listener.onError("Please connect to the network");
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        if( error instanceof NetworkError) {

                            listener.onError(error.getMessage());

                        } else if( error instanceof ServerError) {
                            listener.onError(error.getMessage());

                        } else if( error instanceof AuthFailureError) {
                            listener.onError(error.getMessage());

                        } else if( error instanceof ParseError) {
                            listener.onError(error.getMessage());

                        } else if( error instanceof NoConnectionError) {
                            listener.onError(error.getMessage());

                        } else if( error instanceof TimeoutError) {
                            listener.onError(error.getMessage());
                        }
                    }
                }
                ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("trakt-api-version", "2");
                params.put("trakt-api-key", clientId);

                return params;
            }
        };


        requestQueue.add(jsonRequest);
    }
}
