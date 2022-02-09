package dev.sebastianb.icbm4fabric.client.gui.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class NumberFieldWidget extends TextFieldWidget {
    public NumberFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text text) {
        super(textRenderer, x, y, width, height, text);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (!(Character.isDigit(chr) || (chr == '-' && getText().length() == 0))) {
            return false; // if the character isn't a digit or it's - on the first character
        }

        return super.charTyped(chr, modifiers);
    }

    public Integer getInt() {
        if (getText().length() == 0) {
            return 0; // if no number then 0
        }

        try {
            return Integer.parseInt(getText()); 
        } catch (Exception e) {
            return null;
        }
    }
}
