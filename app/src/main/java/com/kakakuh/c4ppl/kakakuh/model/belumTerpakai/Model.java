package com.kakakuh.c4ppl.kakakuh.model.belumTerpakai;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by farhansyakir on 26/03/15.
 */
public interface Model {
    public String Create();
    public String Update(Object object);
    public String Delete();
}
