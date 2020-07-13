package com.tampro.appbanhang.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;
import com.tampro.appbanhang.R;
import com.tampro.appbanhang.adapter.CategoryAdapter;
import com.tampro.appbanhang.adapter.ProductAdapter;
import com.tampro.appbanhang.model.Category;
import com.tampro.appbanhang.model.GioHang;
import com.tampro.appbanhang.model.Product;
import com.tampro.appbanhang.ultil.CheckConnection;
import com.tampro.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Category> mangloaisp;
    CategoryAdapter loaispAdapter;
    int id = 0 ;
    String tenloaisp = "";
    String hinhanhloaisp = "";
    ArrayList<Product> mangProduct;
    ProductAdapter productAdapter;
    public static ArrayList<GioHang> manggiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFliper();
            GetDuLieuLoaisp();
            GetDuLieuSPMoiNhat();
            CatchOnItemListView();

        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiếm tra lại kết nối");
            finish();;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menugiohang :
                Intent intent = new Intent(getApplicationContext(), com.tampro.appbanhang.activity.GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void CatchOnItemListView() {
        listViewManhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiếm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1 :
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiếm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2 :
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LapTopActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiếm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3 :
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiếm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4 :
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiếm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDuLieuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanspmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int ID = 0 ;
                    String tensp = "";
                    int giaSP = 0 ;
                    String hinhAnh = "";
                    String motasanpham = "";
                    int Idloaisanpham = 0;
                    for(int i = 0; i < response.length() ; i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            giaSP = jsonObject.getInt("giasp");
                            Idloaisanpham = jsonObject.getInt("idcategory");
                            tensp = jsonObject.getString("tensp");
                            hinhAnh = jsonObject.getString("hinhanhsp");
                            motasanpham  = jsonObject.getString("motasp");
                            mangProduct.add(new Product(ID, tensp,giaSP,hinhAnh,Idloaisanpham,motasanpham));
                            productAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuLoaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for(int i = 0 ; i < response.length() ;i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Category(id,tenloaisp,hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3,new Category(0,"Liên Hệ","https://www.freeiconspng.com/uploads/telephone-phone-icon-6.png"));
                    mangloaisp.add(4,new Category(0,"Thông Tin","https://www.freeiconspng.com/uploads/-20-mb-format-psd-color-theme-blue-white-keywords-information-icon-2.jpg"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString()+"rrr");
            }
        });


        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFliper() {
        ArrayList<String> mangQuangCao = new ArrayList<String>();
        mangQuangCao.add("https://cdn.tgdd.vn/2020/07/banner/sinh-nhat-800-300-800x300.png");
        mangQuangCao.add("https://cdn.tgdd.vn/2020/06/banner/reno3-800-300-800x300-1.png");
        mangQuangCao.add("https://cdn.tgdd.vn/2020/06/banner/800-300-800x300-31.png");
        mangQuangCao.add("https://cdn.tgdd.vn/2020/06/banner/800-300-800x300-32.png");
        for(String  items : mangQuangCao){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(items).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void anhXa(){
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFiliper);
        recyclerViewManHinhChinh =(RecyclerView) findViewById(R.id.recyclerview);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listViewManhinhchinh = (ListView)findViewById(R.id.listViewManHinhChinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Category(0,"Trang Chính","https://www.freeiconspng.com/uploads/address-building-company-home-house-office-real-estate-icon--10.png"));
        loaispAdapter = new CategoryAdapter(mangloaisp,getApplicationContext());
        listViewManhinhchinh.setAdapter(loaispAdapter);
        mangProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(),mangProduct);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        recyclerViewManHinhChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewManHinhChinh.setAdapter(productAdapter);
       if(manggiohang !=null){

       }else{
           manggiohang = new ArrayList<GioHang>();

       }
    }
}
