package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BuatAkunFragment extends Fragment{
    public BuatAkunFragment(){}

    private EditText usernameField, passwordField, emailField;
    String user="",pass="", email="",id;
    String role = "0";
    InputStream is=null;
    String result=null;
    String line=null;
    int code;
    private String url1 = "http://ppl-c04.cs.ui.ac.id/index.php/mengelolaAkunController/create";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buat_akun, container, false);

        final RadioGroup radioAkun = (RadioGroup) rootView.findViewById(R.id.radio_group);

        radioAkun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_kakak:
                        // 'Kakak Asuh' checked
                        //TODO
                        role = "1";
                        break;
                    case R.id.radio_adik:
                        // 'Adik Asuh' checked
                        //TODO
                        role = "2";
                        break;
                }
            }
        });

        usernameField = (EditText)rootView.findViewById(R.id.username);
        passwordField = (EditText)rootView.findViewById(R.id.password);
        emailField = (EditText)rootView.findViewById(R.id.email);


        Button btnBuat = (Button) rootView.findViewById(R.id.btn_buat);
        btnBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat Akun ngapain
//                TODO
                user = usernameField.getText().toString();
                pass = passwordField.getText().toString();
                email = emailField.getText().toString();
                //debug
                System.out.println(user);
                System.out.println(pass);
                System.out.println(email);


                if(user.equals("")||pass.equals("")||email.equals("")||role.equals("0")) {
                    Toast.makeText(getActivity(), "Ada yang belum terisi",
                            Toast.LENGTH_LONG).show();
                }
                else if(!email.matches(emailPattern)){
                    Toast.makeText(getActivity(), "Alamat email tidak valid",
                            Toast.LENGTH_LONG).show();
                }
                else if (usernameField.getText().toString().contains(" ")||passwordField.getText().toString().contains(" ")) {
                    Toast.makeText(getActivity(), "Spasi tidak diperbolehkan", Toast.LENGTH_LONG).show();
                }
                else{
                    new insertTask().execute("");
                    Toast.makeText(getActivity(), "Akun Berhasil Dibuat",
                            Toast.LENGTH_LONG).show();
                    usernameField.setText("");
                    passwordField.setText("");
                    emailField.setText("");
                    radioAkun.clearCheck();
                }
            }
        });

        return rootView;
    }


    public String insert() {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("username", user));
            nameValuePairs.add(new BasicNameValuePair("password", pass));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("role", role));
            //debug
            System.out.println(nameValuePairs);

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url1);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                //debug
                System.out.println(is);
                Log.e("pass 1", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
                Toast.makeText(getActivity(), "Invalid IP Address",
                        Toast.LENGTH_LONG).show();

            }

            try {
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                //debug

                Log.e("pass 2", "connection success ");
                System.out.println(result);
            } catch (Exception e) {
                Log.e("Fail 2", e.toString());

            }

          return result;
    }

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



