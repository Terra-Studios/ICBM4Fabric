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
            return false;
        }

        return super.charTyped(chr, modifiers);
    }

    public int getInt() {
        try {
            return Integer.parseInt(getText());
        } catch (Exception e) {
            return -1;
        }
    }
}
