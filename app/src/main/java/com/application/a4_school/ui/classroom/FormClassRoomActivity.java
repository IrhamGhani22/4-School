package com.application.a4_school.ui.classroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.application.a4_school.Models.ClassRoom;
import com.application.a4_school.Models.FilesUpload;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.application.a4_school.adapter.ClassFilesAdapter;
import com.application.a4_school.adapter.SpinnerAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormClassRoomActivity extends AppCompatActivity {
    private Spinner spinType;
    private String type;
    private Button btnUpload;
    private int id_schedule;
    public static final int REQUEST_FILE = 9543;
    private TextInputEditText inputTitlte, inputDescription, inputDays, inputMonth, inputYear, inputTime;
    private TextInputLayout containerTimeDeadline;
    private ImageButton btnCalendar;
    DatePickerDialog.OnDateSetListener setListener;
    private AlertDialog.Builder dialog;
    private TextView deadlineTitle, uploadTitle;
    private LinearLayout containerDeadline;
    private CardView containerUpload;
    private RecyclerView rvFiles;
    private List<FilesUpload> listFileSelected = new ArrayList<>();
    ClassFilesAdapter filesAdapter;
    String filepath[];
    String extension[];
    //data dummy dropdown, hapus aja nanti
    String[] selectOption = {"Task", "Theory"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_class_room);
        findView();
        dialog = new AlertDialog.Builder(this);
        id_schedule = getIntent().getIntExtra("EXTRA_ID_SCHEDULE", 0);
        datePicker(inputYear, inputMonth, inputDays);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_type_style, selectOption);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinType.setAdapter(spinnerAdapter);
        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = spinType.getSelectedItem().toString();
                Log.d("spinValue", "value: "+type);
                if (type.equals("Theory")){
                    deadlineTitle.animate().alpha(0f).setDuration(250);
                    containerDeadline.animate().alpha(0f).setDuration(280);
                    containerTimeDeadline.animate().alpha(0f).setDuration(300);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            deadlineTitle.setVisibility(View.GONE);
                            containerDeadline.setVisibility(View.GONE);
                            containerTimeDeadline.setVisibility(View.GONE);
                        }
                    }, 300);

                }else{
                    deadlineTitle.animate().alpha(1.0f).setDuration(250);
                    containerDeadline.animate().alpha(1.0f).setDuration(280);
                    containerTimeDeadline.animate().alpha(1.0f).setDuration(300);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            deadlineTitle.setVisibility(View.VISIBLE);
                            containerDeadline.setVisibility(View.VISIBLE);
                            containerTimeDeadline.setVisibility(View.VISIBLE);
                        }
                    }, 300);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        containerUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMedia = new Intent(Intent.ACTION_GET_CONTENT);
                openMedia.setType("*/*");
                startActivityForResult(openMedia, REQUEST_FILE);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(FormClassRoomActivity.this);
                progressDialog.setMessage("Uploading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                String id_matpel = getSharedPreferences("userInfo", 0).getString("profession", "");
                String title = inputTitlte.getText().toString().trim();
                String desc = inputDescription.getText().toString().trim();
                String deadline = inputYear.getText().toString().trim() + "-" + inputMonth.getText().toString().trim() + "-" + inputDays.getText().toString().trim()+" "+inputTime.getText().toString().trim();
                if (title.equals("")){
                    inputTitlte.setError("Please input this field");
                }
                if(desc.equals("")){
                    inputDescription.setError("Please input this field");
                }
                if (type.equals("Task") && deadline.equals("-- ")){
                    inputDays.setError("Please input this field");
                    inputMonth.setError("Please input this field");
                    inputYear.setError("Please input this field");
                    inputTime.setError("Please input this field");
                }
                if (type.equals("Task") && !title.equals("") && !desc.equals("") && !deadline.equals("-- ")) {
                    progressDialog.show();
                    uploadTaskTheory(title, desc, type, deadline, progressDialog);
                }if (type.equals("Theory") && !title.equals("") && !desc.equals("")){
                    progressDialog.show();
                    uploadTaskTheory(title, desc, type, null, progressDialog);
                }
            }
        });
    }

    private void findView(){
        spinType = findViewById(R.id.list_task);
        btnUpload = findViewById(R.id.btn_upload_tasktheory);
        inputTitlte = findViewById(R.id.edt_title_formclass);
        inputDescription = findViewById(R.id.edt_descrip_formclass);
        inputDays = findViewById(R.id.edt_days_deadline);
        inputMonth = findViewById(R.id.edt_month_deadline);
        inputYear = findViewById(R.id.edt_year_deadline);
        btnCalendar = findViewById(R.id.btn_calendar_deadline);
        deadlineTitle = findViewById(R.id.deadlineText);
        containerDeadline = findViewById(R.id.container_deadline);
        containerUpload = findViewById(R.id.container_addfile);
        inputTime = findViewById(R.id.edt_time_deadline);
        containerTimeDeadline = findViewById(R.id.container_time_deadline);
        rvFiles = findViewById(R.id.rv_files);
    }

    private void datePicker(final TextInputEditText dateYear, final TextInputEditText dateMonth, final TextInputEditText dateDay) {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        FormClassRoomActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                new TimePickerDialog(FormClassRoomActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        Log.d("Timepickervalue", "The choosen one " + calendar.getTime());
                        inputTime.setText(hourOfDay+"."+minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

                month = month + 1;
                String tahun = "" + year;
                String bulan = "" + month;
                String hari = "" + dayOfMonth;
                dateYear.setText(tahun);
                dateMonth.setText(bulan);
                dateDay.setText(hari);
            }
        };

    }

    private void uploadTaskTheory(final String title, String description, final String type, String deadline, final ProgressDialog progressDialog){
        String token = getSharedPreferences("session", 0).getString("token", "");
        MultipartBody.Part[] document;
        if (listFileSelected.isEmpty()){
            document = null;
        }else{
            document = prepareDocument(listFileSelected, "file");
        }
        APIService api = APIClient.getClient().create(APIService.class);
        RequestBody partTilte = createPartFromString(title);
        RequestBody partDesc = createPartFromString(description);
        RequestBody partType = createPartFromString(type);
        RequestBody partDeadline = null;
        if (deadline!=null) {
            partDeadline = createPartFromString(deadline);
        }
        Call<ResponseBody> upload = api.uploadTaskTheory("Bearer "+token, id_schedule, partTilte, partDesc, partType, partDeadline, document);
        upload.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    try {
                        String jsonObject = response.body().string();
                        Log.d("UploadTask", "success: " + jsonObject);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.setTitle("Sucess upload");
                    dialog.setMessage(type+" "+title+" was uploaded");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            String role = getSharedPreferences("session", 0).getString("role", "");
                            setResult(14);
                            finish();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.show();
                }else{
                    progressDialog.dismiss();
                    try {
                        String jObjError = response.errorBody().string();
                        Log.d("UploadTask", "not success: "+jObjError);
                        Toast.makeText(FormClassRoomActivity.this, jObjError, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(FormClassRoomActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Log.d("UploadTask", "not success: "+response.body());
                    dialog.setTitle(type+" Failed uploaded");
                    dialog.setMessage("We can't upload now, please try again later");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("UploadTask", "failure: "+t.getMessage());
                dialog.setTitle(type+" Failed uploaded");
                dialog.setMessage("We can't connect to server please check your internet connection");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });
    }

    private RequestBody createPartFromString(String param) {
        return RequestBody.create(MediaType.parse("*/*"), param);
    }

    private MultipartBody.Part[] prepareDocument(List<FilesUpload> fileupload, String partName){
        MultipartBody.Part[] multipleFile = new MultipartBody.Part[fileupload.size()];
        for (int i=0; i<fileupload.size(); i++) {
            File file = new File(fileupload.get(i).getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            multipleFile[i] = MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
        }
        return  multipleFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_FILE:
                if (resultCode == RESULT_OK){
                    Uri path = data.getData();
                    Log.d("activityResultValue", "value path: "+path);
                    Log.d("activityResultValue", "value name: "+getFileName(path));
                    Log.d("activityResultValue", "value realpath: "+getRealPathFromURI(this,path));
                    Log.d("activityResultValue", "value realpath: "+getMimeType(this,path));
                    FilesUpload filesUpload = new FilesUpload();
                    filesUpload.setNamefile(getFileName(path));
                    filesUpload.setTypefile(getMimeType(this, path));
                    filesUpload.setPath(getRealPathFromURI(this, path));
                    listFileSelected.add(filesUpload);
                    filesAdapter = new ClassFilesAdapter(listFileSelected, this);
                    rvFiles.setAdapter(filesAdapter);
                    filesAdapter.notifyDataSetChanged();

                    try {
                        InputStream inputStream = FormClassRoomActivity.this.getContentResolver().openInputStream(path);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;

        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}