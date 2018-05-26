package com.example.jingj.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

public class SunsetFragment extends Fragment {

    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    public AnimatorSet animatorSetSunrise;
    private AnimatorSet animatorSetSunset;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;
    private float mSunHeight;
    private int i = 0;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sunset, container, false);

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);

        mSceneView = v;
        mSunView = v.findViewById(R.id.sun);
        mSunHeight = mSunView.getHeight();
        mSkyView = v.findViewById(R.id.sky);
        mSceneView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                i++;
                if (i == 1) {
                    startSunsetAnimation();
                } else {
                    animatorSetSunset.end();
                    animatorSetSunset.reverse();
//                    if (i % 2 == 1) {
//                        animatorSetSunset.reverse();
//                    } else {
//                        animatorSetSunrise.reverse();
//                    }
                }
//                //i为奇数的时候下降，为偶数上升
//                if (i % 2 == 1) {
//                    startSunsetAnimation();
//                } else {
//                    startSunriseAnimation();
//                }
            }
        });
        return v;
    }

    private void startSunsetAnimation() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight() + mSunView.getHeight() * 0.2f;

        //设置太阳大小变化
        ObjectAnimator largeXAnimator = ObjectAnimator
                .ofFloat(mSunView, "scaleX", 1f, 1.2f)
                .setDuration(3000);

        ObjectAnimator largeYAnimator = ObjectAnimator
                .ofFloat(mSunView, "scaleY", 1f, 1.2f)
                .setDuration(3000);

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(3200);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunSetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        //使颜色渐变
        sunSetSkyAnimator.setEvaluator(new ArgbEvaluator());


        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        //创建一个落日动画集
        animatorSetSunset = new AnimatorSet();
        animatorSetSunset
                .play(heightAnimator)
                .with(sunSetSkyAnimator)
                .with(largeXAnimator)
                .with(largeYAnimator)
                .before(nightSkyAnimator);
        animatorSetSunset.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startSunriseAnimation() {
        float sunYStart = mSkyView.getHeight() + 0.2f * mSunView.getHeight();
        float sunYEnd = mSunView.getTop();

        //设置太阳大小变化
        ObjectAnimator largeXAnimator = ObjectAnimator
                .ofFloat(mSunView, "scaleX", 1.2f,1f)
                .setDuration(3000);

        ObjectAnimator largeYAnimator = ObjectAnimator
                .ofFloat(mSunView, "scaleY", 1.2f,1f)
                .setDuration(3000);

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunRiseSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor)
                .setDuration(3000);
        //使颜色渐变
        sunRiseSkyAnimator.setEvaluator(new ArgbEvaluator());


        ObjectAnimator daySkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                .setDuration(1500);
        daySkyAnimator.setEvaluator(new ArgbEvaluator());

        animatorSetSunrise = new AnimatorSet();

        animatorSetSunrise
                .play(sunRiseSkyAnimator)
                .with(heightAnimator)
                .with(largeXAnimator)
                .with(largeYAnimator)
                .after(daySkyAnimator);
        animatorSetSunrise.start();
    }

}
