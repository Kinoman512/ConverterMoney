package test.ru.convertermoney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import test.ru.convertermoney.model.ValCurs;
import test.ru.convertermoney.model.Valute;

public class MainActivity extends AppCompatActivity implements Downloadcallback {

    String xml_money = "";
    private Spinner spin1;
    private Spinner spin2;
    private EditText et_value;
    private TextView tv_result;


    int posSpin1 = 0;
    int posSpin2 = 1;
    private ValCurs valCurs;

    private LinearLayout linear_pb;
    private LinearLayout linear_main;
    private LinearLayout linear_data_not_load;


    static String CONST_DATE = "CONST_DATE";
    private String currentDate;


    void showPB(boolean bl) {
        if (bl) {
            linear_main.setVisibility(View.GONE);
            linear_pb.setVisibility(View.VISIBLE);
        } else {
            linear_main.setVisibility(View.VISIBLE);
            linear_pb.setVisibility(View.GONE);
        }
    }


    void initViews() {
        List<String> valueteNames = new ArrayList<>();

        if(valCurs == null){
            linear_data_not_load.setVisibility(View.VISIBLE);
            linear_pb.setVisibility(View.GONE);
            linear_main.setVisibility(View.GONE);
            return;
        }

        showPB(false);
        for (Valute e : valCurs.valutes) {
            valueteNames.add(e.Nominal + " " + e.Name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valueteNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);
        spin2.setAdapter(adapter);

        spin1.setSelection(posSpin1);
        spin2.setSelection(posSpin2);
    }


    @Override
    public void onDownload(String xml_money) {
        valCurs = EntityUtil.getValCurs(xml_money);

        if(xml_money.isEmpty()){
            valCurs = Cache.read(this);
            initViews();
            return;
        }
        Cache.write(this, valCurs);
        Setting.setString(CONST_DATE, currentDate);
        initViews();

    }

    @Override
    public void onEndDownload() {
        showPB(false);
    }

    @Override
    public void onBeginLoad() {
        showPB(true);
    }

    void startDownload() {
        DownloadMoneyTask dm = new DownloadMoneyTask(this);
        dm.execute();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Setting.init(this);


        setContentView(R.layout.activity_main);

        spin1 = (Spinner) findViewById(R.id.spin1);
        spin2 = (Spinner) findViewById(R.id.spin2);
        et_value = (EditText) findViewById(R.id.et_value);
        tv_result = (TextView) findViewById(R.id.tv_result);

        linear_main = (LinearLayout) findViewById(R.id.linear_main);
        linear_pb = (LinearLayout) findViewById(R.id.linear_pb);
        linear_data_not_load = (LinearLayout) findViewById(R.id.linear_data_not_load);

        tv_result.setText("");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        currentDate = sdf.format(new Date());


        if (Setting.getString(CONST_DATE).equals(currentDate)) {
            valCurs = Cache.read(this);
            initViews();
            if (valCurs == null) {
                startDownload();
            }
        } else {
            startDownload();
        }


        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posSpin1 = position;
                updateResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posSpin2 = position;
                updateResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        et_value.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                updateResult();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }


    void updateResult() {

        if (et_value.getText().toString().isEmpty()) {
            tv_result.setText("");
            return;
        }

        double number = Float.valueOf(et_value.getText().toString());

        Valute v1 = valCurs.valutes.get(posSpin1);
        Valute v2 = valCurs.valutes.get(posSpin2);

        if (v1.Value.contains(",")) {
            v1.Value = v1.Value.replace(",", ".");
        }

        if (v2.Value.contains(",")) {
            v2.Value = v2.Value.replace(",", ".");
        }

        double val1 = Float.valueOf(v1.Value);
        double val2 = Float.valueOf(v2.Value);

        String res = String.valueOf(number * val2 / val1);
        tv_result.setText(res);
    }


}
