package play.fzh.bj.notepad.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class LooperStickView extends FrameLayout {
    private List<String> tipList = new ArrayList<>(2);
    private int curTipIndex = 0;
    private boolean isTipsIn;
    private long lastTimeMillis;

    /**
     * 动画持续时长
     */
    private static final int ANIM_DURATION = 1500;
    private static final int ANIM_DELAYED_MILLIONS = 2 * 1000;

    private TipsItem view_tip_out, view_tip_in;
    private Animation anim_out, anim_in;

    public LooperStickView(Context context) {
        super(context);
        initTipFrame();
        initAnimation();
    }

    public LooperStickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTipFrame();
        initAnimation();
    }

    public LooperStickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTipFrame();
        initAnimation();
    }

    private void initTipFrame() {
        view_tip_out = newItemView();
        view_tip_in = newItemView();
        addView(view_tip_in);
        addView(view_tip_out);
    }

    private TipsItem newItemView() {
        TipsItem tipsItem = new TipsItem(getContext());
        return tipsItem;
    }


    private void initAnimation() {
        anim_out = newAnimation(0, -1);
        anim_in = newAnimation(1, 0);
        anim_in.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                updateTipAndPlayAnimationWithCheck();
            }
        });
    }

    private Animation newAnimation(float fromYValue, float toYValue) {
        Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF, toYValue);
        anim.setDuration(ANIM_DURATION);
        anim.setStartOffset(ANIM_DELAYED_MILLIONS);
        anim.setInterpolator(new DecelerateInterpolator());
        return anim;
    }

    private void updateTipAndPlayAnimationWithCheck() {
        if (System.currentTimeMillis() - lastTimeMillis < 1000) {
            return;
        }
        lastTimeMillis = System.currentTimeMillis();
        updateTipAndPlayAnimation();
    }

    private void updateTipAndPlayAnimation() {
        if (!isTipsIn) {
            updateTip(view_tip_out);
            view_tip_in.startAnimation(anim_out);
            view_tip_out.startAnimation(anim_in);
            this.bringChildToFront(view_tip_in);
        } else {
            updateTip(view_tip_in);
            view_tip_out.startAnimation(anim_out);
            view_tip_in.startAnimation(anim_in);
            this.bringChildToFront(view_tip_out);
        }
    }

    private void updateTip(TipsItem tipView) {
        List<String> group = getNextGroup();
        if (group != null) {
            tipView.updateData(group);
        }
    }

    private List<String> getNextGroup() {
        if (isListEmpty(tipList))
            return null;
        ArrayList<String> list = new ArrayList<>(2);
        if (tipList.size() < 2) {
            view_tip_in.setVisibility(GONE);
            list.add(tipList.get(0));
        } else {
            list.add(tipList.get(curTipIndex++ % tipList.size()));
            list.add(tipList.get(curTipIndex++ % tipList.size()));
            isTipsIn = !isTipsIn;
        }
        return list;
    }

    public static boolean isListEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public void setTipList(List<String> tipList) {
        if (isListEmpty(tipList))
            return;

        this.tipList.clear();
        this.tipList.addAll(tipList);
        curTipIndex = 0;
        updateTip(view_tip_out);
        if (this.tipList.size() > 2) {
            updateTipAndPlayAnimation();
        }
    }
}