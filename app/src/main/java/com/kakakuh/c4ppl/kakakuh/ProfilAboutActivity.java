package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.kakakuh.c4ppl.kakakuh.controller.PengaturanListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.ProfilAboutAdapter;
import com.kakakuh.c4ppl.kakakuh.model.PengaturanListItem;
import java.util.ArrayList;

/**
 * Created by Aldi Reinaldi on 12/04/2015.
 */

public class ProfilAboutActivity extends BaseActivity{
    private ListView listProfilAbout;
    private String[] listTitles;

    private ArrayList<PengaturanListItem> profilListItems;
    private TypedArray listEmeraldIcons;

    //Adapter
    private ProfilAboutAdapter adapter;

    //untuk preferensi
    private String roleSekarang;

    public ProfilAboutActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_generic);
        //baca preferensi
        roleSekarang = MainActivity.getRoleSekarang();

        listProfilAbout = (ListView) findViewById(R.id.list_generic);
        listTitles = getResources().getStringArray(R.array.profil_about_title);
        listEmeraldIcons = getResources().obtainTypedArray(R.array.profil_about_icons);

        profilListItems = new ArrayList<>();

        //npm
        profilListItems.add(new PengaturanListItem(listTitles[0], listEmeraldIcons.getResourceId(0, -1)));
        //nomer hape
        profilListItems.add(new PengaturanListItem(listTitles[1], listEmeraldIcons.getResourceId(1, -1)));
        //alamat
        profilListItems.add(new PengaturanListItem(listTitles[2], listEmeraldIcons.getResourceId(2, -1)));
        //asal daerah
        profilListItems.add(new PengaturanListItem(listTitles[3], listEmeraldIcons.getResourceId(3, -1)));
        //motto
        profilListItems.add(new PengaturanListItem(listTitles[4], listEmeraldIcons.getResourceId(4, -1)));

        // Recycle the typed array
        listEmeraldIcons.recycle();

        // setting the Profil list adapter
        adapter = new ProfilAboutAdapter(getApplicationContext(), profilListItems);
        listProfilAbout.setAdapter(adapter);

    }
}

