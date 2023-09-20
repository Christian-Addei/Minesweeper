package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    private static final int COLUMN_COUNT = 10;

    private ArrayList<TextView> cell_tvs;

    private int dpToPixel(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cell_tvs = new ArrayList<TextView>();

        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout01);
        for (int i = 0; i<=11; i++) {
            for (int j=0; j<=9; j++) {
                TextView tv = new TextView(this);
                tv.setHeight( dpToPixel(30) );
                tv.setWidth( dpToPixel(30) );
                tv.setTextSize( 15 );//dpToPixel(32) );
                tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                tv.setTextColor(Color.GRAY);
                tv.setBackgroundColor(Color.GRAY);
                tv.setOnClickListener(this::onClickTV);

                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.setMargins(dpToPixel(2), dpToPixel(2), dpToPixel(2), dpToPixel(2));
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);

                grid.addView(tv, lp);

                cell_tvs.add(tv);
            }
        }

    }

    private int findIndexOfCellTextView(TextView tv) {
        for (int n=0; n<cell_tvs.size(); n++) {
            if (cell_tvs.get(n) == tv)
                return n;
        }
        return -1;
    }

    private int isMine(int i, int j) {
        if ((i == 1 && j == 2) || (i == 3 && j == 6) || (i == 8 && j == 2) || (i == 6 && j == 7)) {
            return 1;
        } else return 0;
    }

//    public void flood(int i, int j, TextView tv) {
//        if (i < 0 || i > 10 || j < 0 || j > 12) return;
//        if (isMine(i,j) == 1) return;
//        tv.setTextColor(Color.GRAY);
//        tv.setBackgroundColor(Color.LTGRAY);
//        flood(i,j-1,tv);
//        flood(i,j+1,tv);
//        flood(i-1,j,tv);
//        flood(i+1,j,tv);
//    }

    public void onClickTV(View view){
        TextView tv = (TextView) view;
        int n = findIndexOfCellTextView(tv);
        int i = n/COLUMN_COUNT;
        int j = n%COLUMN_COUNT;
        int adj = 0;
        int mine = 0;
        if (isMine(i,j) == 1) {
            mine = 1;
        }
        for (int x = i-1; x <= i+1; x++) {
            for (int y = j-1; y <= j+1; y++) {
                if (isMine(x,y) == 1 && (x != i || y != j)) {
                    adj++;
                }
            }
        }
        if (adj >0) tv.setText(String.valueOf(adj));
        else if (isMine(i,j) == 1) tv.setText("X");
        else {
            tv.setText(" ");
        }
        if (tv.getCurrentTextColor() == Color.GRAY) {
            tv.setTextColor(Color.GREEN);
            tv.setBackgroundColor(Color.parseColor("lime"));
        }else {
            tv.setTextColor(Color.GRAY);
            tv.setBackgroundColor(Color.LTGRAY);
//            flood(i,j,tv);
        }
    }

}