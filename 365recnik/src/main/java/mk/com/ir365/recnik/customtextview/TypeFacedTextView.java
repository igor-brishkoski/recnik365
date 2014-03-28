package mk.com.ir365.recnik.customtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import mk.com.ir365.recnik.R;
import mk.com.ir365.recnik.fund.RecnikApp;
import mk.com.ir365.recnik.fund.RecnikConstant;


public class TypeFacedTextView extends TextView {

    public TypeFacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            TypedArray styledAttr = RecnikApp.getContext().obtainStyledAttributes(attrs, R.styleable.TypeFacedTextView);
            String fontName = styledAttr.getString(R.styleable.TypeFacedTextView_typeface);
            styledAttr.recycle();

            if (fontName != null) {
                Typeface typeface = Typeface.createFromAsset(RecnikApp.getContext().getAssets(), fontName);
                setTypeface(typeface);
            }
        }
    }

    public static void AddRobotoFont(TextView textView) {
        Typeface typeface = Typeface.createFromAsset(RecnikApp.getContext().getAssets(), RecnikConstant.ROBOTO_THIN);
        textView.setTypeface(typeface);
    }

}