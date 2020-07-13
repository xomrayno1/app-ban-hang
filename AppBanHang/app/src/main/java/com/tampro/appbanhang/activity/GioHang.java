package com.tampro.appbanhang.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.tampro.appbanhang.R;
import com.tampro.appbanhang.adapter.GioHangAdapter;
import com.tampro.appbanhang.ultil.CheckConnection;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {
    ListView lvgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnThanhtoan,btntieptucmua;
    Toolbar toolbargiohang;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        ActionToolBar();
        CheckData();
        EventUltil();
        CactchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhtoan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size() > 0){
                    Intent itent = new Intent(getApplicationContext(),Thongtinkhachhang.class);
                    startActivity(itent);
                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm để thanh toán");
                }
            }
        });
    }

    private void CactchOnItemListView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.manggiohang.size() <= 0 ){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.manggiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if(MainActivity.manggiohang.size() <= 0 ){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else{
                                txtthongbao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUltil();;
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        long tongtien = 0;
        for(int i = 0 ; i  < MainActivity.manggiohang.size() ; i++){
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + " Đ");
    }

    private void CheckData() {
        if(MainActivity.manggiohang.size() <= 0){
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }else{
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void Anhxa() {
        lvgiohang = (ListView) findViewById(R.id.listviewgiohang);
        txtthongbao = (TextView)findViewById(R.id.textviewthongbao);
        txttongtien = (TextView)findViewById(R.id.textviewtongtien);
        btnThanhtoan = (Button)findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua = (Button)findViewById(R.id.buttontieptucmuahang);
        toolbargiohang = (Toolbar)  findViewById(R.id.toolbargiohang);
        gioHangAdapter = new GioHangAdapter(GioHang.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(gioHangAdapter);

    }
}
