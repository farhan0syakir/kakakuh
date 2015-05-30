package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.HeaderTugasListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;
import com.kakakuh.c4ppl.kakakuh.controller.SectionedListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.TugasListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.TugasListKakakAdapter;
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class TugasFragment extends Fragment{
    private ArrayList<Tugas> listTugas;
    private ArrayList<TugasListAdapter> adapter;
    private SectionedListAdapter<HeaderTugasListAdapter,TugasListAdapter> sectionAdapter;
    static private Preferensi preferensi;

    InputStream is=null;
    String result=null;
    String line=null;
    JSONArray android = null;

    private String usernameAdik;
    ArrayList<Tugas> queries = new ArrayList<>();

    String url="http://ppl-c04.cs.ui.ac.id/index.php/lihatDaftarTugas";

    private ListView mList;

    public TugasFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        adapter = new ArrayList<>();
        sectionAdapter = new SectionedListAdapter<>(getActivity(), new HeaderTugasListAdapter(getActivity()));

        //mengambil username sendiri
        preferensi = new Preferensi(getActivity().getApplicationContext());
        usernameAdik = preferensi.getUsername();

        // TODO query kolom | idKategori | TextKategori | Deadline | JOIN by idKategory | idTugas | PoinTugas |status pengerjaaan | sorted by idKategory
        //kolom yg dibutuhkan | Kategori | Deadline | idTugas | status pengerjaaan |
        //ini HARDCODED
        //mau tau convert milis to Date? cek http://www.ruddwire.com/handy-code/date-to-millisecond-calculators/
//        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"1","Kerjakan Mock Up",false));
//        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"2","Bikin PPT",true));
//        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"3","Bikin UI",true));
//        queries.add(new Tugas("2","DPP",new Date(new Long("1428345600000")),"4","Sudah lewat deadline",false));
//        queries.add(new Tugas("2","DPP",new Date(new Long("1428345600000")),"5","Sudah lewat deadline",true));
        mList = (ListView) rootView.findViewById(R.id.list_generic);
        new retrieveTask().execute();


        return rootView;
    }

    private static long parseDate(String text) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(text).getTime();
    }

    public String retrieve() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("useradik", usernameAdik));
        //debug
        System.out.println(nameValuePairs);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            //debug
            System.out.println(is);
            Log.e("pass 1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getActivity().getApplicationContext(), "Invalid IP Address",
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

    class retrieveTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = retrieve();
            return hasil ;
        }



        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                android = json.getJSONArray("data");
                if(android.length()==0){
                    queries.add(new Tugas("1", "Tidak ada kategori", new Date(new Long(System.currentTimeMillis())), "1", "tidak ada tugas", true));
                }else{
                    for(int i = 0; i < android.length(); i++){
                        JSONObject c = android.getJSONObject(i);

                        // TODO query kolom | idKategori | TextKategori | Deadline | JOIN by idKategory | idTugas | PoinTugas |status pengerjaaan | sorted by idKategory
                        String idKategori = c.getString("id");
                        String textKategori = c.getString("nama");
                        String deadline = c.getString("deadline");
                        Long deadEnd = parseDate(deadline);
                        String idTugas = c.getString("id_tugas");
                        String poinTugas = c.getString("nama_tugas");
                        String status = c.getString("status");
                        if(status.equals("0")){
                            queries.add(new Tugas(idKategori, textKategori, new Date(new Long(deadEnd)), idTugas, poinTugas, false));
                        }else{
                            queries.add(new Tugas(idKategori, textKategori, new Date(new Long(deadEnd)), idTugas, poinTugas, true));
                        }
                    }
                }
                addList(queries);

                mList.setAdapter(sectionAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        private void addList(ArrayList<Tugas> temps){
            Tugas currentKategori = queries.get(0);
            ArrayList<Tugas> tugases = new ArrayList<>();
            Tugas current;
            for(int i = 0; i < queries.size() ; i++) {
                current = queries.get(i);
                if(!currentKategori.getIdKategori().equals(current.getIdKategori())) {
                    sectionAdapter.addSection(new Tugas(
                                    currentKategori.getIdKategori(),
                                    currentKategori.getTextKategori(),
                                    currentKategori.getDeadline()),
                            new TugasListAdapter(getActivity(),tugases));
                    currentKategori = current;
                    tugases = new ArrayList<>();
                    tugases.add(current);
                } else {
                    tugases.add(current);
                }
            }
            sectionAdapter.addSection(new Tugas(
                            currentKategori.getIdKategori(),
                            currentKategori.getTextKategori(),
                            currentKategori.getDeadline()),
                    new TugasListAdapter(getActivity(),tugases));
        }
    }
}
