package com.tampro.appbanhang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tampro.appbanhang.R;
import com.tampro.appbanhang.activity.MainActivity;
import com.tampro.appbanhang.model.GioHang;



import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter  extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arraygiohang;

    public GioHangAdapter(Context context, ArrayList<GioHang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus,btnplus,btnvalues;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengiohang = (TextView) view.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang = (TextView) view.findViewById(R.id.textviewgiamonhang);
            viewHolder.imggiohang = (ImageView) view.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus = (Button) view.findViewById(R.id.buttonminus);
            viewHolder.btnplus = (Button) view.findViewById(R.id.buttonplus);
            viewHolder.btnvalues = (Button) view.findViewById(R.id.buttonvalues);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(position);
        viewHolder.txttengiohang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(gioHang.getGiasp()) + " Đ");
        Picasso.get().load(gioHang.getHinhsp())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(viewHolder.imggiohang);
        viewHolder.btnvalues.setText(gioHang.getSoluongsp() + "");
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if(sl >= 10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if(sl <= 1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if(sl >= 1){
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) + 1;
                int slht = MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat ) / slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + " Đ");
                com.tampro.appbanhang.activity.GioHang.EventUltil();
                if(slmoinhat > 9){
                    finalViewHolder.btnplus.setVisibility(v.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(v.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }else{
                    finalViewHolder.btnminus.setVisibility(v.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(v.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) - 1;
                int slht = MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat ) / slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + " Đ");
                com.tampro.appbanhang.activity.GioHang.EventUltil();
                if(slmoinhat < 2){
                    finalViewHolder.btnminus.setVisibility(v.INVISIBLE);
                    finalViewHolder.btnplus.setVisibility(v.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }else{
                    finalViewHolder.btnminus.setVisibility(v.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(v.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });


        return view;
    }
}
