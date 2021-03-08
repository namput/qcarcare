package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.DateFormat;
import java.util.Date;

public class CheckStatus extends AppCompatActivity {
    String morderqueue;
    String mstatus;
    int mqueue;
    int mprogress;
    int mtime;
    String id;
    String order;
    String url;
    String urlupdatequeue;
    String statusqueue;
    TextView orderqueue;
    TextView status;
    TextView queue;
    TextView progress;
    TextView time;
    Button button_send;
    RelativeLayout relativeLayout;
    SwipeRefreshLayout swipeContainer;
    String stringDate;
    AlertDialog.Builder dialogBuilder;
    View layoutView;
    AlertDialog alertDialog;
    String sentstatuscencel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);
        urlupdatequeue = getString(R.string.updatequeue);
        url = getString(R.string.url);
        statusqueue = getString(R.string.statusqueue);
        sentstatuscencel = getString(R.string.sentstatuscencel);
        orderqueue = (TextView)findViewById(R.id.qorder);
        status = (TextView)findViewById(R.id.status);
        queue = (TextView)findViewById(R.id.queue);
        progress = (TextView)findViewById(R.id.progress);
        time = (TextView)findViewById(R.id.time);
        button_send =(Button)findViewById(R.id.button_send);
        relativeLayout = (RelativeLayout)findViewById(R.id.rela);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            id= bundle.getString("member_id");

            fetchTimelineAsync(0);
            swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    fetchTimelineAsync(0);
                }
            });
            // Configure the refreshing colors
            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        }


        }

    public void fetchTimelineAsync(int page) {
        Date date = new Date();
        stringDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
        Ion.with(CheckStatus.this)
                .load(url+statusqueue)
                .setBodyParameter("id",id)
                .setBodyParameter("datenow",stringDate)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (result!=null){
                            JsonObject item = (JsonObject)result.get(0);
                            order= item.get("queue_order").getAsString();
                            morderqueue = item.get("queue_id").getAsString();
                            mstatus = item.get("status_name").getAsString();
                            int mstatusId = item.get("status_id").getAsInt();
                            mqueue = item.get("queue").getAsInt();
                            mprogress = item.get("progress").getAsInt();
                            mtime = item.get("all_time").getAsInt();
                            if (mstatusId==1){
                                status.setTextColor(Color.parseColor("#00C853"));
                            }
                            if (mstatusId==2){
                                status.setTextColor(Color.parseColor("#00C853"));
                            }
                            if (mstatusId==3){
                                status.setTextColor(Color.parseColor("#BF360C"));
                            }


                            orderqueue.setText("ลำดับคิว "+order);
                            if (mqueue>1) {
                                queue.setText("เหลือ " + mqueue + " คิว");
                                progress.setText("กำลังดำเนินการ " + mprogress + " รายการ");
                                time.setText("เวลาที่ใช้ในการล้างรถโดยประมาณ " + mtime + " นาที");
                            }else {
                                status.setText("ถึงคิวแล้ว");
                                queue.setText("");
                                progress.setText("");
                                time.setText("");
                            }

                            button_send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    showAlertDialog(R.layout.fragment_custom_dialog);
//

                                }
                            });
                            swipeContainer.setRefreshing(false);
                        }

                    }
                });
            }
    private void showAlertDialog(int layout){
        dialogBuilder = new AlertDialog.Builder(CheckStatus.this);
        layoutView = getLayoutInflater().inflate(layout, null);
        Button btnOk = layoutView.findViewById(R.id.btnOk);
        Button btnCancel = layoutView.findViewById(R.id.btnCancel);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ion.with(CheckStatus.this)
                        .load(url+urlupdatequeue)
                        .setBodyParameter("qid", morderqueue)
                        .setBodyParameter("statusid","4")
                        .setBodyParameter("datenow",stringDate)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                String carcare_id = result.get("carcare_id").getAsString();
                                if (result!=null){
                                    Ion.with(CheckStatus.this)
                                            .load(url+sentstatuscencel)
                                            .setBodyParameter("carcare_id",carcare_id)
                                            .asJsonObject()
                                            .setCallback(new FutureCallback<JsonObject>() {
                                                @Override
                                                public void onCompleted(Exception e, JsonObject result) {
                                                }
                                            });
                                    alertDialog.dismiss();
                                    status.setTextColor(Color.parseColor("#BF360C"));
                                    relativeLayout.setBackgroundColor(getColor(R.color.md_amber_900));
                                    status.setText("สถานะ เสร็จสิ้น");
                                    queue.setText(" ");
                                    progress.setText(" ");
                                    time.setText(" ");
                                    swipeContainer.setRefreshing(false);

                                    layoutView = getLayoutInflater().inflate(R.layout.fragment_custom_dialogs, null);
                                    dialogBuilder.setView(layoutView);
                                    alertDialog = dialogBuilder.create();
                                    alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    alertDialog.show();
                                    Button getout = layoutView.findViewById(R.id.getout);
                                    getout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertDialog.dismiss();
                                            finish();
                                            startActivity(new Intent(CheckStatus.this,Menu.class).putExtra("member_id",id));

                                        }
                                    });
                                }



                            }
                        });
                            }

        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}


