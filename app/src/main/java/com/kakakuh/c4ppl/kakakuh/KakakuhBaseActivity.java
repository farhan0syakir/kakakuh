package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;

/**
 * Ini untuk semua action bar biar ada action button pesan
 * Tinggal extend kelas abstract ini
 *
 * Created by Anas on 4/10/2015.
 */
public abstract class KakakuhBaseActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                //Intent upIntent = new Intent(this, MainActivity.class);
                //upIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(upIntent);
                finish();
                super.onBackPressed();
                return true;
            case R.id.action_pesan:
                //Buah Pikiran. Gimana agar pesan activity jalan di background?
                Intent nextScreen;
                Preferensi pref = new Preferensi(this);
                switch (pref.getRole()) {
                    case "Koordinator":
                        nextScreen = new Intent(getApplicationContext(), PesanActivity.class);
                        startActivity(nextScreen);
                        break;
                    case "Kakak Asuh":
                        nextScreen = new Intent(getApplicationContext(), PesanActivity.class);
                        startActivity(nextScreen);
                        break;
                    case "Adik Asuh":
                        //TODO Query ambil data kakak ROLAND
                        int jumlahKakak = 1;
                        if(jumlahKakak > 1) {
                            nextScreen = new Intent(getApplicationContext(), PesanActivity.class);
                            startActivity(nextScreen);
                        } else {
                            nextScreen = new Intent(getApplicationContext(), DetailPesanActivity.class);
                            nextScreen.putExtra("username","choco");
                            //TODO encoded Bitmap ROLAND
                            //nextScreen.putExtra("image",);
                            nextScreen.putExtra("nama","lala");
                            startActivity(nextScreen);
                        }
                        break;
                    default:
                        break;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}