package com.tampro.appbanhang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tampro.appbanhang.R;
import com.tampro.appbanhang.model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LapTopAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> arrayLaptop;

    public LapTopAdapter() {

    }
    public LapTopAdapter(Context context, ArrayList<Product> arrayLaptop) {
        this.context = context;
        this.arrayLaptop = arrayLaptop;
    }

    @Override
    public int getCount() {
        return arrayLaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayLaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txttenlaptop,txtgialaptop,txtmotalaptop;
        public ImageView imglaptop;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptop,null);
            viewHolder.txttenlaptop = (TextView) view.findViewById(R.id.textviewlaptop);
            viewHolder.txtgialaptop = (TextView) view.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmotalaptop = (TextView) view.findViewById(R.id.textviewmotalaptop);
            viewHolder.imglaptop = (ImageView) view.findViewById(R.id.imageviewlaptop);
            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txttenlaptop.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgialaptop.setText("Giá :"+decimalFormat.format(product.getPrice())+" Đ");
        viewHolder.txtmotalaptop.setMaxLines(2);
        viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotalaptop.setText(product.getDescription());
        Picasso.get().load(product.getImages())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(viewHolder.imglaptop);

        return view;
    }
}
