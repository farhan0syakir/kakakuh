package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.kakakuh.c4ppl.kakakuh.controller.KakakuhBaseJSONParserAsyncTask;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Anas on 4/2/2015.
 */
public class JadwalKakakFragment extends Fragment
        implements WeekView.MonthChangeListener,WeekView.EventClickListener,WeekView.EventLongPressListener{
    Spinner monthSpinner;
    WeekView mWeekView;
    TextView tahun;
    Button button,goToTodayBtn,tambahJadwalBtn;
    String id,user,judul,startDate,tahunMulai,bulanMulai,tanggalMulai,jamMulai,menitMulai,detikMulai,endDate,tahunSelesai,bulanSelesai,tanggalSelesai,jamSelesai,menitSelesai,detikSelesai;
    Intent nextScreen;


    List<WeekViewEvent> events=new ArrayList<>();
    JSONArray jsonArray = null;
    String myDateSqlFormat = "yyyy-MM-dd HH:mm:ss";
    String myDateFormat = "E, dd-MM-yy HH:mm";
    String statusBook = null;


    public JadwalKakakFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jadwal_kakak, container, false);
        Calendar today = Calendar.getInstance();

        Preferensi pref = new Preferensi(getActivity());
//        setViewMode("3 hari");

        mWeekView = (WeekView) rootView.findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

//        yearpicker = (NumberPicker)rootView.findViewById(R.id.tahun);
//        yearpicker.setValue(Calendar.getInstance().get(Calendar.YEAR));
        goToTodayBtn = (Button) rootView.findViewById(R.id.refresh);


        Calendar cal = Calendar.getInstance();
        addListener();
        setViewMode(pref.getPengaturanJadwal());
//        onMonthChange(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH));
        return rootView;
    }
    public void addListener() {
        goToTodayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeekView.goToToday();
            }
        });
    }

    public void setViewMode(String type){
        if (type.equals("3 hari")) {
            mWeekView.setNumberOfVisibleDays(3);

            // Lets change some dimensions to best fit the view.
            mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
            mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        }else

        if (type.equals("7 hari")) {
            mWeekView.setNumberOfVisibleDays(7);

            //Lets change some dimensions to best fit the view.
            mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
            mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
            mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        }

    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF rectF) {
        nextScreen = new Intent(getActivity().getApplicationContext(), DetailJadwalByAdikActivity.class);
        nextScreen.putExtra("id", event.getId());
        nextScreen.putExtra("judul", event.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat(myDateFormat);
        nextScreen.putExtra("start",""+dateFormat.format(event.getStartTime().getTime()));
        nextScreen.putExtra("end",""+dateFormat.format(event.getEndTime().getTime()));
        nextScreen.putExtra("color",event.getColor());
        startActivity(nextScreen);
    }

    @Override
    public void onEventLongPress(WeekViewEvent weekViewEvent, RectF rectF) {

    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> tempEvents=null;
        try{
//            new JSONParse().execute().get();
            Preferensi pref = new Preferensi(getActivity());
            String tempYear = ""+newYear;
            String tempMonth = ""+(newMonth-1);
//            monthSpinner.setSelection(newMonth-1);

            //System.out.print("ayo cek sizenya nih" + events.size());
            String myUrl = "http://ppl-c04.cs.ui.ac.id/index.php/jadwalController/retrieveJadwalKakak?useradik="+pref.getUsername()+"&year="+tempYear+"&month="+ tempMonth;
            new JSONParse(getActivity(),myUrl).execute().get();
            System.out.println("my url is " + myUrl + " size after asycn event = " + events.size());
            tempEvents= new ArrayList<>(events);
            events = new ArrayList<>();
            //System.out.println("myurl "+myUrl);
            //for(int i =0;i<events.size();i++){
            //    System.out.println("onmonthcahge = "+events.get(i).getName()+" ");
            //}
        }catch (Exception e){
            System.out.println(e);
        }
        mWeekView.notifyDatasetChanged();
        return tempEvents;

    }

    class JSONParse extends KakakuhBaseJSONParserAsyncTask {

        private String url = "";
        private int newMonth,newYear;
        public JSONParse(Context context, String url) {
            super(context, url);
            this.url=url;
        }


        @Override
        protected void onPostExecute(JSONObject json) {
            events = new ArrayList<>();
            try {
                // Getting JSON Array from URL
                jsonArray= json.getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    id = c.getString("id_jadwal");
                    user = c.getString("user_id");
                    judul = c.getString("title");
                    statusBook = c.getString("status_book");
                    //Date format 2015-05-18 09:33:00
                    startDate = c.getString("start_date");
                    endDate = c.getString("end_date");

                    try {
                        Date parsedStartDate = null, parsedEndDate = null;
                        parsedStartDate = new SimpleDateFormat(myDateSqlFormat).parse(startDate);
                        parsedEndDate = new SimpleDateFormat(myDateSqlFormat).parse(endDate);

                        Calendar startTime = Calendar.getInstance();
                        startTime.setTime(parsedStartDate);
                        Calendar endTime = Calendar.getInstance();
                        endTime.setTime(parsedEndDate);

                        int tempInt  = Integer.parseInt(id);
                        WeekViewEvent event = new WeekViewEvent(tempInt, judul, startTime, endTime);
                        if(Integer.parseInt(statusBook)==0){
                            event.setColor(getResources().getColor(R.color.available));
                        }else{
                            event.setColor(getResources().getColor(R.color.booked));
                        }
//                        if(!eventIdContainer.contains(tempInt)){
                        events.add(event);
//                            eventIdContainer.add(tempInt);
//                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
//                System.out.println("my url is "+url+"sekarang sizenya " + events.size());
//                mWeekView.notifyDatasetChanged();
                pDialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
