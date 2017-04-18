package play.fzh.bj.notepad;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import play.fzh.bj.notepad.view.ProgressView;

/**
 * 项目名称：NotePadTips
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2017/4/17 14:26
 * 修改人：fuzh2
 * 修改时间：2017/4/17 14:26
 * 修改备注：
 */

public class ProgressDialog extends Dialog implements DialogInterface.OnShowListener {

    private ProgressView mViewrogressView;

    public ProgressDialog(Context context) {
        super(context);
    }

    public ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
    }

    private void initWindow() {
        setContentView(R.layout.dialog_view);
        mViewrogressView = (ProgressView) findViewById(R.id.taskView);
        setOnShowListener(this);
    }


    @Override
    public void onShow(DialogInterface pDialogInterface) {
        mViewrogressView.setProgress(69);
    }
}
