package dev.sebastianb.icbm4fabric.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.icbm4fabric.Constants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LaunchScreen extends HandledScreen<LaunchScreenHandler> {


    private static final Identifier TEXTURE = new Identifier(Constants.MOD_ID, "textures/gui/frog.png"); // placeholder


    public LaunchScreen(LaunchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.addButton();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        super.render(matrices,mouseX,mouseY,delta);
        // with this uncommented, gives a crash at render
        // https://pastebin.com/GcXH4gnU
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.renderBackground(matrices);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // doesn't seem to be displaying
    private void addButton() {
        this.addButton(new ButtonWidget(this.width / 2 - 100, 196, 200, 20, ScreenTexts.YES, (buttonWidget) -> {
            this.client.openScreen(null);
        }));
    }

//    @Override
//    public Text getTitle() {
//        return LiteralText.EMPTY;
//    }
}
