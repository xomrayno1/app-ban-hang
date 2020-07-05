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

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListDienThoai;

    public DienThoaiAdapter() {

    }
    public DienThoaiAdapter(Context context, ArrayList<Product> arrayListDienThoai) {
        this.context = context;
        this.arrayListDienThoai = arrayListDienThoai;
    }

    @Override
    public int getCount() {
        return arrayListDienThoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListDienThoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttendienthoai,txtgiadienthoai,txtmotadienthoai;
        public ImageView imgdienthoai;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dienthoai,null);
            viewHolder.txttendienthoai = (TextView) view.findViewById(R.id.textviewdienthoai);
            viewHolder.txtgiadienthoai = (TextView) view.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtmotadienthoai = (TextView) view.findViewById(R.id.textviewmotadienthoai);
            viewHolder.imgdienthoai = (ImageView) view.findViewById(R.id.imageviewdienthoai);
            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txttendienthoai.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadienthoai.setText("Giá :"+decimalFormat.format(product.getPrice())+" Đ");
        viewHolder.txtmotadienthoai.setMaxLines(2);
        viewHolder.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotadienthoai.setText(product.getDescription());
        Picasso.get().load(product.getImages())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(viewHolder.imgdienthoai);

        return view;
    }
}
