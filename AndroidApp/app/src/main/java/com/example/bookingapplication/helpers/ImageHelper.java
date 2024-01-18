package com.example.bookingapplication.helpers;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageHelper {

    public static MultipartBody.Part bitmapToMultipartBodyPart(Bitmap bitmap) {
        // Convert Bitmap to File
        File imageFile = convertBitmapToFile(bitmap);

        // Create RequestBody from File
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);

        // Create MultipartBody.Part from RequestBody
        return MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
    }

    private static File convertBitmapToFile(Bitmap bitmap) {
        File filesDir = Environment.getExternalStorageDirectory();
        File imageFile = new File(filesDir, "image.jpg");

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFile;
    }
}