package com.application.a4_school.ui.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.application.a4_school.Models.ClassRoom;
import com.application.a4_school.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailClassRoomActivity extends AppCompatActivity {
    private TextView shDeadline, shTitle, shDetail, shPoint, shAttachment;
    private RecyclerView rv_files;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_class);
        initialize();
        ClassRoom classdata = getIntent().getParcelableExtra("EXTRA_PARCEL_CLASS");
        type = classdata.getType();
        String role = getSharedPreferences("session", 0).getString("role", "");
        switch (role){
            case "siswa":
                switch (type){
                    case "Task":
                        shDeadline.setText("deadline: "+reformatdate(classdata.getDeadline()));
                        break;
                    case "Theory":
                        shPoint.setVisibility(View.GONE);
                        shDeadline.setVisibility(View.GONE);
                        break;
                }
                break;

            case "guru":
                shPoint.setVisibility(View.GONE);
                switch (type){
                    case "Task":
                        shDeadline.setText("deadline: "+reformatdate(classdata.getDeadline()));
                        break;
                    case "Theory":
                        shDeadline.setVisibility(View.GONE);
                        break;
                }
                break;
        }
        if (classdata.getFile_url() == null){
            shAttachment.setVisibility(View.GONE);
        }
        shTitle.setText(classdata.getTitle());
        shDetail.setText(classdata.getDescription());
    }

    private void initialize(){
        shDeadline  = findViewById(R.id.tv_deadline_detail_class);
        shPoint     = findViewById(R.id.tv_point_detail_class);
        shTitle     = findViewById(R.id.tv_title_detail_class);
        shDetail    = findViewById(R.id.tv_detail_detail_class);
        shAttachment= findViewById(R.id.txt_attach);
        rv_files    = findViewById(R.id.rv_files_detail);
    }

    private String reformatdate(String time){
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
}