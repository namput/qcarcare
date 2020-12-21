package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

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
                            ArrayList<CustomItem> itemArray=new ArrayList<>();
                            if (result != null) {
                                int len = result.size();
                                for (int i=0;i<len;i++){
                                    JsonObject item=(JsonObject)result.get(i);
                                    String carcare =item.get("carcare_name").getAsString();
                                    String date =item.get("create_date").getAsString();
                                    itemArray.add(new CustomItem(carcare,date));
                                }
                            }
                            CustomAdapter adapter=new CustomAdapter(getBaseContext(),itemArray);
                            ListView listView=(ListView)findViewById(R.id.listView);
                            listView.setAdapter(adapter);
                        }
                    });
        }
    }
}

