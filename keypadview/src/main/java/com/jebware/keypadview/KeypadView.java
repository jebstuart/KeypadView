package com.jebware.keypadview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * Created by Jeb Ware on 9/1/15.
 * (c) 2015
 */
public class KeypadView extends FrameLayout {

    private static final String TAG = "NumericKeypad";

    public interface NumericKeypadContainer {
        @Nullable
        EditText getActiveField();
    }

    @Nullable
    private NumericKeypadContainer container;

    public KeypadView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public KeypadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public KeypadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @SuppressWarnings("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeypadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        initView(this);
    }

    public void setContainer(@Nullable NumericKeypadContainer container) {
        this.container = container;
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        initView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        initView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        initView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        initView(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        initView(child);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        boolean result = super.addViewInLayout(child, index, params);
        initView(child);
        return result;
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        boolean result = super.addViewInLayout(child, index, params, preventRequestLayout);
        initView(child);
        return result;
    }

    private void initView(View view) {
        if (view instanceof ViewGroup) {
            for (int i=0; i<((ViewGroup) view).getChildCount(); i++) {
                View child = ((ViewGroup) view).getChildAt(i);
                initView(child);
            }
        }
        if (view instanceof KeypadKey) {
            view.setOnClickListener(didTapButtonListener);
        }
    }

    private OnClickListener didTapButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof KeypadKey) {
                Action action = ((KeypadKey) v).getAction();
                switch (action) {
                    case Insert:
                        insertText(((KeypadKey) v).getText());
                        break;
                    case Delete:
                        doBackspace();
                        break;
                    case Radix:
                        insertRadix(((KeypadKey) v).getText());
                        break;
                }
            }
        }
    };

    private void insertRadix(CharSequence radix) {
        if (container == null) {
            Log.w(TAG, "No container set on NumericKeypad.  Call setContainer() if you want this keypad to do anything.");
            return;
        }
        EditText field = container.getActiveField();
        if (field == null) {
            //No active field.
            return;
        }

        if (field.getText().toString().contains(radix)) {
            //NOOP, can't put two decimals in
            return;
        }
        if (field.getSelectionEnd() < 0) {
            field.getText().append(radix);
        } else {
            field.getText().insert(field.getSelectionEnd(), radix);
        }
    }

    private void insertText(CharSequence c) {
        if (container == null) {
            Log.w(TAG, "No container set on NumericKeypad.  Call setContainer() if you want this keypad to do anything.");
            return;
        }
        EditText field = container.getActiveField();
        if (field == null) {
            //No active field.
            return;
        }

        if (field.getSelectionEnd() < 0) {
            field.getText().append(c);
        } else {
            field.getText().insert(field.getSelectionEnd(), c);
        }
    }

    private void doBackspace() {
        if (container == null) {
            Log.w(TAG, "No container set on NumericKeypad.  Call setContainer() if you want this keypad to do anything.");
            return;
        }
        EditText field = container.getActiveField();
        if (field == null) {
            //No active field.
            return;
        }

        if (field.getSelectionEnd() > 0) {
            //remove a character before the cursor
            field.getText().replace(field.getSelectionEnd() - 1, field.getSelectionEnd(), "");
        } // else NOOP, cursor is at the beginning, can't backspace
    }

    //TODO remove me? - automatically search up the hierarchy and find a view in focus?
    private EditText getActiveField() {
        return container.getActiveField();
    }

}
