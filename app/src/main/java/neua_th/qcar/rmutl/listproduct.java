 package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

 public class listproduct extends AppCompatActivity {
String url;
String urlattribute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listproduct);
        url =getString(R.string.url);
        urlattribute = getString(R.string.attribute);
        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            String carcareid = bundle.get("carcareid").toString();
            String carmemberid = bundle.get("carmemberid").toString();
            String memberid = bundle.getString("mid");

            Ion.with(listproduct.this)
                    .load(url+urlattribute)
                    .setBodyParameter("id",memberid)
                    .setBodyParameter("cmid",carmemberid)
                    .setBodyParameter("cid",carcareid)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            ArrayList<AtCustomITem> itemArray=new ArrayList<>();
                            if (result!=null){
                                int len =result.size();
                                for (int i=0;i<len;i++){
                                    JsonObject item = (JsonObject)result.get(i);
                                    int aid = item.get()

                                }
                            }
                            Toast.makeText(listproduct.this,""+result,Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}