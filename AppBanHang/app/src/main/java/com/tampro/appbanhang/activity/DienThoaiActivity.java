package com.tampro.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tampro.appbanhang.R;
import com.tampro.appbanhang.adapter.DienThoaiAdapter;
import com.tampro.appbanhang.model.Product;
import com.tampro.appbanhang.ultil.CheckConnection;
import com.tampro.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbardt;
    ListView lvdt;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<Product> mangdt;
    int iddt = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    mHandler mHandler;
    boolean limitdata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdloaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại internet");
            finish();
        }

    }

    private void LoadMoreData() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                itent.putExtra("thongtinsanpham",mangdt.get(position));
                startActivity(itent);
            }
        });
        lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int FirstItem, int visibleItem, int totalItemCount) {
                if( FirstItem + visibleItem == totalItemCount && totalItemCount != 0 && isLoading == false && limitdata == false ){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });


    }

    private void GetData(final int pages) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdandienthoai+String.valueOf(pages);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tendt = "";
                int Giadt = 0;
                String Hinhanhdt = "";
                String mota = "";
                int Idloaisanpham = 0 ;
                if(response != null && response.length() != 2){
                    lvdt.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0 ; i < jsonArray.length() ; i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensp");
                            Giadt = jsonObject.getInt("giasp");
                            Idloaisanpham = jsonObject.getInt("idcategory");
                            Hinhanhdt = jsonObject.getString("hinhanhsp");
                            mota  = jsonObject.getString("motasp");
                            Log.d("ten sanpham",Tendt+"");
                            mangdt.add(new Product(id,Tendt,Giadt,Hinhanhdt,Idloaisanpham,mota));

                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    limitdata = true;
                    lvdt.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idcategory",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdloaisp() {
        iddt = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("Giatriloaisanpham",iddt+"");

    }

    private void Anhxa() {
        toolbardt = (Toolbar) findViewById(R.id.toolbardienthoai);
        lvdt  = (ListView) findViewById(R.id.listViewdienthoai);
        mangdt = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(),mangdt);
        lvdt.setAdapter(dienThoaiAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }

    public class  mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvdt.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;

            }
            super.handleMessage(msg);
        }

    }
    public class ThreadData extends  Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

}
