package com.application.a4_school.ui.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.TextView;
import android.widget.Toast;

import com.application.a4_school.Models.ClassRoom;
import com.application.a4_school.Models.FilesUpload;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.application.a4_school.RestAPI.DownloadFromUrl;
import com.application.a4_school.RestAPI.ResponseData;
import com.application.a4_school.adapter.ClassFilesAdapter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailClassRoomActivity extends AppCompatActivity {
    private TextView shDeadline, shTitle, shDetail, shPoint, shAttachment;
    private ClassFilesAdapter adapter;
    private RecyclerView rv_files;
    private String type;
    private static String file_url;
    private String id_taskclass;
    private List<FilesUpload> listFiles = new ArrayList<>();
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_class);
        initialize();
        ClassRoom classdata = getIntent().getParcelableExtra("EXTRA_PARCEL_CLASS");
        type = classdata.getType();
        id_taskclass = classdata.getId_taskclass();

        String role = getSharedPreferences("session", 0).getString("role", "");
        switch (role) {
            case "siswa":
                switch (type) {
                    case "Task":
                        shDeadline.setText("deadline: " + reformatdate(classdata.getDeadline()));
                        break;
                    case "Theory":
                        shPoint.setVisibility(View.GONE);
                        shDeadline.setVisibility(View.GONE);
                        break;
                }
                break;

            case "guru":
                shPoint.setVisibility(View.GONE);
                switch (type) {
                    case "Task":
                        shDeadline.setText("deadline: " + reformatdate(classdata.getDeadline()));
                        break;
                    case "Theory":
                        shDeadline.setVisibility(View.GONE);
                        break;
                }
                break;
        }
        if (classdata.getFile_url() == null) {
            shAttachment.setVisibility(View.GONE);
        } else {
            getItemFile();
        }
        shTitle.setText(classdata.getTitle());
        shDetail.setText(classdata.getDescription());
    }

    private void initialize() {
        shDeadline = findViewById(R.id.tv_deadline_detail_class);
        shPoint = findViewById(R.id.tv_point_detail_class);
        shTitle = findViewById(R.id.tv_title_detail_class);
        shDetail = findViewById(R.id.tv_detail_detail_class);
        shAttachment = findViewById(R.id.txt_attach);
        rv_files = findViewById(R.id.rv_files_detail);
    }

    private void getItemFile() {
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseData> loadFile = api.getListFiles(id_taskclass, "file_guru");
        loadFile.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getFilesDetail() != null) {
                        listFiles.addAll(response.body().getFilesDetail());
                        adapter = new ClassFilesAdapter(listFiles, DetailClassRoomActivity.this, "detail");
                        adapter.notifyDataSetChanged();
                        rv_files.setAdapter(adapter);
                        adapter.setOnItemClickCallback(new ClassFilesAdapter.OnItemClickCallback() {
                            @Override
                            public void onItemClicked(FilesUpload filesUpload, int index) {
                                addListFiles(filesUpload.getFile_url(), filesUpload.getNamefile(), filesUpload.getTypefile(), index);
                            }
                        });
                    }
                } else {
                    Log.d("DetailLoadfile", "not success");
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d("DetailLoadfile", "failure: " + t.getMessage());
            }
        });
    }

    private String reformatdate(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM HH.mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    private void getFile(String url, String title) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        request.setDescription("Load...");
        String cookie = CookieManager.getInstance().getCookie(url);
        request.addRequestHeader("cookie", cookie);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    private void addListFiles(String url, String filename, String extension, int position) {
        Log.d("downloadfile", "" + filename);
        File fileDownloaded = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename + "." + extension);
        Uri openPath = FileProvider.getUriForFile(DetailClassRoomActivity.this, getApplicationContext().getPackageName() + ".provider", fileDownloaded);
        ContentResolver cR = this.getContentResolver();
        String type = cR.getType(openPath);
        Log.d("downloadfile", "mime: " + type);
        Log.d("downloadfile", "" + openPath);
        if (fileDownloaded.exists()) {
            Intent openFile = new Intent(Intent.ACTION_VIEW);
            openFile.setDataAndType(openPath, type);
            openFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Log.d("realmime", "" + type);
            startActivity(openFile);
        } else {
            Log.d("downloadfile", "" + url);
            new DownloadFromUrl(DetailClassRoomActivity.this, url, filename, extension, type).downloadFile();
        }
    }

}