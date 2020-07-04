package com.tampro.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tampro.appbanhang.R;
import com.tampro.appbanhang.model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    Context context;
    ArrayList<Product> arrayProduct;

    public ProductAdapter() {

    }
    public ProductAdapter(Context context, ArrayList<Product> arrayProduct) {
        this.context = context;
        this.arrayProduct = arrayProduct;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Product> getArrayProduct() {
        return arrayProduct;
    }

    public void setArrayProduct(ArrayList<Product> arrayProduct) {
        this.arrayProduct = arrayProduct;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder = new ItemHolder(view);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product = arrayProduct.get(position);
        holder.txtTenSanPham.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSanPham.setText("Giá :"+decimalFormat.format(product.getPrice())+" Đ");
        Picasso.get().load(product.getImages())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imgHinhAnhSanPham);

    }

    @Override
    public int getItemCount() {
        return arrayProduct.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgHinhAnhSanPham;
        public TextView txtTenSanPham,txtGiaSanPham;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhAnhSanPham = (ImageView) itemView.findViewById(R.id.imageviewsanpham);
            txtGiaSanPham = (TextView) itemView.findViewById(R.id.textviewgiasanpham);
            txtTenSanPham = (TextView) itemView.findViewById(R.id.textviewtensanpham);
        }
    }
}
