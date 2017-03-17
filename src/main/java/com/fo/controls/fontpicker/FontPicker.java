package com.fo.controls.fontpicker;

import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Skin;
import javafx.scene.text.Font;
import java.io.File;

@SuppressWarnings("unused")
public class FontPicker extends ComboBoxBase<Font> {
    public FontPicker() {
        this(null);
    }

    public FontPicker(Font font) {
        setValue(font);
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new FontPickerSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return getClass().getResource("font-picker.css").toExternalForm();
    }

    private static final String DEFAULT_STYLE_CLASS = "font-picker";
}
