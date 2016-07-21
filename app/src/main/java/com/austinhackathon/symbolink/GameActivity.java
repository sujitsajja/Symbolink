package com.austinhackathon.symbolink;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ArrayList<String[]> fileContents = new ArrayList<>();
        clickDataStack = new Stack<>();
        MapLoader mapLoader = new MapLoader();
        Intent intent = getIntent();
        String level = intent.getStringExtra("GAME_LEVEL");
        int totalTime=0;
        switch (level){
            case "Easy":
                grid = mapLoader.createGrid(MapData.map1);
                fileContents = MapData.formattedMap(MapData.map1);
                totalTime=90000;
                break;
            case "Medium":
                grid = mapLoader.createGrid(MapData.map2);
                fileContents = MapData.formattedMap(MapData.map2);
                totalTime=120000;
                break;
            case "Hard":
                grid = mapLoader.createGrid(MapData.map3);
                fileContents = MapData.formattedMap(MapData.map3);
                totalTime=150000;
                break;
        }
        countOfValidNode = new Integer(fileContents.size()*fileContents.get(0).length);
        setGrid(fileContents);
        final TextView countView = (TextView) findViewById(R.id.mTextField);
        timer = new CountDownTimer(totalTime, 1000) {
            public void onTick(long millisUntilFinished) {
                countView.setText("seconds remaining: " + millisUntilFinished / 1000);
                remainingTime = millisUntilFinished;
            }
            public void onFinish() {
                countView.setText("Times up!");
            }
        }.start();
    }

    private void setGrid(ArrayList<String[]> map) {
        int numRow = map.size();
        int numCol = map.get(0).length;
        TableLayout gameBoard = (TableLayout) findViewById(R.id.GameBoard);
        for (int i=0; i < numRow; ++i) {
            TableRow row = new TableRow(this);
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5,-10,-10,-10);
            row.setLayoutParams(params);
            row.setGravity(Gravity.CENTER);
            for (int j=0;j<numCol;++j){
                final ImageButton tableCell = new ImageButton(this);
                tableCell.setBackgroundColor(Color.TRANSPARENT);
                String t = map.get(i)[j];
                final String uri = "@drawable/a"+t;
                if (!t.equals("0") ){
                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                    Drawable res = getResources().getDrawable(imageResource);
                    tableCell.setImageDrawable(res);
                } else {
                    --countOfValidNode;
                    int imageResource = getResources().getIdentifier("@drawable/a"+1, null, getPackageName());
                    Drawable res = getResources().getDrawable(imageResource);
                    tableCell.setImageDrawable(res);
                    tableCell.setVisibility(View.INVISIBLE);
                }
                tableCell.setId(10*i+j);
                final MediaPlayer mp = MediaPlayer.create(this, R.raw.beep1);
                tableCell.setOnClickListener(new View.OnClickListener(){
                     @Override
                     public void onClick(View v) {
                     Pair<Integer, String> cellContents = new Pair<>((Integer.valueOf(v.getId())),uri);
                     if (clickDataStack.empty()) {
                         clickDataStack.push(cellContents);
                     } else {
                        Pair<Integer,String> topClick = clickDataStack.peek();
                        if (topClick.first==cellContents.first) {
                            clickDataStack.pop();
                        } else if (topClick.first!=cellContents.first && !(topClick.second.toString()).equals(cellContents.second.toString())) {
                            clickDataStack.pop();
                            clickDataStack.push(cellContents);
                        } else {
                            int row1 = topClick.first/10;
                            int col1 = topClick.first%10;
                            int row2 = cellContents.first/10;
                            int col2 = cellContents.first%10;
                            Path pathExist=grid.linkTwoCells(row1,col1,row2,col2) ;
                            if (pathExist.isValid) {
                                ArrayList<Integer> pathData = pathExist.getPathForGame();
                                for(int i=0;i<pathData.size();i++){
                                    ImageButton pathElem = (ImageButton) findViewById(pathData.get(i));
                                    pathElem.setVisibility(View.VISIBLE);
                                    pathElem.setBackgroundColor(Color.TRANSPARENT);
                                    pathElem.setImageResource(R.drawable.star);
                                }
                                for(int i=0;i<pathData.size();i++){
                                    final ImageButton pathElem = (ImageButton) findViewById(pathData.get(i));
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            pathElem.setVisibility(View.INVISIBLE);
                                        }
                                    }, 300);
                                }
                                try {
                                    mp.prepare();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                mp.start();
                                countOfValidNode -= 2;
                                //find the first button and hide it too
                                if (countOfValidNode==0){
                                    AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
                                    alertDialog.setTitle("Success");
                                    alertDialog.setMessage("You win the Game!\n Your score is "+remainingTime);
                                    timer.cancel();
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                onBackPressed();
                                            }
                                        });
                                    alertDialog.show();
                                }
                            }
                            clickDataStack.pop();
                        }
                     }
                     }
                });
                row.addView(tableCell);
            }
            gameBoard.addView(row);
        }
    }

    private Stack<Pair<Integer,String>> clickDataStack;
    private Grid grid;
    Integer countOfValidNode;
    private CountDownTimer timer;
    long remainingTime;

}
