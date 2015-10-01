package com.jebware.keypadview;

/**
 * An Action represents what should happen when the user taps on a particular
 * {@link KeypadKey}.  Available Actions are:
 *
 * Insert - inserts some text
 * Delete - works like a backspace key, deleting the character before the cursor
 * Radix - inserts text that is only allowed to appear once, like a decimal point in a number
 *
 * Created by jware on 9/30/15.
 * (c) 2015
 */
public enum Action {
    Insert,
    Delete,
    Radix

}
