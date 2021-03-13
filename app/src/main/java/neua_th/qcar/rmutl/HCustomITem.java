package neua_th.qcar.rmutl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.rmutl.R;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HCustomITem {
    public String qid;
    public String carcare;
    public String dates;
    public String cmnumber;
    public String sername;
    public String status;

    public HCustomITem(String qid,String carcare,String date,String cmnumber,String sername,String status){
        this.qid=qid;
        this.carcare=carcare;
        this.dates =date;
        this.cmnumber=cmnumber;
        this.sername=sername;
        this.status = status;
    }
}

class HViewHolder{
    public TextView textViewTitle;
    public TextView textViewContent1;
    public LinearLayout linearLayout;

    public HViewHolder(View cv){
        textViewTitle=(TextView)cv.findViewById(R.id.textView1);
        textViewContent1=(TextView)cv.findViewById(R.id.textView2);
        linearLayout = (LinearLayout)cv.findViewById(R.id.contact);
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

        String inputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        int year=0,month=0,day=0;
        Date date=null;
        String Months[] = {
                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};
        try {
            date = inputFormat.parse(item.dates);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String dates = String.format("%s %s %s ", day,Months[month],year+543);
        String content=item.sername+" ทะเบียนรถ "+item.cmnumber+"\n"+dates;
        mHolder.textViewContent1.setText(content);
        switch (item.status){

            case "3":mHolder.linearLayout.setBackground(getContext().getDrawable( R.drawable.shapehistory1));
                mHolder.textViewTitle.setTextColor(Color.BLUE);
                mHolder.textViewTitle.setTextColor(Color.BLUE);
                mHolder.textViewContent1.setText(content+" สถานะ สำเร็จ");
            break;
            case "4":mHolder.textViewContent1.setText(content+" สถานะ ยกเลิก");
                mHolder.textViewTitle.setTextColor(Color.WHITE);
                mHolder.linearLayout.setBackground(getContext().getDrawable( R.drawable.shapehistory));
                break;
        }

        return convertView;
    }
//    ฟังก์ชันแปลงวันที่เป็นไทย
//    public static String dateThai(String strDate)
//    {
//        String Months[] = {
//                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
//                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
//                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};
//        String inputPattern = "yyyy-MM-dd";
//        SimpleDateFormat df = new SimpleDateFormat(inputPattern);
//
//        int year=0,month=0,day=0;
//        Date date=null;
//        try {
//             date = df.parse(strDate);
//            Calendar c = Calendar.getInstance();
//            c.setTime(date);
//
//            year = c.get(Calendar.YEAR);
//            month = c.get(Calendar.MONTH);
//            day = c.get(Calendar.DATE);
//
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return String.format("%s %s %s ", day,Months[month],year+543);
//    }
}



