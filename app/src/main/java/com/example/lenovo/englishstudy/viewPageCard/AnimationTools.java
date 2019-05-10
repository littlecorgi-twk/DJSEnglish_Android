package com.example.lenovo.englishstudy.viewPageCard;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class AnimationTools {
    public static void scale(View v) {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setDuration(300);
        v.startAnimation(anim);

    }
}
