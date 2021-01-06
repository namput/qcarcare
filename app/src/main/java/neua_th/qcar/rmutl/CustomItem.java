package neua_th.qcar.rmutl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rmutl.R;

import java.util.ArrayList;

class CustomItem {
    public String title;
    public String content;
    public CustomItem(String title, String content){
        this.title=title;
        this.content=content;
    }
}
class ViewHolder{
    public TextView textViewTitle;
    public TextView textViewContent;
    public ViewHolder(View cv){
        textViewTitle=(TextView)cv.findViewById(R.id.textView1);
        textViewContent=(TextView)cv.findViewById(R.id.textView2);
    }
}
class CustomAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<CustomItem> mITems;
    private ViewHolder mHolder;

    public CustomAdapter(Context context, ArrayList<CustomItem> items){
        super(context,0,items);
        mContext=context;
        mITems=items;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        if (convertView==null){
            LayoutInflater inflater=LayoutInflater.from(mContext);
            convertView=inflater.inflate(R.layout.row,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder=(ViewHolder)convertView.getTag();
        }
        CustomItem item =mITems.get(pos);
        mHolder.textViewTitle.setText(item.title);
        mHolder.textViewContent.setText(item.content);

        return convertView;
    }
}