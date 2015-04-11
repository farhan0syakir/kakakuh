package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.controller.NavDrawerListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.NavDrawerItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView mDrawerRole;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    //terkait shared preferensi
    static private String roleSekarang; //default
    static private int positionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getResources().getString(R.string.app_name);

        //ambil dari layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        //Add header
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header_role = (ViewGroup)inflater.inflate(R.layout.header_drawer, mDrawerList, false);
        mDrawerList.addHeaderView(header_role, null, false);
        mDrawerRole = (TextView) findViewById(R.id.role);
        mDrawerRole.setText(roleSekarang);

        //sesuaikan drawer dengan role
        if(roleSekarang.equals("Koordinator")) {
            //load slide menu items
            navMenuTitles = getResources().getStringArray(R.array.koor_nav_drawer_items);

            //nav drawer icons from resources
            navMenuIcons = getResources().obtainTypedArray(R.array.koor_nav_drawer_icons);

            navDrawerItems = new ArrayList<NavDrawerItem>();

            //Profil
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
            //Review Log
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            //Buat Akun
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
            //Hapus Akun
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
            //Pasangkan akun
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
            //List kakak asuh
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
            //List adik asuh
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
            //Pengaturan
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));

            // Recycle the typed array
            navMenuIcons.recycle();
        } else if (roleSekarang.equals("Kakak Asuh")) {

            //load slide menu items
            navMenuTitles = getResources().getStringArray(R.array.kakak_nav_drawer_items);

            //nav drawer icons from resources
            navMenuIcons = getResources().obtainTypedArray(R.array.kakak_nav_drawer_icons);

            navDrawerItems = new ArrayList<NavDrawerItem>();

            //Profil
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
            //Adik Asuhku
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            //Jadwal
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
            //Konfirmasi Booking
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
            //List Kakak Asuh
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
            //Pengaturan
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

            // Recycle the typed array
            navMenuIcons.recycle();
        } else /*adik*/ {
            //load slide menu items
            navMenuTitles = getResources().getStringArray(R.array.adik_nav_drawer_items);

            //nav drawer icons from resources
            navMenuIcons = getResources().obtainTypedArray(R.array.adik_nav_drawer_icons);

            navDrawerItems = new ArrayList<NavDrawerItem>();

            //Profil
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
            //Tugas
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            //Jadwal Kakak
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
            //List Kakak Asuh
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
            //Pengaturan
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

            // Recycle the typed array
            navMenuIcons.recycle();
        }

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(navMenuTitles[1]);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                getActionBar().setIcon(R.drawable.ic_white_home);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                hideKeyboard(); //hide keyboard
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            positionFragment = 1;
            // sesuaikan dengan role
            if(roleSekarang.equals("Koordinator")) {
                displayViewKoordinator(1);
            } else if (roleSekarang.equals("Kakak Asuh")) {
                displayViewKakak(1);
            } else /*adik*/ {
                displayViewAdik(1);
            }
        }
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            position -= mDrawerList.getHeaderViewsCount();
            if(roleSekarang.equals("Koordinator")) displayViewKoordinator(position);
            else if (roleSekarang.equals("Kakak Asuh")) displayViewKakak(position);
            else /*adik*/ displayViewAdik(position);
            positionFragment = position;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle presses on the action bar items
        Intent nextScreen;
        switch (item.getItemId()) {
            case R.id.action_pesan:
                //TODO: go to Pesan
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_pesan).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * Untuk KOORDINATOR
     * */
    private void displayViewKoordinator(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                getActionBar().setIcon(R.drawable.ic_white_profil);
                fragment = new ProfilFragment();
                break;
            case 1:
                getActionBar().setIcon(R.drawable.ic_white_home);
                fragment = new ReviewLogFragment();
                break;
            case 2:
                getActionBar().setIcon(R.drawable.ic_white_buat_akun);
                fragment = new BuatAkunFragment();
                break;
            case 3:
                getActionBar().setIcon(R.drawable.ic_white_hapus_akun);
                fragment = new HapusAkunFragment();
                break;
            case 4:
                getActionBar().setIcon(R.drawable.ic_white_pasangkan_akun);
                fragment = new PasangkanAkunFragment();
                break;
            case 5:
                getActionBar().setIcon(R.drawable.ic_white_list_akun);
                fragment = new ListKakakAsuhFragment();
                break;
            case 6:
                getActionBar().setIcon(R.drawable.ic_white_list_akun_adik);
                fragment = new ListAdikAsuhFragment();
                break;
            case 7:
                getActionBar().setIcon(R.drawable.ic_white_pengaturan);
                fragment = new PengaturanFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position+1, true);
            mDrawerList.setSelection(position+1);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("KoordinatorView", "Error in creating fragment");
        }
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * untuk KAKAK ASUH
     * */
    private void displayViewKakak(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                getActionBar().setIcon(R.drawable.ic_white_profil);
                fragment = new ProfilFragment();
                break;
            case 1:
                getActionBar().setIcon(R.drawable.ic_white_home);
                fragment = new AdikAsuhkuFragment();
                break;
            case 2:
                getActionBar().setIcon(R.drawable.ic_white_jadwal);
                fragment = new JadwalFragment();
                break;
            case 3:
                getActionBar().setIcon(R.drawable.ic_white_konfirmasi_booking);
                fragment = new KonfirmasiBookingFragment();
                break;
            case 4:
                getActionBar().setIcon(R.drawable.ic_white_list_akun);
                fragment = new ListKakakAsuhFragment();
                break;
            case 5:
                getActionBar().setIcon(R.drawable.ic_white_pengaturan);
                fragment = new PengaturanFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position+1, true);
            mDrawerList.setSelection(position+1);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("KakakView", "Error in creating fragment");
        }
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * Untuk ADIK ASUH
     * */
    private void displayViewAdik(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                getActionBar().setIcon(R.drawable.ic_white_profil);
                fragment = new ProfilFragment();
                break;
            case 1:
                getActionBar().setIcon(R.drawable.ic_white_home);
                fragment = new TugasFragment();
                break;
            case 2:
                getActionBar().setIcon(R.drawable.ic_white_jadwal);
                fragment = new JadwalKakakFragment();
                break;
            case 3:
                getActionBar().setIcon(R.drawable.ic_white_konfirmasi_booking);
                fragment = new ListKakakAsuhFragment();
                break;
            case 4:
                getActionBar().setIcon(R.drawable.ic_white_pengaturan);
                fragment = new PengaturanFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position+1, true);
            mDrawerList.setSelection(position+1);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("KakakView", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    // agar backnya sesuai UAT
    @Override
    public void onBackPressed() {
        if(positionFragment == 1) {
            //ketika di home. back dari activity
            super.onBackPressed();
        } else {
            //else. display ke home
            positionFragment = 1;
            if(roleSekarang.equals("Koordinator")) {
                displayViewKoordinator(1);
            } else if (roleSekarang.equals("Kakak Asuh")) {
                displayViewKakak(1);
            } else /*adik*/ {
                displayViewAdik(1);
            }
        }
    }

    public static String getRoleSekarang() {
        return roleSekarang;
    }

    public static void setRoleSekarang(String roleNumber) {
        if(roleNumber.equals("0")){
            roleSekarang = "Koordinator";
        } else if(roleNumber.equals("1")){
            roleSekarang = "Kakak Asuh";
        } else {
            roleSekarang = "Adik Asuh";
        }
    }

    /* hide keyboard*/
    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}