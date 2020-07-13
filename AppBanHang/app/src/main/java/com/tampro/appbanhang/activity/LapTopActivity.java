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
import android.view.Menu;
import android.view.MenuItem;
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
import com.tampro.appbanhang.adapter.LapTopAdapter;
import com.tampro.appbanhang.model.Product;
import com.tampro.appbanhang.ultil.CheckConnection;
import com.tampro.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LapTopActivity extends AppCompatActivity {

    Toolbar toolbarlaptop;
    ListView lvlaptop;
    LapTopAdapter lapTopAdapter;
    ArrayList<Product> manglaptop;
    int idLaptop = 0;
    int page = 1;

    View footerview;
    boolean isLoading = false;
    mHandler mHandler;
    boolean limitdata = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_top);

       if(CheckConnection.haveNetworkConnection(getApplicationContext())){
           Anhxa();
           GetIdloaisp();
           ActionToolbar();
           GetData(page);
           LoadMoreData();
       }else{
           CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối internet");
           finish();
       }
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

    private void Anhxa() {
        toolbarlaptop = (Toolbar) findViewById(R.id.toolbarlaptop);
        lvlaptop  = (ListView) findViewById(R.id.listViewlaptop);
        manglaptop = new ArrayList<>();
        lapTopAdapter = new LapTopAdapter(getApplicationContext(),manglaptop);
        lvlaptop.setAdapter(lapTopAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }
    private void LoadMoreData() {
        lvlaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                itent.putExtra("thongtinsanpham",manglaptop.get(position));
                startActivity(itent);
            }
        });
        lvlaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
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
    private void GetIdloaisp() {
        idLaptop = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("Giatriloaisanpham",idLaptop+"");

    }
    private void ActionToolbar() {
        setSupportActionBar(toolbarlaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlaptop.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
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
                String Tenlaptop = "";
                int Giadt = 0;
                String Hinhanhlaptop = "";
                String motaLaptop = "";
                int Idloaisanpham = 0 ;
                if(response != null && response.length() != 2){
                    lvlaptop.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0 ; i < jsonArray.length() ; i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tenlaptop = jsonObject.getString("tensp");
                            Giadt = jsonObject.getInt("giasp");
                            Idloaisanpham = jsonObject.getInt("idcategory");
                            Hinhanhlaptop = jsonObject.getString("hinhanhsp");
                            motaLaptop  = jsonObject.getString("motasp");
                            Log.d("ten sanpham",Tenlaptop+"");
                            manglaptop.add(new Product(id,Tenlaptop,Giadt,Hinhanhlaptop,Idloaisanpham,motaLaptop));

                            lapTopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    limitdata = true;
                    lvlaptop.removeFooterView(footerview);
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
                param.put("idcategory",String.valueOf(idLaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }
    public class  mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvlaptop.addFooterView(footerview);
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
