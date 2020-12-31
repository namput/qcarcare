package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class CheckStatus extends AppCompatActivity {
    String morderqueue;
    String mstatus;
    int mqueue;
    int mprogress;
    int mtime;
    String id;
    String order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);
        String urlupdatequeue = getString(R.string.updatequeue);
        String url = getString(R.string.url);
        String statusqueue = getString(R.string.statusqueue);
        JsonArray itemArray = new JsonArray();
        TextView orderqueue = (TextView)findViewById(R.id.qorder);
        TextView status = (TextView)findViewById(R.id.status);
        TextView queue = (TextView)findViewById(R.id.queue);
        TextView progress = (TextView)findViewById(R.id.progress);
        TextView time = (TextView)findViewById(R.id.time);
        Button button_send =(Button)findViewById(R.id.button_send);
        RatingBar ratingBar=(RatingBar)findViewById(R.id.simpleRatingBar);
        Button rating=(Button)findViewById(R.id.rating);

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float getrating = ratingBar.getRating();

                Toast.makeText(CheckStatus.this,""+getrating,Toast.LENGTH_LONG).show();
            }
        });



        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            id= bundle.getString("mid");

            Ion.with(CheckStatus.this)
                    .load(url+statusqueue)
                    .setBodyParameter("id",id)
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
                                if (mstatusId==2){
                                    status.setTextColor(Color.parseColor("#00C853"));
                                }
                                if (mstatusId==3){
                                    status.setTextColor(Color.parseColor("#BF360C"));
                                }

                                status.setText("สถานะ "+mstatus);
                                orderqueue.setText("ลำดับคิว "+order);
                                queue.setText("เหลือ "+mqueue+" คิว");
                                progress.setText("กำลังดำเนินการ "+mprogress+" รายการ");
                                time.setText("เวลาที่ใช้ในการล้างรถโดยประมาณ "+mtime+" นาที");

                                button_send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Ion.with(CheckStatus.this)
                                                .load(url+urlupdatequeue)
                                                .setBodyParameter("qid", morderqueue)
                                                .setBodyParameter("statusid","3")
                                                .asString()
                                                .setCallback(new FutureCallback<String>() {
                                                    @Override
                                                    public void onCompleted(Exception e, String result) {
                                                        status.setTextColor(Color.parseColor("#BF360C"));
                                                        status.setText("สถานะ เสร็จสิ้น");
                                                        queue.setText(" ");
                                                        progress.setText(" ");
                                                        time.setText(" ");

                                                    }
                                                });
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                Intent intent=new Intent(CheckStatus.this,Menu.class);
                                                intent.putExtra("member_id",id);
                                                finish();
                                                startActivity(intent);
                                            }
                                        },30000);

                                    }
                                });
                            }

                        }
                    });

        }


    }

}
