package com.jebware.keypadview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * A KeypadView is a ViewGroup that can be added into an Android layout to be used as a keyboard
 * instead of a normal Android virtual keyboard (IME).  This is beneficial if you don't want your
 * layout to be resized or obscured by the virtual keyboard, or if you want very tight control
 * over exactly what the keyboard will look like.
 *
 * You need to provide the keypad's layout, as it doesn't provide its own.
 * See {@link com.jebware.keypadview.R.layout.numeric_keypad} for an example, or include it directly
 * if that's the keypad you want.
 *
 * For the KeypadView to work, it needs to know what EditText it should edit.  You can provide this
 * in any of the following ways:
 * - in the XML layout, by setting the app:field property on the KeypadView:
 * {@code
 * <EditText
    android:id="@android:id/text1"
    android:layout_width="match_parent"
    android:layout_height="96dp"
    android:background="#FFFFFF"
    />

    <com.jebware.keypadview.KeypadView
    android:id="@+id/kpv_keypad"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="1"
    app:field="@android:id/text1"
    >
 * }
 * - in code, by calling {@link KeypadView#setField(EditText)}
 * - in code, by calling {@link KeypadView#setContainer(KeypadContainer)}
 *

 *
 * Created by Jeb Ware on 9/1/15.
 * (c) 2015
 */
public class KeypadView extends FrameLayout {

    private static final String TAG = "NumericKeypad";

    /**
     * A KeypadContainer provides the EditText that should be edited.
     *
     * getActiveField will be called every time a button is pressed.
     */
    public interface KeypadContainer {
        @Nullable
        EditText getActiveField();
    }

    private int fieldId = -1;
    @Nullable
    private EditText field;
    @Nullable
    private KeypadContainer container;

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
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.KeypadView,
                    defStyleAttr,
                    defStyleRes);
            fieldId = a.getResourceId(R.styleable.KeypadView_field, -1);
            a.recycle();
        }
        initView(this);
    }

    @Nullable
    public EditText getField() {
        return field;
    }

    public void setField(@Nullable EditText field) {
        this.field = field;
    }

    public void setContainer(@Nullable KeypadContainer container) {
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

    /**
     * Ensure that this view, and any children will call didTapButtonListener
     * @param view the view on which we want to add the listener
     */
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
        EditText field = getActiveField();
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
        EditText field = getActiveField();
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
        EditText field = getActiveField();
        if (field == null) {
            //No active field.
            return;
        }
        field.getText();

        if (field.getSelectionEnd() > 0) {
            //remove a character before the cursor
            field.getText().replace(field.getSelectionEnd() - 1, field.getSelectionEnd(), "");
        } // else NOOP, cursor is at the beginning, can't backspace
    }

    @Nullable
    private EditText getActiveField() {
        if (field == null && fieldId != -1) { //search for an EditText with this id
            ViewParent p = getParent();
            while (field == null && p != null) {
                if (p instanceof View) {
                    field = (EditText) ((View) p).findViewById(fieldId);
                }
                p = p.getParent();
            }
        }

        if (field != null) {
            return field;
        }

        if (container != null) {
            return container.getActiveField();
        }
        Log.w(TAG, "KeypadView doesn't know where to send characters.  Call setContainer() or setField() or set the \"field\" XML property");
        return null;
    }

}
