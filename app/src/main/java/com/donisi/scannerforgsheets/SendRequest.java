package com.donisi.scannerforgsheets;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class SendRequest extends AsyncTask<String, Void, String> {

    private String data;
    private Context context;

    SendRequest(String data, Context context) {
        this.data = data;
        this.context = context;
    }

    protected void onPreExecute(){}

    protected String doInBackground(String... arg0) {
        try{
            URL url = new URL("https://script.google.com/macros/s/AKfycbxlHzpZR_3gIQ9L_vGTf-GO8mpako96N3K0CjHbmQ/exec");  //Web service de google Sheets
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("sdata",getData());
            Log.i("params",postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();
            Log.i("responseCode",responseCode+" "+  HttpsURLConnection.HTTP_OK);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();

                Log.i("SB true",sb.toString());
                return sb.toString();
            } else {
                Log.i("SB ELSE", "false: "+responseCode);
                return "false : " + responseCode;
            }
        } catch(Exception e){
            return "Exception: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(getContext(), result,
                Toast.LENGTH_LONG).show();
    }

    private String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    private void setData(String data){ this.data = data; }

    private String getData(){ return this.data; }

    private Context getContext(){ return this.context; }
}
