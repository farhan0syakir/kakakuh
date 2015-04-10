package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Ini untuk semua action bar biar ada action button pesan
 * Tinggal extend kelas abstract ini
 *
 * Created by Anas on 4/10/2015.
 */
public abstract class BaseActivity extends Activity {

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
        int id = item.getItemId();

        Intent nextScreen;
        String roleSekarang = MainActivity.getRoleSekarang();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_pesan) {
            //Buah Pikiran. Gimana agar pesan activity jalan di background?
            if(roleSekarang.equals("Koordinator")) {
                nextScreen = new Intent(getApplicationContext(), PesanKoordinatorActivity.class);
                startActivity(nextScreen);
            } else if (roleSekarang.equals("KakakAsuh")) {
                nextScreen = new Intent(getApplicationContext(), PesanKakakActivity.class);
                startActivity(nextScreen);
            } else {
                nextScreen = new Intent(getApplicationContext(), PesanAdikActivity.class);
                startActivity(nextScreen);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}