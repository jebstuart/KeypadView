package com.jebware.keypadview;

/**
 * By implementing this interface, any View subclass can act as a Key in a {@link KeypadView}
 *
 * Created by jware on 9/30/15.
 * (c) 2015
 */
public interface KeypadKey {

    /**
     * What action should we take when the user taps this key?
     * @return the action
     */
    Action getAction();

    /**
     * What text should be inserted, if the action is Insert or Radix.
     *
     * This method is not called if the action is Delete
     *
     * @return the text to insert
     */
    CharSequence getText();

}
