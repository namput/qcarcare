package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.IonContext;

import java.text.DateFormat;
import java.util.Date;

public class Menu extends AppCompatActivity {

    private String mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        final String urlcheckstatus = getString(R.string.checkstatus);
        final String url = getString(R.string.url);
        final LinearLayout profile = (LinearLayout) findViewById(R.id.profile);
        final LinearLayout mcontact = (LinearLayout) findViewById(R.id.contact);
        final LinearLayout queue = (LinearLayout) findViewById(R.id.queue);
        final LinearLayout history = (LinearLayout) findViewById(R.id.history);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mid = bundle.getString("member_id");

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final String urlshowprofile = getString(R.string.showprofile);

                    Ion.with(Menu.this)
                            .load(url + urlshowprofile)
                            .setBodyParameter("id", mid)
                            .setBodyParameter("type", "3")
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result != null) {
                                        String member_id = result.get("member_id").getAsString();
                                        String member_phone = result.get("member_phone").getAsString();
                                        String member_name = result.get("member_name").getAsString();

                                        Intent intent = new Intent(Menu.this, profile.class);
                                        intent.putExtra("member_id", member_id);
                                        intent.putExtra("member_phone", member_phone);
                                        intent.putExtra("member_name", member_name);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Menu.this, "ไม่สามารถสมัครสมาชิกได้", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            });
            mcontact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Menu.this, contact.class);
                    startActivity(intent);
                }
            });

            queue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = new Date();
                    String stringDate = DateFormat.getDateInstance().format(date);
                    Ion.with(Menu.this)
                            .load(url+urlcheckstatus)
                            .setBodyParameter("id",mid)
                            .setBodyParameter("datenow",stringDate)
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    Intent intent;
                                    if (result!=""){

                                        intent = new Intent(Menu.this, CheckStatus.class);
                                        intent.putExtra("mid",mid);
                                    }
                                    else {
                                        intent = new Intent(Menu.this, QueueActivity.class);
                                        intent.putExtra("member_id", mid);
                                    }
                                    startActivity(intent);
                                }
                            });
                }
            });

            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Menu.this, history.class);
                    intent.putExtra("member_id", mid);
                    startActivity(intent);
                }
            });

        } else {
            Intent intent = new Intent(Menu.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

}