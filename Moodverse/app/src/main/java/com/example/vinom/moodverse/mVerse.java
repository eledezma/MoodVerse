package com.example.vinom.moodverse;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class mVerse extends ActionBarActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://api.androidhive.info/contacts/";

    private static final String TAG_PASSAGE = "passage";
    private static final String TAG_PREVIEW = "preview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.httpex);

        new getVerse().execute();
    }


    private class getVerse extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(mVerse.this);
        InputStream inputStream = null;
        String result = "";

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url_select = "http://api.biblia.com/v1/bible/search/LEB.txt?query=bread&mode=verse&start=0&limit=20&key=5cf1428ff950a05d6d99b23f0e8329b9";

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                // Set up HTTP post

                // HttpClient is more then less deprecated. Need to change to URLConnection
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url_select);
                httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
                inputStream = httpEntity.getContent();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (ClientProtocolException e2) {
                Log.e("ClientProtocolException", e2.toString());
                e2.printStackTrace();
            } catch (IllegalStateException e3) {
                Log.e("IllegalStateException", e3.toString());
                e3.printStackTrace();
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
            }
            // Convert response to string using String Builder
            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                inputStream.close();
                result = sBuilder.toString();

            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            //parse JSON data
            try {

                JSONObject jObject = new JSONObject(result);

                JSONArray jArray = jObject.getJSONArray("results");
//                for(int i=0; i < jArray.length(); i++) {

                
                JSONObject Jason = jArray.getJSONObject(0);


                    String passageString = Jason.getString("preview");
                    String verseString = Jason.getString("preview");

                TextView passageT = (TextView) findViewById(R.id.passage);
                TextView verseT = (TextView) findViewById(R.id.verse);

                passageT.setText(passageString);
                verseT.setText(verseString);

//                /} // End Loop
                this.progressDialog.dismiss();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        } // protected void onPostExecute(Void v)
    } //class MyAsyncTask extends AsyncTask<String, String, Void>






















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_m_verse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
