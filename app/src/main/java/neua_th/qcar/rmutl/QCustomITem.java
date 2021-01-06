package neua_th.qcar.rmutl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmutl.R;

import java.util.ArrayList;


public class QCustomITem {
    public int mid;
    public String mname;
    public int mnum;
    public int mscore;
    public int mhour;
    public int minute;
    public QCustomITem(int id, String name, int num, int score, int hour,int minute){
        this.mid = id;
        this.mname = name;
        this.mnum= num;
        this.mscore = score;
        this.mhour = hour;
        this.minute = minute;
    }
        }
class QViewHolder{
    public TextView textViewTitle;
    public TextView textViewContent1;
    public RatingBar ratingBar;

    public QViewHolder(View cv){
        textViewTitle=(TextView)cv.findViewById(R.id.textView1);
        textViewContent1=(TextView)cv.findViewById(R.id.textView2);
        ratingBar=(RatingBar)cv.findViewById(R.id.ratingcarcare);
    }
}
class QCustomAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<QCustomITem> mITems;
    private QViewHolder mHolder;

    public QCustomAdapter(Context context, ArrayList<QCustomITem> items){
        super(context,0,items);
        mContext=context;
        mITems=items;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        if (convertView==null){
            LayoutInflater inflater=LayoutInflater.from(mContext);
            convertView=inflater.inflate(R.layout.qrow,parent,false);
            mHolder=new QViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder=(QViewHolder)convertView.getTag();
        }
        QCustomITem item =mITems.get(pos);
        mHolder.textViewTitle.setText(item.mname);
        mHolder.textViewContent1.setTextColor(Color.parseColor("#64DD17"));
//        mHolder.textViewContent1.setText("คิวว่าง");
        String content="คิวว่าง";
        if (item.mnum>0){
            content="เหลืออีก "+item.mnum+" คิว";
            mHolder.textViewContent1.setTextColor(Color.parseColor("#F57F17"));
        }

        if (item.minute>0 || item.mhour>0){
            content+="  เหลืออีกประมาณ ";
        }
        if (item.mhour>0){
            content+=item.mhour+" ชั่วโมง ";
        }
        if (item.minute>0){
            content+=item.minute+" นาที";
        }
        mHolder.textViewContent1.setText(content);
        mHolder.ratingBar.setRating(item.mscore);

        mHolder.ratingBar.setIsIndicator(true);
        return convertView;
    }
}

