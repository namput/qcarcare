package neua_th.qcar.rmutl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class ConfigActivity extends AppCompatActivity {

    String url;
    String showcolor;
    String urlupdatename;
    String urlshowcar;
    String urlcarmember;
    String mnumcar;
    String mbrand;
    Spinner spinner;
    Spinner spinnercolor;
    ArrayList<String> carlist;
    ArrayList<CustomItem> color;
    AlertDialog.Builder dialogBuilder;
    View layoutView;
    AlertDialog alertDialog;
    Button getout;
    TextView content;
    String urllistcarmember;
    String mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);


        url= getString(R.string.url);
        showcolor=getString(R.string.showcolor);
        urlupdatename=getString(R.string.updatename);
        urlshowcar=getString(R.string.showcar);
        urlcarmember=getString(R.string.carmember);
        urllistcarmember = getString(R.string.listcarmember);

        EditText mname=(EditText)findViewById(R.id.name);
        Button savename=(Button)findViewById(R.id.savename);


        dialogBuilder = new AlertDialog.Builder(ConfigActivity.this);

        carlist =new ArrayList<>();
        spinner =findViewById(R.id.carsize);
        color =new ArrayList<>();
        spinnercolor =findViewById(R.id.color);

//เช็คว่ามีการส่งค่ามาไหม
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            mid = bundle.getString("member_id");
            String member_name = bundle.getString("member_name");
            if (member_name==null){
                member_name="";
            }


            mname.setText(member_name);
            savename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String changname=mname.getText().toString();

                    Ion.with(ConfigActivity.this)
                            .load(url+urlupdatename)
                            .setBodyParameter("id",mid)
                            .setBodyParameter("name",changname)
                            .setBodyParameter("type","3")
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {


                                    if (result!=null){
                                        String member_name=result.get("member_name").getAsString();
                                        mname.setText(member_name);

                                        layoutView = getLayoutInflater().inflate(R.layout.dialog_success, null);
                                        getout=layoutView.findViewById(R.id.getout);
                                        content =layoutView.findViewById(R.id.contact);
                                        dialogBuilder.setView(layoutView);
                                        alertDialog = dialogBuilder.create();
                                        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        alertDialog.show();
                                        getout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertDialog.dismiss();

                                            }
                                        });

                                    }
                                    else {
                                        layoutView = getLayoutInflater().inflate(R.layout.dialog_fail, null);
                                        getout=layoutView.findViewById(R.id.getout);
                                        content =layoutView.findViewById(R.id.contact);
                                        dialogBuilder.setView(layoutView);
                                        alertDialog = dialogBuilder.create();
                                        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        alertDialog.show();
                                        getout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertDialog.dismiss();
                                                Intent intent=new Intent(ConfigActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    }
                                }
                            });
                }
            });
        }
        else {
            Intent intent=new Intent(ConfigActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}


