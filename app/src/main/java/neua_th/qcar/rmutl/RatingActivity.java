package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.rmutl.R;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class RatingActivity extends AppCompatActivity {
    String url;
    String urlupdaterating;
    String queueId;
    String memberId;
    RatingBar ratingBar;
    EditText reviewcustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Button updaterating =(Button)findViewById(R.id.button_send);
        ratingBar = (RatingBar)findViewById(R.id.ratingcustomer);
        reviewcustomer = (EditText)findViewById(R.id.reviewcustomer);
        url = getString(R.string.url);
        urlupdaterating = getString(R.string.updaterating);
        Bundle bundle= getIntent().getExtras();
        if (bundle!=null){
            queueId = bundle.getString("queueId");
            memberId = bundle.getString("mid");

            ratingupdate();
            updaterating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ratingupdate();
                    Toast.makeText(RatingActivity.this, "รีวิวสำเร็จ",Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void ratingupdate() {
       float ratingstar= 0;
//        if (ratingBar.getRating()>0){
            ratingstar =ratingBar.getRating();
//        }

        String comment = reviewcustomer.getText().toString();
        Ion.with(RatingActivity.this)
                .load(url+urlupdaterating)
                .setBodyParameter("id",memberId)
                .setBodyParameter("qid",queueId)
                .setBodyParameter("rating", String.valueOf(ratingstar))
                .setBodyParameter("review",comment)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        float score = result.get("score").getAsFloat();
                        if(result.get("reviews").toString()!=null){
                            String reviews = result.get("reviews").toString();
                            switch (reviews){
                                case "null":reviewcustomer.setText("");break;
                                default:
                                    String review = result.get("reviews").getAsString();
                                    reviewcustomer.setText(review);
                            }
                        }
                        if (score>0){
                            ratingBar.setRating(score);
                        }

                    }
                });
    }
}