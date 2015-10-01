package com.jebware.keypadview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Jeb Ware on 9/30/15.
 * (c) 2015
 */
public class KeypadButton extends Button implements KeypadKey {

    private Action action = Action.Insert;

    public KeypadButton(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public KeypadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public KeypadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @SuppressWarnings("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeypadButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.KeypadButton,
                    defStyleAttr,
                    defStyleRes);
            int actionInt = a.getInt(R.styleable.KeypadButton_action, -1);
            switch (actionInt) {
                case 0:
                    action = Action.Insert;
                    break;
                case 1:
                    action = Action.Delete;
                    break;
                case 2:
                    action = Action.Radix;
                    break;
            }
            a.recycle();
        }
    }

    @Override
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

}
