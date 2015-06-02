package com.kakakuh.c4ppl.kakakuh.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Anas on 5/24/2015.
 */
public class ImageConverter {
    private static final int DEFAULT_WIDTH = 250;
    private static final int DEFAULT_HEIGHT = 250;

    public static Bitmap convertStringToBitmap (String encodedPhoto) {
        return convertStringToBitmap (encodedPhoto,true);
    }

    public static Bitmap convertStringToBitmap (String encodedPhoto, boolean minify) {
        Bitmap decodedByte = null;
        if(encodedPhoto != null) {
            byte[] decodedString = android.util.Base64.decode(encodedPhoto, android.util.Base64.NO_WRAP);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        if(decodedByte != null) {
            return (minify) ? minify(decodedByte) : decodedByte;
        } else {
            return null;
        }
    }
    public static Bitmap minify(Bitmap bitmap_Source) {
        float factorH = DEFAULT_WIDTH / (float)bitmap_Source.getHeight();
        float factorW = DEFAULT_HEIGHT / (float)bitmap_Source.getWidth();
        float factorToUse = (factorH > factorW) ? factorW : factorH;
        Bitmap bm = Bitmap.createScaledBitmap(bitmap_Source,
                (int) (bitmap_Source.getWidth() * factorToUse),
                (int) (bitmap_Source.getHeight() * factorToUse),
                false);
        return bm;
    }

    public static String convertBitmapToStringBase64(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(bitmap != null) bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
        byte[] hasil = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(hasil,Base64.NO_WRAP);
    }




}
