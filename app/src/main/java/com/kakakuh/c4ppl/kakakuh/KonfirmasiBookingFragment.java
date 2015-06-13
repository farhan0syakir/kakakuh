package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.BookingListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.ImageConverter;
import com.kakakuh.c4ppl.kakakuh.controller.KakakuhBaseJSONParserAsyncTask;
import com.kakakuh.c4ppl.kakakuh.controller.Kalender;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;
import com.kakakuh.c4ppl.kakakuh.model.BookingListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Anas on 4/2/2015.
 */
public class KonfirmasiBookingFragment extends Fragment{
    private ListView mListKonfirmasi;
    private ArrayList<BookingListItem> konfirmasiListItems;
    private BookingListAdapter adapter;
    private ListView mListBooking;
    private JSONArray jsonArray = null;
    private String user;
    private Bitmap decodedByte;
    private String dateTxt;

    private Preferensi preferensi;


    public KonfirmasiBookingFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);
        mListBooking = (ListView) rootView.findViewById(R.id.list_generic);

        mListBooking.setItemsCanFocus(true);
        mListBooking.setFocusable(false);
        mListBooking.setFocusableInTouchMode(false);
        mListBooking.setClickable(false);

        preferensi = new Preferensi(getActivity());
        user = preferensi.getUsername();
        new JSONParser(getActivity(),"http://ppl-c04.cs.ui.ac.id/index.php/bookingController?userkakak="+user).execute();

//        Date date = new Date();
//        konfirmasiListItems.add(new BookingListItem("kakak","adik",1,true,date,date,date));
//        konfirmasiListItems.add(new BookingListItem("kakak","adik",1,true,date,date,date));
//        konfirmasiListItems.add(new BookingListItem("kakak","adik",1,true,date,date,date));
//
//        adapter = new BookingListAdapter(getActivity(), konfirmasiListItems);
//        System.out.println("ini adapter "+adapter);
//        System.out.println("ini adapter "+konfirmasiListItems);
//        mListBooking.setAdapter(adapter);
        return rootView;
    }

    class JSONParser extends KakakuhBaseJSONParserAsyncTask {

        public JSONParser(Context context, String url) {
            super(context, url);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {

                konfirmasiListItems = new ArrayList<>();
                // Getting JSON Array from URL
                jsonArray = json.getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    String useradik = c.getString("useradik");
                    String userkakak = c.getString("user_id");
                    String idjadwal = c.getString("id_jadwal");
                    String bookstatus = c.getString("book_status");
                    decodedByte = ImageConverter.convertStringToBitmap(c.getString("img"));
                    String txttimestamp = c.getString("timestamp");
                    String txtstart = c.getString("start_date");
                    String txtend = c.getString("end_date");
                    String myDateSqlFormat = "yyyy-MM-dd HH:mm:ss";

                    Date parsedStartDate = new SimpleDateFormat(myDateSqlFormat).parse(txtstart);
                    Date parsedEndDate = new SimpleDateFormat(myDateSqlFormat).parse(txtend);

                    Calendar startTime = Calendar.getInstance();
                    startTime.setTime(parsedStartDate);
                    Calendar endTime = Calendar.getInstance();
                    endTime.setTime(parsedEndDate);
                    String[] timeStart = Kalender.getTimeBooking(startTime);
                    String hariStart = timeStart[0];
                    String waktuStart = timeStart[1];
                    String[] timeEnd = Kalender.getTimeBooking(endTime);
                    String hariEnd = timeEnd[0];
                    String waktuEnd = timeEnd[1];

                    if(startTime.get(Calendar.YEAR)==endTime.get(Calendar.YEAR)&&startTime.get(Calendar.DAY_OF_YEAR)==endTime.get(Calendar.DAY_OF_YEAR)){
                        dateTxt = "Ingin melakukan booking pada " + hariStart + " di jam " + waktuStart + "-"+ waktuEnd;
                    }
                    else{
                        dateTxt = "Ingin melakukan booking pada " + hariStart + " di jam " + waktuStart + " sampai" + hariEnd + " di jam " + waktuEnd ;
                    }
//                    String datetxt = "Ingin melakukan booking pada hari" + txtstart + " sampai" + txtend;
                    BookingListItem booking = new BookingListItem(useradik,userkakak,Integer.parseInt(idjadwal), txttimestamp, dateTxt,decodedByte);
                    if(bookstatus.equals("2")){
                        konfirmasiListItems.add(booking);
                    }
                    else{

                    }
                }
                adapter = new BookingListAdapter(getActivity(), konfirmasiListItems);
                mListBooking.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


}
