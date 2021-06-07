package dev.sebastianb.icbm4fabric.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.icbm4fabric.Constants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

@SuppressWarnings("FieldCanBeLocal")
@Environment(EnvType.CLIENT)
public class LaunchScreen extends HandledScreen<LaunchScreenHandler> {


    private static final Identifier TEXTURE = new Identifier(Constants.MOD_ID, "textures/gui/missile_launcher_screen.png");
    private final int textureWidth = 256;
    private final int textureHeight = 256;
    private final float textureScale = 0.87f;

    private boolean entityGUIRotate;
    float bodyRotate = 180f;
    float bodyRotateDiff = 0f;

    private volatile boolean openedGUI; // if the GUI is in a opened state


    public LaunchScreen(LaunchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        entityGUIRotate = false;
        openedGUI = true; // ik it's redundant but just for readability
        long openedTime = System.currentTimeMillis();

        Runnable task = () -> {
            while (openedGUI) {
                long currentTime = System.currentTimeMillis();
                if (currentTime >= openedTime + 3000) {
                    float t = MathHelper.wrapDegrees(((System.currentTimeMillis() % 3600) / 10f));
                    float d = MathHelper.wrapDegrees(t + 180); // 180 faces toward the front
                    bodyRotateDiff = d;
                    entityGUIRotate = true;
                    break;
                }
            }
        };
        new Thread(task).start();
    }

    @Override
    protected void init() {
        super.backgroundWidth = textureWidth;
        super.backgroundHeight = textureHeight;
        super.init();
    }

    @Override
    public void onClose() {
        super.onClose();
        this.openedGUI = false;

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
        this.drawEntity
                (this.x + 73, this.y + 170, 40, (float)(this.x + 88 - mouseX), (float)(this.y + 45 - 30 - mouseY), this.client.player);

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

    private void drawEntity(int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
        // float f = (float)Math.atan((double)(mouseX / 40.0F));
        // float g = (float)Math.atan((double)(mouseY / 40.0F));
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate((double)x, (double)y, 1050.0D);
        matrixStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack matrixStack2 = new MatrixStack();
        matrixStack2.translate(0.0D, 0.0D, 1000.0D);
        matrixStack2.scale((float)size, (float)size, (float)size);
        Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180f);
        // Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(g * 20.0F);
        // quaternion.hamiltonProduct(quaternion2);
        matrixStack2.multiply(quaternion);
        float h = entity.bodyYaw;
        float i = entity.getYaw();
        float j = entity.getPitch();
        float k = entity.prevHeadYaw;
        float l = entity.headYaw;

        if (entityGUIRotate) {
            bodyRotate = MathHelper.wrapDegrees(((System.currentTimeMillis() % 3600) / 10f) - bodyRotateDiff); // spins the entity based on a time
        }

        entity.bodyYaw = bodyRotate; // yaw 180 default. Spins entity
        entity.setYaw(180.0F);
        entity.setPitch(20.0F);
        entity.headYaw = bodyRotate;

        entity.prevHeadYaw = bodyRotate;
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        // quaternion2.conjugate();
        // entityRenderDispatcher.setRotation(quaternion2);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack2, immediate, 15728880);
        });
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        entity.bodyYaw = h;
        entity.setYaw(i);
        entity.setPitch(j);
        entity.prevHeadYaw = k;
        entity.headYaw = l;
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }

}
