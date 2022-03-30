package dev.sebastianb.icbm4fabric.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.block.launcher.GenericMissileLauncherEntity;
import dev.sebastianb.icbm4fabric.client.gui.widget.NumberFieldWidget;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
@Environment(EnvType.CLIENT)
public class LaunchScreen extends HandledScreen<LaunchScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Constants.MOD_ID, "textures/gui/missile_launcher_screen.png");
    private final int textureWidth = 512;
    private final int textureHeight = 256;
    private final float textureScale = 0.87f;

    private boolean entityGUIRotate;
    float bodyRotate = 180f;
    float bodyRotateDiff = 0f;

    private volatile boolean openedGUI; // if the GUI is in an opened state
    private volatile boolean clickedEntity = false;

    private boolean valuesChanged;

    private ArrayList<Drawable> drawables = new ArrayList<>();

    // add X detonator
    NumberFieldWidget xMissileInput = new NumberFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 70, 70, 14, LiteralText.EMPTY);

    // add Z detonator
    NumberFieldWidget zMissileInput = new NumberFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 53, 70, 14, LiteralText.EMPTY);

    // add Y detonator
    NumberFieldWidget yMissileInput = new NumberFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 6, 70, 14, LiteralText.EMPTY);

    // button for launch
    ButtonWidget button;

    public LaunchScreen(LaunchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        entityGUIRotate = false;
        openedGUI = true; // ik it's redundant but just for readability
        // get rid of missile status
        if (handler.hasMissile()) {
            runRotationCountdown(bodyRotate);
        }
        valuesChanged = false;

        BlockPos target = handler.getTarget();

        xMissileInput.setText(String.valueOf(target.getX())); // set input texts
        yMissileInput.setText(String.valueOf(target.getY()));
        zMissileInput.setText(String.valueOf(target.getZ()));
    }

    private void runRotationCountdown(float bodyRotate) { // wait for time to rotate missile (I think?)
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
        super.backgroundWidth = 256;
        super.backgroundHeight = 256;
        super.init();
        drawables.clear(); // clear anything left behind

        // add X detonator
        NumberFieldWidget xTarget = new NumberFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 70, 70, 14, LiteralText.EMPTY);
        xTarget.setText(xMissileInput.getText()); // "fill" new box with old box
        this.xMissileInput = xTarget;

        // add Z detonator
        NumberFieldWidget zTarget = new NumberFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 53, 70, 14, LiteralText.EMPTY);
        zTarget.setText(zMissileInput.getText());
        this.zMissileInput = zTarget;

        // add Y detonator
        NumberFieldWidget yTarget = new NumberFieldWidget(textRenderer, this.width / 2 + 8, this.height / 2 - 6, 70, 14, LiteralText.EMPTY);
        yTarget.setText(yMissileInput.getText());
        this.yMissileInput = yTarget;

        if (handler.hasMissile()) {
            button = new ButtonWidget(this.width / 2 + 4, this.height / 2 + 35, 80, 20, new LiteralText("Launch"), btn -> {
                sendLaunchCords(); // make sure target is updated

                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(handler.getPos()); // add block pos for getting BE on server
                System.out.println("before " + handler.hasMissile());
                buf.writeBoolean(false);
                ClientPlayNetworking.send(Constants.Packets.LAUNCH_MISSILE, buf);

                BlockEntity be = MinecraftClient.getInstance().world.getBlockEntity(handler.getPos());
                if (be instanceof GenericMissileLauncherEntity launcherEntity) {
                    launcherEntity.setMissile(ItemStack.EMPTY); // remove missile from entity on client
                    System.out.println("after " + handler.hasMissile());
                }

                // this.client.setScreen(null); // close screen
            });
            this.addDrawableChild(button);
        }

        addTextedButton(xTarget);
        addTextedButton(zTarget);
        addTextedButton(yTarget);
    }

    private void sendLaunchCords() { // sends target cords to the server

        PacketByteBuf buf = PacketByteBufs.create();

        Integer x = xMissileInput.getInt();
        Integer y = yMissileInput.getInt();
        Integer z = zMissileInput.getInt();

        System.out.println(x + ", " + y + ", " + z);

        if (x == null || y == null || z == null) {
            return; // make sure all of the inputs are numbers
        }

        BlockPos target = new BlockPos(x, y, z);

        buf.writeBlockPos(target);
        buf.writeBlockPos(handler.getPos());

        ClientPlayNetworking.send(Constants.Packets.NEW_LAUNCH_CORDS, buf);
    }

    @Override
    public void close() {
        super.close();
        this.openedGUI = false;

        if (valuesChanged) {
            sendLaunchCords(); // if any values changed, update the cords 
        }
    }

    // used to prevent close on "E" press
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        valuesChanged = true;
        if (keyCode == GLFW.GLFW_KEY_E && !(getFocused() == null)) { // if nothing has been focused then we don't need to cancel E
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return super.charTyped(chr, modifiers);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();

        this.renderBackground(matrices);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (int) ((width / 2) - ((backgroundWidth / 2) * textureScale));
        int y = (int) ((height / 2) - ((backgroundHeight / 2) * textureScale));

        matrices.translate(x, y, 0);
        matrices.scale(textureScale, textureScale, textureScale);

        drawTexture(matrices, 0, 0, 0, 0, backgroundWidth, backgroundHeight, textureWidth, textureHeight);

        if (handler.hasMissile()) {
            this.drawEntity(this.x + 71, this.y + 180, 40, (float) (this.x + 88 - mouseX), (float) (this.y + 45 - 30 - mouseY), ModEntityTypes.Missiles.TATER.getType().create(client.world));
        } else {

        }

        matrices.pop();
        matrices.push();
        for (Drawable e : drawables) {
            e.render(matrices, mouseX, mouseY, delta);
        }
        // launch button won't render if there's no missile
        if (handler.hasMissile()) {
            button.render(matrices, mouseX, mouseY, delta); // TODO: Maybe use an image instead of default mc button
        } else {
            // TODO: replace with a image of the launch button being grayed out
            this.textRenderer.draw(matrices, "No missile", this.width / 2 + 4, this.height / 2 + 35, 0xFFFFFF);
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

    private void drawEntity(int x, int y, int size, float mouseX, float mouseY, AbstractMissileProjectile entity) {
        // float f = (float)Math.atan((double)(mouseX / 40.0F));
        // float g = (float)Math.atan((double)(mouseY / 40.0F));
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate((double) x, (double) y, 1050.0D);
        matrixStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack matrixStack2 = new MatrixStack();
        matrixStack2.translate(0.0D, 0.0D, 1000.0D);
        matrixStack2.scale((float) size, (float) size, (float) size);
        Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180f);
        // Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(g * 20.0F);
        // quaternion.hamiltonProduct(quaternion2);
        matrixStack2.multiply(quaternion);
        float i = entity.getYaw();
        float j = entity.getPitch();

        if (entityGUIRotate) {
            bodyRotate = MathHelper.wrapDegrees(((System.currentTimeMillis() % 3600) / 10f) - bodyRotateDiff); // spins the entity based on a time
        }
        entity.setPitch(90);
        // correct rotation
        matrixStack2.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270 + bodyRotate));

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
        entity.setYaw(i);
        entity.setPitch(j);
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }

}
