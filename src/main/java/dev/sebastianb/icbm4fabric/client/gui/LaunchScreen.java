package dev.sebastianb.icbm4fabric.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.icbm4fabric.Constants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@SuppressWarnings("all") // like every good programmer
@Environment(EnvType.CLIENT)
public class LaunchScreen extends HandledScreen<LaunchScreenHandler> {


    private static final Identifier TEXTURE = new Identifier(Constants.MOD_ID, "textures/gui/missile_launcher_screen.png");
    private final int textureWidth = 256;
    private final int textureHeight = 256;
    private float textureScale = 0.87f;


    public LaunchScreen(LaunchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.backgroundWidth = textureWidth;
        super.backgroundHeight = textureHeight;

        super.init();
        // this.addButton();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        matrices.push(); {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, TEXTURE);

            int x = (int) ((width / 2) - ((backgroundWidth / 2) * textureScale));
            int y = (int) ((height/ 2) - ((backgroundHeight / 2) * textureScale));

            matrices.translate(x, y, 0);
            matrices.scale(textureScale, textureScale, textureScale);

            drawTexture(matrices, 0, 0, 0, 0, backgroundWidth, backgroundHeight, 256, 256);


        }
        matrices.pop();

    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.renderBackground(matrices);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    private void addButton() {
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, 196, 200, 20, ScreenTexts.YES, (buttonWidget) -> {
            this.client.openScreen(null);
        }));
    }

    @Override
    public Text getTitle() {
        return LiteralText.EMPTY;
    }
}
