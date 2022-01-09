package ch.hevs.ig.android.zemrani.teamalers.remaked.utils.customed_widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class MontSerratEditTextRegular extends AppCompatEditText {
    public MontSerratEditTextRegular( @NonNull Context context , @Nullable AttributeSet attrs ) {
        super( context , attrs );
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"Montserrat-Regular.ttf" );
        setTypeface( typeface );
    }
}
