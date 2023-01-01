/*
 * Copyright (C) 2023 The VIEWTIFY Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package viewtify;

import javafx.scene.input.KeyEvent;

/**
 * @version 2018/08/02 11:31:46
 */
public class Key {
    /**
     * Constant for the {@code Backspace} key.
     */
    public static final Key Backspace = new Key(8, "Backspace");

    /**
     * Constant for the {@code Tab} key.
     */
    public static final Key Tab = new Key(9, "Tab");

    /**
     * Constant for the {@code Enter} key.
     */
    public static final Key Enter = new Key(10, "Enter");

    /**
     * Constant for the {@code Clear} key.
     */
    public static final Key Clear = new Key(12, "Clear");

    /**
     * Constant for the {@code Shift} key.
     */
    public static final Key Shift = new Key(16, "Shift");

    /**
     * Constant for the {@code Ctrl} key.
     */
    public static final Key Control = new Key(17, "Ctrl");

    /**
     * Constant for the {@code Alt} key.
     */
    public static final Key Alt = new Key(18, "Alt");

    /**
     * Constant for the {@code Pause} key.
     */
    public static final Key Pause = new Key(19, "Pause");

    /**
     * Constant for the {@code Caps Lock} key.
     */
    public static final Key CapsLock = new Key(20, "Caps Lock");

    /**
     * Constant for the {@code Esc} key.
     */
    public static final Key Escape = new Key(27, "Esc");

    /**
     * Constant for the {@code Space} key.
     */
    public static final Key Space = new Key(32, "Space");

    /**
     * Constant for the {@code Page Up} key.
     */
    public static final Key PageUp = new Key(33, "Page Up");

    /**
     * Constant for the {@code Page Down} key.
     */
    public static final Key PageDown = new Key(34, "Page Down");

    /**
     * Constant for the {@code End} key.
     */
    public static final Key End = new Key(35, "End");

    /**
     * Constant for the {@code Home} key.
     */
    public static final Key Home = new Key(36, "Home");

    /**
     * Constant for the non-numpad <b>left</b> arrow key.
     */
    public static final Key Left = new Key(37, "Left");

    /**
     * Constant for the non-numpad <b>up</b> arrow key.
     */
    public static final Key Up = new Key(38, "Up");

    /**
     * Constant for the non-numpad <b>right</b> arrow key.
     */
    public static final Key Right = new Key(39, "Right");

    /**
     * Constant for the non-numpad <b>down</b> arrow key.
     */
    public static final Key Down = new Key(40, "Down");

    /**
     * Constant for the {@code Insert} key.
     */
    public static final Key Insert = new Key(45, "Insert");

    /**
     * Constant for the {@code Delete} key.
     */
    public static final Key Delete = new Key(46, "Delete");

    /**
     * Constant for the comma key, ","
     */
    public static final Key Comma = new Key(188, "Comma");

    /**
     * Constant for the minus key, "-"
     */
    public static final Key Minus = new Key(189, "Minus");

    /**
     * Constant for the period key, "."
     */
    public static final Key Period = new Key(190, "Period");

    /**
     * Constant for the forward slash key, "/"
     */
    public static final Key Slash = new Key(191, "Slash");

    /**
     * Constant for the {@code 0} key.
     */
    public static final Key Digit0 = new Key(48, "0");

    /**
     * Constant for the {@code 1} key.
     */
    public static final Key Digit1 = new Key(49, "1");

    /**
     * Constant for the {@code 2} key.
     */
    public static final Key Digit2 = new Key(50, "2");

    /**
     * Constant for the {@code 3} key.
     */
    public static final Key Digit3 = new Key(51, "3");

    /**
     * Constant for the {@code 4} key.
     */
    public static final Key Digit4 = new Key(52, "4");

    /**
     * Constant for the {@code 5} key.
     */
    public static final Key Digit5 = new Key(53, "5");

    /**
     * Constant for the {@code 6} key.
     */
    public static final Key Digit6 = new Key(54, "6");

    /**
     * Constant for the {@code 7} key.
     */
    public static final Key Digit7 = new Key(55, "7");

    /**
     * Constant for the {@code 8} key.
     */
    public static final Key Digit8 = new Key(56, "8");

    /**
     * Constant for the {@code 9} key.
     */
    public static final Key Digit9 = new Key(57, "9");

    /**
     * Constant for the semicolon key, ";"
     */
    public static final Key SemiColon = new Key(186, "Semicolon");

    /**
     * Constant for the {@code A} key.
     */
    public static final Key A = new Key(65, "A");

    /**
     * Constant for the {@code B} key.
     */
    public static final Key B = new Key(66, "B");

    /**
     * Constant for the {@code C} key.
     */
    public static final Key C = new Key(67, "C");

    /**
     * Constant for the {@code D} key.
     */
    public static final Key D = new Key(68, "D");

    /**
     * Constant for the {@code E} key.
     */
    public static final Key E = new Key(69, "E");

    /**
     * Constant for the {@code F} key.
     */
    public static final Key F = new Key(70, "F");

    /**
     * Constant for the {@code G} key.
     */
    public static final Key G = new Key(71, "G");

    /**
     * Constant for the {@code H} key.
     */
    public static final Key H = new Key(72, "H");

    /**
     * Constant for the {@code I} key.
     */
    public static final Key I = new Key(73, "I");

    /**
     * Constant for the {@code J} key.
     */
    public static final Key J = new Key(74, "J");

    /**
     * Constant for the {@code K} key.
     */
    public static final Key K = new Key(75, "K");

    /**
     * Constant for the {@code L} key.
     */
    public static final Key L = new Key(76, "L");

    /**
     * Constant for the {@code M} key.
     */
    public static final Key M = new Key(77, "M");

    /**
     * Constant for the {@code N} key.
     */
    public static final Key N = new Key(78, "N");

    /**
     * Constant for the {@code O} key.
     */
    public static final Key O = new Key(79, "O");

    /**
     * Constant for the {@code P} key.
     */
    public static final Key P = new Key(80, "P");

    /**
     * Constant for the {@code Q} key.
     */
    public static final Key Q = new Key(81, "Q");

    /**
     * Constant for the {@code R} key.
     */
    public static final Key R = new Key(82, "R");

    /**
     * Constant for the {@code S} key.
     */
    public static final Key S = new Key(83, "S");

    /**
     * Constant for the {@code T} key.
     */
    public static final Key T = new Key(84, "T");

    /**
     * Constant for the {@code U} key.
     */
    public static final Key U = new Key(85, "U");

    /**
     * Constant for the {@code V} key.
     */
    public static final Key V = new Key(86, "V");

    /**
     * Constant for the {@code W} key.
     */
    public static final Key W = new Key(87, "W");

    /**
     * Constant for the {@code X} key.
     */
    public static final Key X = new Key(88, "X");

    /**
     * Constant for the {@code Y} key.
     */
    public static final Key Y = new Key(89, "Y");

    /**
     * Constant for the {@code Z} key.
     */
    public static final Key Z = new Key(90, "Z");

    /**
     * Constant for the open bracket key, "["
     */
    public static final Key OpenBracket = new Key(219, "Open Bracket");

    /**
     * Constant for the back slash key, "\"
     */
    public static final Key BackSlash = new Key(220, "Back Slash");

    /**
     * Constant for the close bracket key, "]"
     */
    public static final Key CloseBracket = new Key(221, "Close Bracket");

    /**
     * Constant for the {@code Numpad 0} key.
     */
    public static final Key Numpad0 = new Key(96, "Numpad 0");

    /**
     * Constant for the {@code Numpad 1} key.
     */
    public static final Key Numpad1 = new Key(97, "Numpad 1");

    /**
     * Constant for the {@code Numpad 2} key.
     */
    public static final Key Numpad2 = new Key(98, "Numpad 2");

    /**
     * Constant for the {@code Numpad 3} key.
     */
    public static final Key Numpad3 = new Key(99, "Numpad 3");

    /**
     * Constant for the {@code Numpad 4} key.
     */
    public static final Key Numpad4 = new Key(100, "Numpad 4");

    /**
     * Constant for the {@code Numpad 5} key.
     */
    public static final Key Numpad5 = new Key(101, "Numpad 5");

    /**
     * Constant for the {@code Numpad 6} key.
     */
    public static final Key Numpad6 = new Key(102, "Numpad 6");

    /**
     * Constant for the {@code Numpad 7} key.
     */
    public static final Key Numpad7 = new Key(103, "Numpad 7");

    /**
     * Constant for the {@code Numpad 8} key.
     */
    public static final Key Numpad8 = new Key(104, "Numpad 8");

    /**
     * Constant for the {@code Numpad 9} key.
     */
    public static final Key Numpad9 = new Key(105, "Numpad 9");

    /**
     * Constant for the {@code Multiply} key.
     */
    public static final Key Multiply = new Key(106, "Multiply");

    /**
     * Constant for the {@code Add} key.
     */
    public static final Key Add = new Key(107, "Add");

    /**
     * Constant for the {@code Subtract} key.
     */
    public static final Key Subtract = new Key(109, "Subtract");

    /**
     * Constant for the {@code Decimal} key.
     */
    public static final Key DecimalPoint = new Key(110, "Decimal");

    /**
     * Constant for the {@code Divide} key.
     */
    public static final Key Divide = new Key(111, "Divide");

    /**
     * Constant for the {@code Num Lock} key.
     */
    public static final Key NumLock = new Key(144, "Num Lock");

    /**
     * Constant for the {@code Scroll Lock} key.
     */
    public static final Key ScrollLock = new Key(145, "Scroll Lock");

    /**
     * Constant for the F1 function key.
     */
    public static final Key F1 = new Key(112, "F1");

    /**
     * Constant for the F2 function key.
     */
    public static final Key F2 = new Key(113, "F2");

    /**
     * Constant for the F3 function key.
     */
    public static final Key F3 = new Key(114, "F3");

    /**
     * Constant for the F4 function key.
     */
    public static final Key F4 = new Key(115, "F4");

    /**
     * Constant for the F5 function key.
     */
    public static final Key F5 = new Key(116, "F5");

    /**
     * Constant for the F6 function key.
     */
    public static final Key F6 = new Key(0x11775, "F6");

    /**
     * Constant for the F7 function key.
     */
    public static final Key F7 = new Key(118, "F7");

    /**
     * Constant for the F8 function key.
     */
    public static final Key F8 = new Key(119, "F8");

    /**
     * Constant for the F9 function key.
     */
    public static final Key F9 = new Key(120, "F9");

    /**
     * Constant for the F10 function key.
     */
    public static final Key F10 = new Key(121, "F10");

    /**
     * Constant for the F11 function key.
     */
    public static final Key F11 = new Key(122, "F11");

    /**
     * Constant for the F12 function key.
     */
    public static final Key F12 = new Key(123, "F12");

    /**
     * Constant for the F13 function key.
     */
    public static final Key F13 = new Key(124, "F13");

    /**
     * Constant for the F14 function key.
     */
    public static final Key F14 = new Key(125, "F14");

    /**
     * Constant for the F15 function key.
     */
    public static final Key F15 = new Key(126, "F15");

    /**
     * Constant for the F16 function key.
     */
    public static final Key F16 = new Key(127, "F16");

    /**
     * Constant for the "@" key.
     */
    public static final Key AtSign = new Key(64, "At");

    /**
     * Constant for the ":" key.
     */
    public static final Key Colon = new Key(58, "Colon");

    /**
     * Constant for the "^" key.
     */
    public static final Key Circumflex = new Key(160, "Circumflex");

    /**
     * Constant for the Microsoft Windows "Windows" key. It is used for both the left and right
     * version of the key.
     */
    public static final Key WINDOWS = new Key(91, "Windows");

    /**
     * Null object for {@link Key}.
     */
    public static final Key None = new Key(999999, "NoExistence");

    /** The modifier. */
    private static final int ALT = 1 << 0;

    /** The modifier. */
    private static final int CTRL = 1 << 1;

    /** The modifier. */
    private static final int META = 1 << 2;

    /** The modifier. */
    private static final int SHIFT = 1 << 3;

    /** The modifier. */
    private static final int SHORTCUT = 1 << 4;

    /** The native key code. */
    public final int code;

    /** The key name. */
    public final String name;

    /** The modifier manager. */
    private final int modifiers;

    /**
     * @param code A native key code.
     */
    private Key(int code, String name) {
        this(code, name, 0);
    }

    /**
     * @param code A native key code.
     */
    private Key(int code, String name, int modifier) {
        this.code = code;
        this.name = name;
        this.modifiers = modifier;
    }

    /**
     * Create key from {@link KeyEvent}.
     * 
     * @param e
     */
    Key(KeyEvent e) {
        int modifiers = 0;
        if (e.isAltDown()) modifiers |= ALT;
        if (e.isControlDown()) modifiers |= CTRL;
        if (e.isMetaDown()) modifiers |= META;
        if (e.isShiftDown()) modifiers |= SHIFT;
        if (e.isShortcutDown()) modifiers |= SHORTCUT;

        this.code = e.getCode().getCode();
        this.name = e.getText();
        this.modifiers = modifiers;
    }

    /**
     * With modifier.
     * 
     * @return A modified key combination.
     */
    public Key alt() {
        return new Key(code, name, modifiers | ALT);
    }

    /**
     * With modifier.
     * 
     * @return A modified key combination.
     */
    public Key ctrl() {
        return new Key(code, name, modifiers | CTRL);
    }

    /**
     * With modifier.
     * 
     * @return A modified key combination.
     */
    public Key meta() {
        return new Key(code, name, modifiers | META);
    }

    /**
     * With modifier.
     * 
     * @return A modified key combination.
     */
    public Key shift() {
        return new Key(code, name, modifiers | SHIFT);
    }

    /**
     * With modifier.
     * 
     * @return A modified key combination.
     */
    public Key shortcut() {
        return new Key(code, name, modifiers | SHORTCUT);
    }

    /**
     * Tests if this key matches the combination on the specified KeyEvent.
     * 
     * @param e
     */
    public boolean match(KeyEvent e) {
        if (e.getCode().getCode() != code) {
            return false;
        }

        if ((modifiers & ALT) != 0 && !e.isAltDown()) {
            return false;
        }

        if ((modifiers & CTRL) != 0 && !e.isControlDown()) {
            return false;
        }

        if ((modifiers & META) != 0 && !e.isMetaDown()) {
            return false;
        }

        if ((modifiers & SHIFT) != 0 && !e.isShiftDown()) {
            return false;
        }

        if ((modifiers & SHORTCUT) != 0 && !e.isShortcutDown()) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        int result = 1;
        result = 31 * result + code;
        result = 31 * result + modifiers;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof Key == false) {
            return false;
        }

        Key other = (Key) obj;
        return code == other.code && modifiers == other.modifiers;
    }
}