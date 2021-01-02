package neua_th.qcar.rmutl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
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
    AlertDialog.Builder dialogBuilder;
    View layoutView;
    AlertDialog alertDialog;
    Button getout;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Button updaterating =(Button)findViewById(R.id.button_send);
        ratingBar = (RatingBar)findViewById(R.id.ratingcustomer);
        dialogBuilder = new AlertDialog.Builder(RatingActivity.this);
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