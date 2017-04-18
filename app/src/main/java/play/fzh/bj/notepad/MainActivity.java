package play.fzh.bj.notepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import play.fzh.bj.notepad.view.LooperStickView;
import play.fzh.bj.notepad.view.ProgressView;

public class MainActivity extends AppCompatActivity {


    private List<String> strs = new ArrayList<>(5);
    private List<String> str2 = new ArrayList<>(5);
    private LooperStickView loopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressView taskView = (ProgressView) findViewById(R.id.taskView);
        taskView.setProgress(60);

        taskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
//                ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//                dialog.show();
                loopView.setTipList(str2);
            }
        });

        for (int i = 0; i < 7; i++) {
            strs.add("hhhhhhh= " + i);
        }
        for (int i = 0; i < 9; i++) {
            str2.add("ttttttttttttttttt= " + i);
        }

        loopView = (LooperStickView) findViewById(R.id.loopView);
        loopView.setTipList(strs);
    }
}
