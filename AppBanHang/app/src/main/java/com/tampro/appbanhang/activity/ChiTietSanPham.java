package com.tampro.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.tampro.appbanhang.R;
import com.tampro.appbanhang.model.GioHang;
import com.tampro.appbanhang.model.Product;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button btndatmua;
    int id = 0;
    String TenChitiet = "";
    int GiaChitiet = 0 ;
    String HinhanhChitiet = "";
    String MotaChitiet = "";
    int Idsanpham = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEvenSpinner();
        EventButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menugiohang :
                Intent intent = new Intent(getApplicationContext(), com.tampro.appbanhang.activity.GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for(int i = 0 ;  i < MainActivity.manggiohang.size() ; i++){
                        if(MainActivity.manggiohang.get(i).getIdsp() == Idsanpham){
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl);
                            if(MainActivity.manggiohang.get(i).getSoluongsp() >= 10){
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(GiaChitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if(exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * GiaChitiet;
                        MainActivity.manggiohang.add(new GioHang(Idsanpham,TenChitiet,Giamoi,HinhanhChitiet,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * GiaChitiet;
                    MainActivity.manggiohang.add(new GioHang(Idsanpham,TenChitiet,Giamoi,HinhanhChitiet,soluong));
                }
                Intent intent = new Intent(getApplicationContext(), com.tampro.appbanhang.activity.GioHang.class);
                startActivity(intent);

            }
        });
    }

    private void CatchEvenSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {


        Product product = (Product) getIntent().getSerializableExtra("thongtinsanpham");
        Idsanpham = product.getId();
        TenChitiet = product.getName();
        GiaChitiet = product.getPrice();
        HinhanhChitiet = product.getImages();
        id = product.getIdCategory();
        MotaChitiet = product.getDescription();

        txtten.setText(TenChitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá  "+ decimalFormat.format(GiaChitiet) + " Đ");
        txtmota.setText(MotaChitiet);
        Picasso.get().load(HinhanhChitiet)
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_foreground)
        .into(imgChitiet);
        Log.d("Giatrisanpham",Idsanpham+"");

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChitiet = (Toolbar) findViewById(R.id.toolbarchitietsanpham);
        imgChitiet = (ImageView) findViewById(R.id.imageviewchitietsanpham);
        txtten = (TextView) findViewById(R.id.textviewtenchitietsanpham);
        txtgia = (TextView) findViewById(R.id.textviewgiachitietsanpham);
        txtmota = (TextView) findViewById(R.id.textviewmotachitietsanpham);
        spinner = (Spinner) findViewById(R.id.spinner);
        btndatmua = (Button) findViewById(R.id.buttondatmua);
    }
}
