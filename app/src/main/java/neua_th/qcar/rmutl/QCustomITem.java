package neua_th.qcar.rmutl;

import android.content.Context;
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
    public int malltime;
    public QCustomITem(int id,String name,int num,int score,int alltime){
        this.mid = id;
        this.mname = name;
        this.mnum= num;
        this.mscore = score;
        this.malltime = alltime;
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
        mHolder.textViewContent1.setText("คิวที่เหลือ "+item.mnum+" คิว");
        mHolder.textViewContent2.setText("rating "+item.mscore);
        mHolder.textViewContent3.setText("เวลารวม "+item.malltime+" นาที");

        return convertView;
    }
}

//public class QCustomITem {
//    public int mid;
//    public String msername;
//    public String mcarcolor;
//    public String mcarnumber;
//    public String mbrand;
//
//    public QCustomITem(int id,String sername,String color,String carnumber,String brand){
//        this.mid=id;
//        this.msername=sername;
//        this.mcarcolor=color;
//        this.mcarnumber=carnumber;
//        this.mbrand=brand;
//    }
//}
