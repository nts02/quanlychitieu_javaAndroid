package com.example.quanlychitieu;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.dal.SQLiteHelper;
import com.example.quanlychitieu.model.Item;
import com.example.quanlychitieu.network.APIInterface;
import com.example.quanlychitieu.network.ApiClient;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    public Spinner sp;
    private EditText eTitle,ePrice,eDate;
    private Button btUpdate,btCancel;
    private APIInterface apiInterface;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        apiInterface = ApiClient.getClient().create(APIInterface.class);
        initView();

        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    private void initView() {
        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.tvTitle);
        ePrice = findViewById(R.id.tvPrice);
        eDate = findViewById(R.id.tvDate);
        btUpdate = findViewById(R.id.btUpdate);
        btCancel = findViewById(R.id.btCanel);
        sp.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View view) {
        if(view == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if(m > 8 && d > 8) {
                        date = d +"/" +(m+1) + "/" + y;
                    }
                    else if(m >8 && d <= 8 ) {
                        date = "0" + d + "/" +(m+1) + "/" + y;
                    }
                    else if(m <= 8 && d > 8) {
                        date= d + "/0" + (m+1) + "/" +y;
                    }
                    else {
                        date= "0"+ d+"/0" + (m+1) + "/" +y;
                    }
                    eDate.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(view == btCancel) {
            finish();
        }
        if(view == btUpdate) {
            Log.d("PTIT", "onClick: AAAAAA");
            Item item = new Item();
//            String  t = eTitle.getText().toString();
//            String  p = ePrice.getText().toString();
//            String  c = sp.getSelectedItem().toString();
//            String d = eDate.getText().toString();
            item.setTitle(eTitle.getText().toString());
            item.setPrice(ePrice.getText().toString());
            item.setCategory(sp.getSelectedItem().toString());
            item.setDate(eDate.getText().toString());

            apiInterface.createItem(item).enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {

                }
            });

//            if(!t.isEmpty() && p.matches("\\d+")){
//                Item i = new Item(t, c, p, d);
//                SQLiteHelper db = new SQLiteHelper(this);
//                db.addItem(i);
//                finish();
//            }
        }
    }
}
