package com.example.quanlychitieu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

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

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener{
    public Spinner sp;
    private EditText eTitle,ePrice,eDate;
    private Button btUpdate,btBack,btRemove;
    private Item item ;
    private APIInterface apiInterface;
    private int idSaved = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        apiInterface = ApiClient.getClient().create(APIInterface.class);
        initView();

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        idSaved = item.getId();
        eTitle.setText(item.getTitle());
        ePrice.setText(item.getPrice());
        eDate.setText(item.getDate());
        int p = 0;
        for(int i = 0 ; i < sp.getCount();i++) {
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())) {
                p = i;
                break;
            }
        }
        sp.setSelection(p);
    }

    private void initView() {
        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.tvTitle);
        ePrice = findViewById(R.id.tvPrice);
        eDate = findViewById(R.id.tvDate);
        btUpdate = findViewById(R.id.btnUpdate);
        btRemove = findViewById(R.id.btnDelete);
        btBack = findViewById(R.id.btnBack);
        sp.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.category)));
        btBack.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SQLiteHelper db = new SQLiteHelper(this);
        if(view == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        if(view == btBack) {
            finish();
        }
        if(view == btUpdate) {
            Item item = new Item();
            item.setId(idSaved);
            item.setTitle(eTitle.getText().toString());
            item.setPrice(ePrice.getText().toString());
            item.setCategory(sp.getSelectedItem().toString());
            item.setDate(eDate.getText().toString());

            apiInterface.updateItem(item).enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {

                }
            });

        }
        if(view == btRemove) {
            int id = idSaved;
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Th??ng b??o");
            builder.setMessage("B???n c?? ch???c mu???n xo?? "+item.getTitle() +"kh??ng ?");
            builder.setIcon(R.drawable.remove);
            builder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    SQLiteHelper bb = new SQLiteHelper(getApplicationContext());
//                    bb.delete(id);
                    apiInterface.deleteItem(id).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            });
            builder.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
