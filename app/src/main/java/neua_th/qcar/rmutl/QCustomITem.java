package neua_th.qcar.rmutl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
    public TextView textViewContent2;
    public TextView textViewContent3;

    public QViewHolder(View cv){
        textViewTitle=(TextView)cv.findViewById(R.id.textView1);
        textViewContent1=(TextView)cv.findViewById(R.id.textView2);
        textViewContent2=(TextView)cv.findViewById(R.id.textView);
        textViewContent3=(TextView)cv.findViewById(R.id.textView3);
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
        mHolder.textViewContent1.setText("ยังไม่มีคิว");
        if (item.mnum>0){
            mHolder.textViewContent1.setText("อีก "+item.mnum+" คิว");
            mHolder.textViewContent1.setTextColor(Color.parseColor("#F57F17"));
        }

        mHolder.textViewContent2.setText("rating "+item.mscore);
        String h="คิวว่าง";
        mHolder.textViewContent3.setTextColor(Color.parseColor("#64DD17"));
        if (item.minute>0 || item.mhour>0){
            h="เหลืออีกประมาณ ";
            mHolder.textViewContent3.setTextColor(Color.parseColor("#F57F17"));
        }
        if (item.mhour>0){
            h+=item.mhour+" ชั่วโมง ";
        }
        if (item.minute>0){
            h+=item.minute+" นาที";
        }


        mHolder.textViewContent3.setText(h);

        return convertView;
    }
}

