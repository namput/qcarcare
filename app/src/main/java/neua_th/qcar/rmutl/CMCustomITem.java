package neua_th.qcar.rmutl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rmutl.R;

import java.util.ArrayList;

public class CMCustomITem {
    public int mid;
    public String msername;
    public String mcarcolor;
    public String mcarnumber;
    public String mbrand;

    public CMCustomITem(int id,String sername,String color,String carnumber,String brand){
        this.mid=id;
        this.msername=sername;
        this.mcarcolor=color;
        this.mcarnumber=carnumber;
        this.mbrand=brand;
    }
}
class CMViewHolder{
    public TextView textViewTitle;
    public TextView textViewContent1;
    public TextView textViewContent2;
    public TextView textViewContent3;

    public CMViewHolder(View cv){
        textViewTitle=(TextView)cv.findViewById(R.id.textView1);
        textViewContent1=(TextView)cv.findViewById(R.id.textView2);
    }
}
class CMCustomAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<CMCustomITem> mITems;
    private CMViewHolder mHolder;

    public CMCustomAdapter(Context context, ArrayList<CMCustomITem> items){
        super(context,0,items);
        mContext=context;
        mITems=items;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        if (convertView==null){
            LayoutInflater inflater=LayoutInflater.from(mContext);
            convertView=inflater.inflate(R.layout.crow,parent,false);
            mHolder=new CMViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder=(CMViewHolder)convertView.getTag();
        }
        CMCustomITem item =mITems.get(pos);
        mHolder.textViewTitle.setText(item.msername);
        mHolder.textViewContent1.setText("ทะเบียนรถ : "+item.mcarnumber+" สี : "+item.mcarcolor+" ยี่ห้อรถ : "+ item.mbrand);

        return convertView;
    }
}


