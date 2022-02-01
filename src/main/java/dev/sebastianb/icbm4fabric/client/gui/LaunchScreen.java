package dev.sebastianb.icbm4fabric.client.gui;

import java.util.ArrayList;

import com.mojang.blaze3d.systems.RenderSystem;

import org.lwjgl.glfw.GLFW;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.missile.TaterMissileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
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
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

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

    private volatile boolean openedGUI; // if the GUI is in an opened state
    private volatile boolean clickedEntity = false;

    private ArrayList<Drawable> drawables = new ArrayList<>();

    // add X detonator
    TextFieldWidget xMissileInput = new TextFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 70, 70, 14, LiteralText.EMPTY);

    // add Z detonator
    TextFieldWidget zMissileInput = new TextFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 53, 70, 14, LiteralText.EMPTY);

    // add Y detonator
    TextFieldWidget yMissileInput = new TextFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 6, 70, 14, LiteralText.EMPTY);


    public LaunchScreen(LaunchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        entityGUIRotate = false;
        openedGUI = true; // ik it's redundant but just for readability
        runRotationCountdown(bodyRotate);
    }

    private void runRotationCountdown(float bodyRotate) {
        long openedTime = System.currentTimeMillis();
        Runnable task = () -> {
            while (openedGUI) {
                if (clickedEntity)
                    break;
                long currentTime = System.currentTimeMillis();
                if (currentTime >= openedTime + 3000) {
                    float t = MathHelper.wrapDegrees(((System.currentTimeMillis() % 3600) / 10f));
                    float d = MathHelper.wrapDegrees(t + bodyRotate); // 180 faces toward the front
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
        drawables.clear(); // clear anything left behind

        // add X detonator
        TextFieldWidget xTarget = new TextFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 70, 70, 14, LiteralText.EMPTY);
        xTarget.setText(xMissileInput.getText()); // "fill" new box with old box
        this.xMissileInput = xTarget;

        // add Z detonator
        TextFieldWidget zTarget = new TextFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 53, 70, 14, LiteralText.EMPTY);
        zTarget.setText(zMissileInput.getText());
        this.zMissileInput = zTarget;

        // add Y detonator
        TextFieldWidget yTarget = new TextFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 6, 70, 14, LiteralText.EMPTY);
        yTarget.setText(yMissileInput.getText());
        this.yMissileInput = yTarget;

        addTextedButton(xTarget);
        addTextedButton(zTarget);
        addTextedButton(yTarget);

    }

    @Override
    public void onClose() {
        super.onClose();
        this.openedGUI = false;
    }

    // used to prevent close on "E" press
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_E) {
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    // used to prevent close on "E" press
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_E) {
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push(); {
            this.renderBackground(matrices);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, TEXTURE);

            int x = (int) ((width / 2) - ((backgroundWidth / 2) * textureScale));
            int y = (int) ((height/ 2) - ((backgroundHeight / 2) * textureScale));

            matrices.translate(x, y, 0);
            matrices.scale(textureScale, textureScale, textureScale);

            drawTexture(matrices, 0, 0, 0, 0, backgroundWidth, backgroundHeight, 256, 256);

            this.drawEntity(this.x + 71, this.y + 180, 40, (float)(this.x + 88 - mouseX), (float)(this.y + 45 - 30 - mouseY), new TaterMissileEntity(ModEntityTypes.TATER_MISSILE, client.world)); // new TaterMissileEntity(ModEntityTypes.TATER_MISSILE, this.client.world)

        }
        matrices.pop();
        matrices.push();
        for (Drawable e : drawables) {
            e.render(matrices, mouseX, mouseY, delta);
        }
        matrices.pop();

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if ((mouseX < 218 && mouseX > 145) && (mouseY < 201 && mouseY > 67)) {
            entityGUIRotate = false;
            clickedEntity = true;
        }

        // unfocuses all other text widgets
        for (Drawable textFieldWidget : this.drawables) {
            if (((TextFieldWidget) textFieldWidget).mouseClicked(mouseX, mouseY, button)) {
                TextFieldWidget selectedWidget = ((TextFieldWidget) textFieldWidget);
                for (Drawable otherTextWidgets : this.drawables) {
                    if (!otherTextWidgets.equals(selectedWidget)) {
                        ((TextFieldWidget) otherTextWidgets).setTextFieldFocused(false);
                    }
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

        bodyRotate = (float) (bodyRotate + deltaX);

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (clickedEntity) {
            runRotationCountdown(-bodyRotate);
            clickedEntity = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.renderBackground(matrices);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private void addTextedButton(TextFieldWidget textFieldWidget) {
        textFieldWidget.setMaxLength(9);
        drawables.add(textFieldWidget);
        this.addSelectableChild(textFieldWidget);
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

        entity.prevHeadYaw = bodyRotate;

        entity.setYaw(bodyRotate); // = bodyRotate;
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
