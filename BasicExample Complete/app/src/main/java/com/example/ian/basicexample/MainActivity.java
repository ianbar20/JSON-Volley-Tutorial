package com.example.ian.basicexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    // Will show the string "data" that holds the results
    TextView results;

    // URL of object to be parsed
    String JsonURL = "https://raw.githubusercontent.com/ianbar20/JSON-with-Volley/master/JSON-Examples/Basic-Example.JSON";

    // This string will hold the results
    String data = "";

    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        //casts results into the TextView found within the main layout XML with id jsonData
        results = (TextView) findViewById(R.id.jsonData);

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ja = response.getJSONArray("colorArray");

                            // Iterates through the JSON Array getting Objects and adding them
                            // to the list view until there are no more objects in the array
                            for (int i = 0; i < ja.length(); i++) {
                                //gets each JSON object within the JSON array
                                JSONObject jsonObject = ja.getJSONObject(i);

                                // Retrieves the string labeled "colorName" and "hexValue",
                                // and converts them into javascript objects
                                String color = jsonObject.getString("colorName");
                                String hex = jsonObject.getString("hexValue");

                                // Adds strings from the current object to the data string
                                //spacing is included at the end to separate the results from
                                //one another
                                data += "Color Number " + (i + 1) + "\nColor Name: " + color +
                                        "\nHex Value : " + hex + "\n\n\n";
                            }
                            // Adds the data string to the TextView "results"
                            results.setText(data);
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }
}
