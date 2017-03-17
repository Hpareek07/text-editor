package com.fo.controls.fontpicker;

import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

@SuppressWarnings("restriction")
public class FontPickerBehavior extends ComboBoxBaseBehavior<Font> {

    public FontPickerBehavior(final FontPicker fontPicker) {
        super(fontPicker, FONT_PICKER_BINDINGS);
    }

    protected static final String OPEN_ACTION = "Open";
    protected static final String CLOSE_ACTION = "Close";
    protected static final List<KeyBinding> FONT_PICKER_BINDINGS = new ArrayList<>();

    static {
        FONT_PICKER_BINDINGS.add(new KeyBinding(ESCAPE, KEY_PRESSED, CLOSE_ACTION));
        FONT_PICKER_BINDINGS.add(new KeyBinding(SPACE, KEY_PRESSED, OPEN_ACTION));
        FONT_PICKER_BINDINGS.add(new KeyBinding(ENTER, KEY_PRESSED, OPEN_ACTION));
    }

    @Override
    protected void callAction(String name) {
        if (OPEN_ACTION.equals(name)) {
            show();
        } else if (CLOSE_ACTION.equals(name)) {
            hide();
        } else {
            super.callAction(name);
        }
    }

    @Override
    public void onAutoHide() {
        FontPicker fontPicker = (FontPicker) getControl();
        FontPickerSkin fontPickerSkin = (FontPickerSkin) fontPicker.getSkin();
        fontPickerSkin.syncWithAutoUpdate();
        if (!fontPicker.isShowing()) {
            super.onAutoHide();
        }
    }

}
