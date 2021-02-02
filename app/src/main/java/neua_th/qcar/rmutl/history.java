package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class history extends AppCompatActivity {
    String url;
    String urlhistory;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        listView = findViewById(R.id.listView);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        url= getString(R.string.url);
        urlhistory= getString(R.string.history);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            String mid = bundle.getString("member_id");
            Ion.with(history.this)
                    .load(url+urlhistory)
                    .setBodyParameter("id",mid)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            ArrayList<HCustomITem> itemArray=new ArrayList<>();
                            if (result != null) {
                                int len = result.size();
                                for (int i=0;i<len;i++){
                                    JsonObject item=(JsonObject)result.get(i);
                                    String carcare =item.get("carcare_name").getAsString();
                                    String date =item.get("create_date").getAsString();
                                    String qid =item.get("queue_id").getAsString();
                                    String cmnumber =item.get("car_member_number").getAsString();
                                    String sername =item.get("car_service_name").getAsString();
                                    String status =item.get("status_id").getAsString();
                                    itemArray.add(new HCustomITem(qid,carcare,date,cmnumber,sername,status));
//                                    Toast.makeText(history.this,""+date,Toast.LENGTH_SHORT).show();
                                }
                            }
                            HCustomAdapter adapter = new HCustomAdapter(getBaseContext(),itemArray);
                            LinearLayout mcontact = (LinearLayout) findViewById(R.id.contact);

                            listView=(ListView)findViewById(R.id.listView);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    HCustomITem item = itemArray.get(position);
                                    String queueId =item.qid;
                                    String status = item.status;

                                    switch (status){
                                        case "3":
                                        Intent intent = new Intent(history.this,RatingActivity.class);
                                        intent.putExtra("queueId", queueId);
                                        intent.putExtra("mid",mid);
                                        startActivity(intent);
                                        break;
                                        default:Toast.makeText(history.this,"รายการนี้ไม่สามารถให้ความเห็นได้",Toast.LENGTH_LONG).show();break;
                                    }

                                }
                            });
                        }
                    });
        }
    }
}

