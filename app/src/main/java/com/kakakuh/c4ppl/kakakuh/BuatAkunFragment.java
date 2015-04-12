package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class BuatAkunFragment extends Fragment{
    public BuatAkunFragment(){}

    private EditText usernameField, passwordField;
    String user,pass,id;
    int role = 0;
    InputStream is=null;
    String result=null;
    String line=null;
    int code;
    private String url1 = "http://ppl-c04.cs.ui.ac.id/index.php/buatAkunController";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buat_akun, container, false);

        RadioGroup radioAkun = (RadioGroup) rootView.findViewById(R.id.radio_group);

        radioAkun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_kakak:
                        // 'Kakak Asuh' checked
                        //TODO
                        role = 1;
                        break;
                    case R.id.radio_adik:
                        // 'Adik Asuh' checked
                        //TODO
                        role = 2;
                        break;
                }
            }
        });

        usernameField = (EditText)rootView.findViewById(R.id.username);
        passwordField = (EditText)rootView.findViewById(R.id.password);


        Button btnBuat = (Button) rootView.findViewById(R.id.btn_buat);
        btnBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat Akun ngapain
//                TODO
                user = usernameField.getText().toString();
                pass = passwordField.getText().toString();
                //debug
                System.out.println(user);
                System.out.println(pass);
//                new loadData().execute();





//                insert();






                new insertTask().execute("");
            }
        });

        return rootView;
    }


    public String insert()
    {
        String checker = "OK";
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("username",user));
        nameValuePairs.add(new BasicNameValuePair("password",pass));
        nameValuePairs.add(new BasicNameValuePair("role",Integer.toString(role)));
        //debug
        System.out.println(nameValuePairs);

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url1);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            //debug
            System.out.println(is);
            Log.e("pass 1", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getActivity(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
            checker = "NO";
        }

        try
        {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            //debug

            Log.e("pass 2", "connection success ");
            System.out.println(result);
        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
            checker = "NO";
        }

//        try
//        {
//            JSONObject json_data = new JSONObject(result);
//            code=(json_data.getInt("code"));
//
//            if(code==1)
//            {
//                Toast.makeText(getActivity(), "Inserted Successfully",
//                        Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                Toast.makeText(getActivity(), "Sorry, Try Again",
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//        catch(Exception e)
//        {
//            Log.e("Fail 3", e.toString());
//            checker = "NO";
//        }
        return result;
    }








//    class loadData extends AsyncTask<String, Integer, String> {
//        private StringBuilder sb;
//        private ProgressDialog pr;
//        private HttpResponse req;
//        private InputStream is;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Toast.makeText(getActivity(), "Start", Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        protected String doInBackground(String... arg0) {
//
//            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//
//            nameValuePairs.add(new BasicNameValuePair("username",user));
//            nameValuePairs.add(new BasicNameValuePair("password",pass));
//            nameValuePairs.add(new BasicNameValuePair("role",Integer.toString(1)));
//
//            try
//            {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(url1);
//                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                HttpResponse response = httpclient.execute(httppost);
//                HttpEntity entity = response.getEntity();
//                is = entity.getContent();
//                InputStreamReader ireader = new InputStreamReader(is);
//                BufferedReader bf = new BufferedReader(ireader);
//                sb = new StringBuilder();
//                String line = null;
//                while ((line = bf.readLine()) != null) {
//                    sb.append(line);
//                }
//                Log.e("pass 1", "connection success ");
//            }
//            catch(Exception e)
//            {
//                Log.e("Fail 1", e.toString());
//                Toast.makeText(getActivity(), "Invalid IP Address",
//                        Toast.LENGTH_LONG).show();
//            }
//            return id;
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            pr.dismiss();
//            Toast.makeText(getActivity(), "End", Toast.LENGTH_LONG).show();
//
//        }
//    }








    class insertTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = insert();
            return hasil ;
        }



        protected void onPostExecute(String result) {
            if(result.equals("OK"))
                System.out.print("ea");
            //after background is done, use this to show or hide dialogs
        }
    }
}



