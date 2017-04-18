package play.fzh.bj.notepad.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import play.fzh.bj.notepad.R;

/**
 * 项目名称：NotePadTips
 * 类描述：
 * 创建人：fuzh2
 * 创建时间：2017/4/17 15:05
 * 修改人：fuzh2
 * 修改时间：2017/4/17 15:05
 * 修改备注：
 */

public class TipsItem extends FrameLayout implements View.OnClickListener {

    private TextView adTop;
    private TextView adBottom;
    private LinearLayout llTop;
    private LinearLayout llBottom;

    private List<String> mDatas;

    public TipsItem(Context context) {
        this(context, null);
    }

    public TipsItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
        mDatas = new ArrayList<>(2);
    }

    private void initLayout() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_notice_ads, null);
        adTop = (TextView) inflate.findViewById(R.id.tvAdTop);
        adBottom = (TextView) inflate.findViewById(R.id.tvAdBottom);
        llTop = (LinearLayout) inflate.findViewById(R.id.llAdTop);
        llBottom = (LinearLayout) inflate.findViewById(R.id.llAdBottom);

        llTop.setOnClickListener(this);
        llBottom.setOnClickListener(this);
        addView(inflate);
    }

    public void updateData(List<String> pDatas) {
        if (pDatas != null && !pDatas.isEmpty()) {
            mDatas.clear();
            mDatas.addAll(pDatas);
        }

        if (mDatas.isEmpty())
            return;

        adTop.setText(mDatas.get(0));
        if (mDatas.size() < 2) {
            adBottom.setText(mDatas.get(0));
            llBottom.setVisibility(GONE);
        } else {
            adBottom.setText(mDatas.get(1));
        }

        llTop.setTag(adTop.getText());
        llBottom.setTag(adBottom.getText());
    }

    @Override
    public void onClick(View pView) {
        Toast.makeText(getContext(), (String) pView.getTag(), Toast.LENGTH_SHORT).show();
    }
}
