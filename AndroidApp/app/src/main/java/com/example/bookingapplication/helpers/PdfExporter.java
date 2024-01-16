package com.example.bookingapplication.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfExporter {

    public static void exportToPdf(Context context, Bitmap chartBitmap, String fileName) {
        File pdfFile = createPdfFile(context, fileName);
        Log.d("PdfExporter", "PDF file path: " + pdfFile.getAbsolutePath());
        if (pdfFile != null) {
            try {
                PdfWriter writer = new PdfWriter(pdfFile);
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                Image chartImage = createImageFromBitmap(chartBitmap);
                document.add(chartImage);

                document.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static File createPdfFile(Context context, String fileName) {
        File pdfFolder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            pdfFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        } else {
            pdfFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        }

        if (!pdfFolder.exists()) {
            pdfFolder.mkdirs();
        }

        return new File(pdfFolder, fileName + ".pdf");
    }

    private static Image createImageFromBitmap(Bitmap bitmap) {
        ImageData imageData = ImageDataFactory.create(bitmapToByteArray(bitmap));
        return new Image(imageData);
    }

    private static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
