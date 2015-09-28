package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.MyVocabList;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;

public class Slide
{
    public static void slideDown(Context context, View v)
    {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        if (animation != null)
        {
            animation.reset();
            if (v != null)
            {
                v.clearAnimation();
                v.startAnimation(animation);
            }
        }
    }

    public static void slideUp(Context context, View v)
    {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        if (animation != null)
        {
            animation.reset();
            if (v != null)
            {
                v.clearAnimation();
                v.startAnimation(animation);
            }
        }
    }
}