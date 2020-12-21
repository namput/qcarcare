package neua_th.qcar.rmutl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rmutl.R;

import java.util.ArrayList;

public class AtCustomITem {
    public int id;
    public String aname;
    public String size;
    public int time;
    public AtCustomITem(int mid, String mname, String msize, int mtime){
        this.id = mid;
        this.aname = mname;
        this.size = msize;
        this.time = mtime;
    }
}
class AtViewHolder {
    public TextView textViewTitle;

    public AtViewHolder(View cv){
        textViewTitle=(TextView)cv.findViewById(R.id.switch1);
    }
}
class AtCustomAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<AtCustomITem> mITems;
    private AtViewHolder mHolder;
    public AtCustomAdapter(Context context, ArrayList<AtCustomITem> items){
        super(context,0,items);
        mContext=context;
        mITems=items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater=LayoutInflater.from(mContext);
            convertView=inflater.inflate(R.layout.arow,parent,false);
            mHolder=new AtViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder=(AtViewHolder)convertView.getTag();
        }
        AtCustomITem item =mITems.get(position);
        mHolder.textViewTitle.setText(item.aname);

        return convertView;
    }
}
