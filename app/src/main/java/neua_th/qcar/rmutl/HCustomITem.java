package neua_th.qcar.rmutl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rmutl.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HCustomITem {
    public String qid;
    public String carcare;
    public String date;
    public String cmnumber;
    public String sername;

    public HCustomITem(String qid,String carcare,String date,String cmnumber,String sername){
        this.qid=qid;
        this.carcare=carcare;
        this.date=date;
        this.cmnumber=cmnumber;
        this.sername=sername;
    }
}

class HViewHolder{
    public TextView textViewTitle;
    public TextView textViewContent1;

    public HViewHolder(View cv){
        textViewTitle=(TextView)cv.findViewById(R.id.textView1);
        textViewContent1=(TextView)cv.findViewById(R.id.textView2);
    }
}

class HCustomAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<HCustomITem> mITems;
    private HViewHolder mHolder;

    public HCustomAdapter(Context context, ArrayList<HCustomITem> items){
        super(context,0,items);
        mContext=context;
        mITems=items;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        if (convertView==null){
            LayoutInflater inflater=LayoutInflater.from(mContext);
            convertView=inflater.inflate(R.layout.row,parent,false);
            mHolder=new HViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder=(HViewHolder)convertView.getTag();
        }
        HCustomITem item =mITems.get(pos);
        mHolder.textViewTitle.setText(item.carcare);

        String content=item.sername+" ทะเบียนรถ "+item.cmnumber+"\n วันที่ "+item.date;
        mHolder.textViewContent1.setText(content);

        return convertView;
    }
}


