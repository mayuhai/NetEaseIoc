package com.netease.neteaseioc;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.netease.neteaseioc.annotation.ContentView;
import com.netease.neteaseioc.annotation.InjectView;
import com.netease.neteaseioc.annotation.OnClick;
import com.netease.neteaseioc.annotation.OnLongClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private Button btn;

    @InjectView(R.id.btn2)
    private Button btn2;
    @Override
    protected void onResume() {
        super.onResume();
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setText("btn2注解成功");
            }
        });

        btn2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @OnClick({R.id.btn})
    private void btnClick() {
        Toast.makeText(MainActivity.this, "btnClick() btn ", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn4)
    private void btn4OnClick(View view) {
        Toast.makeText(MainActivity.this, "btn4OnClick(View view) btn4", Toast.LENGTH_LONG).show();
    }

    @OnLongClick({R.id.btn3})
    private boolean btn3OnLongClick(View view) {
        Toast.makeText(MainActivity.this, "btn3OnLongClick()", Toast.LENGTH_LONG).show();
        return false;
    }

    @OnLongClick({R.id.btn})
    private void btnOnLongClick(View view) {
        Toast.makeText(MainActivity.this, "btnOnLongClick(View view)", Toast.LENGTH_LONG).show();
    }
}
