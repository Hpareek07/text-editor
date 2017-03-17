package com.fo.controls.fontpicker;

import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

@SuppressWarnings("restriction")
public class FontPickerSkin extends ComboBoxPopupControl<Font> {
    private FontPicker fontPicker;
    private final Label displayNode;
    private FontPickerContent fontPickerContent;

    public FontPickerSkin(final FontPicker fontPicker) {
        super(fontPicker, new FontPickerBehavior(fontPicker));
        this.fontPicker = fontPicker;
        registerChangeListener(fontPicker.valueProperty(), "VALUE");
        displayNode = new Label("");
        displayNode.getStyleClass().add("font-label");
        displayNode.setManaged(false);
    }

    @Override
    protected void handleControlPropertyChanged(String p) {
        super.handleControlPropertyChanged(p);
        if ("VALUE".equals(p)) {
            updateFont();
        }
    }

    private void updateFont() {
        final FontPicker fontPicker = (FontPicker) getSkinnable();
        Font font = fontPicker.getValue();
        String fontText = font.getFamily() + ", " + font.getStyle() + ", " + (int) font.getSize();
        displayNode.setText(fontText);
    }

    public void syncWithAutoUpdate() {
        if (!getPopup().isShowing() && getSkinnable().isShowing()) {
            getSkinnable().hide();
        }
    }

    @Override
    protected Node getPopupContent() {
        if (fontPickerContent == null) {
            fontPickerContent = new FontPickerContent(fontPicker);
        }
        return fontPickerContent;
    }

    @Override
    protected TextField getEditor() {
        return null;
    }

    @Override
    protected StringConverter<Font> getConverter() {
        return null;
    }

    @Override
    public Node getDisplayNode() {
        return displayNode;
    }

}
