package com.example.tnelection2021.PdfDownload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.example.tnelection2021.DetailsActivity;
import com.example.tnelection2021.MainActivity;

import java.io.File;
import java.io.IOException;

public class DownloadFile extends AsyncTask<String, Void, Void>  {

    Context context;
    String filePath;
    Uri uri;

    public DownloadFile(Context context){
        this.context=context;
    }

    // Progress dialog
    private ProgressDialog pDialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(  context   );
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
    }

    @Override
    protected Void doInBackground(String... strings) {

        String fileUrl = strings[0];
        String fileName = strings[1];
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "PDF DOWNLOAD");
        folder.mkdir();

        File pdfFile = new File(folder, fileName);
        try{
            pdfFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        filePath = pdfFile.getPath();
        uri = Uri.parse(filePath);
        FileDownloader.downloadFile(fileUrl, pdfFile);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        hidepDialog();
       Toast.makeText(context, "Download PDf successfully", Toast.LENGTH_SHORT).show();

       openPdf();
       // Log.d("Download complete", "----------");
    }

    private void openPdf()
    {
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
