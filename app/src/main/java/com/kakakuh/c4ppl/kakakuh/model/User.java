package com.kakakuh.c4ppl.kakakuh.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
//
public class User implements Model{
    private int id;
    private int role;
    private String username;
    private String password;
    private String npm;
    private String name;
    private String angkatan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }


    @Override
    public String Create() {
        //

            /*String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
            data += "&" + URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(Login, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(Pass, "UTF-8");
            */
            String data = "?id=1&role-1&username=farhan&password=ganteng&npm=1206242151&name=farhan&angkatan=2012";
            String text = "";
            BufferedReader reader=null;
            // Send data
            try
            {

                URL url = new URL("ppl-c04.cs.ui.ac.id/login.php");

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                // Get the response


                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;


                while((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                text = sb.toString();
            }
            catch(Exception ex)
            {

            }
            finally
            {
                try
                {

                    reader.close();
                }
                catch(Exception ex) {}
            }



        return text;
    }

    @Override
    public String Update(Object object) {
        User user = (User) object;
        this.id = user.id;
        this.role = user.role;
        this.angkatan = user.angkatan;
        this.name = user.name;
        this.npm = user.npm;
        this.username = user.username;
        this.password = user.password;

        return "berhasil mengupdate";
    }

    @Override
    public String Delete() {
        return null;
    }
}
