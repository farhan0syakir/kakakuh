package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by Anas on 5/24/2015.
 */
public abstract class KakakuhBaseJSONParserAsyncTask extends AsyncTask<String, String, JSONObject> {
    protected ProgressDialog pDialog;
    protected String url;
    protected Context context;

    public KakakuhBaseJSONParserAsyncTask(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Ambil Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        JSONParser jParser = new JSONParser();
        // Getting JSON from URL
        JSONObject json = jParser.getJSONFromUrl(url);
        return json;
    }
}
