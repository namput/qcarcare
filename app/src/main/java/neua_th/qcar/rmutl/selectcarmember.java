package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class selectcarmember extends AppCompatActivity {

    String url;
    String urllistcarmember;
    ListView listView;
    String memberid;
    int ccid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcarmember);

        url= getString(R.string.url);
        urllistcarmember= getString(R.string.listcarmember);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            memberid = bundle.getString("mid");
            ccid = bundle.getInt("carcareid");
            Ion.with(selectcarmember.this)
                    .load(url+urllistcarmember)
                    .setBodyParameter("id",memberid)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            ArrayList<CMCustomITem> itemArray = new ArrayList<>();
                            if (result!=null){
                                int len =result.size();
                                for (int i=0;i<len;i++){
                                    JsonObject jsObject = (JsonObject)result.get(i);
                                    int carmemberid = jsObject.get("id").getAsInt();
                                    String service = jsObject.get("sername").toString();
                                    String color = jsObject.get("color").toString();
                                    String carnumber = jsObject.get("carnumber").toString();
                                    String brand = jsObject.get("brand").toString();
                                    itemArray.add(new CMCustomITem(carmemberid,service,color,carnumber,brand));
                                }

                            }
                            //Toast.makeText(selectcarmember.this,""+itemArray,Toast.LENGTH_LONG).show();
                            CMCustomAdapter adapter = new CMCustomAdapter(getBaseContext(),itemArray);
                            listView=(ListView)findViewById(R.id.listView2);
                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    CMCustomITem item = itemArray.get(position);
                                    int carmemberid =item.mid;
                                    Intent intent = new Intent(selectcarmember.this,listproduct.class);
                                    intent.putExtra("carcareid", ccid);
                                    intent.putExtra("carmemberid", carmemberid);
                                    intent.putExtra("mid",memberid);
                                    startActivity(intent);
                                }
                            });
                        }
                    });


        }

    }

}