package xyz.templecheats.templeclient.features.module.modules.render.esp.sub;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import xyz.templecheats.templeclient.TempleClient;
import xyz.templecheats.templeclient.event.ForgeEventManager;
import xyz.templecheats.templeclient.features.module.Module;
import xyz.templecheats.templeclient.features.module.modules.client.Colors;
import xyz.templecheats.templeclient.manager.HoleManager;
import xyz.templecheats.templeclient.util.color.impl.GradientShader;
import xyz.templecheats.templeclient.util.render.RenderUtil;
import xyz.templecheats.templeclient.util.setting.impl.BooleanSetting;
import xyz.templecheats.templeclient.util.setting.impl.EnumSetting;
import xyz.templecheats.templeclient.util.setting.impl.IntSetting;

import net.minecraft.util.math.AxisAlignedBB;
import xyz.templecheats.templeclient.util.math.MathUtil;

import java.awt.*;
import java.util.ArrayList;


public class Hole extends Module {
    /*
     * Settings
     */

    private final IntSetting range = new IntSetting("Range", this, 1, 20, 5);
    private final IntSetting speed = new IntSetting("Speed", this, 1, 200, 50);
    private final EnumSetting<Mode> mode = new EnumSetting<>("Mode", this, Mode.Normal);
    private final BooleanSetting slideAnimation = new BooleanSetting("Gradient", this, false);

    /*
     * Variables
     */
    private final ArrayList<HoleInfo> renderHoles = new ArrayList<>();

    public Hole() {
        super("Hole", "Highlights holes", Keyboard.KEY_NONE, Category.Render, true);

        registerSettings(range, speed, mode, slideAnimation);
    }

    private boolean differentRenderType(final HoleManager.HolePos pos) {
        return TempleClient.holeManager.getHoles().stream().filter(holePos -> holePos.getPos().equals(pos.getPos())).anyMatch(holePos -> !holePos.getHoleType().equals(pos.getHoleType()));
    }

    private boolean holesContains(final HoleManager.HolePos pos) {
        return renderHoles.stream().anyMatch(renderHole -> renderHole.holePos.getPos().equals(pos.getPos()));
    }

    @SubscribeEvent
    public void onRender3d(RenderWorldLastEvent event) {
        TempleClient.holeManager.loadHoles(range.intValue());
        for (HoleManager.HolePos holePos : TempleClient.holeManager.getHoles()) {
            final boolean diff = differentRenderType(holePos);
            if (!holesContains(holePos) || diff) {
                HoleInfo holeInfo1 = new HoleInfo(holePos);
                renderHoles.add(holeInfo1);
            }
        }
        new ArrayList<>(renderHoles).forEach(holeInfo -> {
            if (!TempleClient.holeManager.holeManagerContains(holeInfo.holePos.getPos()) || differentRenderType(holeInfo.holePos)) {
                holeInfo.out = true;
                if (holeInfo.size <= 0.1f) {
                    renderHoles.remove(holeInfo);
                    return;
                }
            }
            holeInfo.render();
        });
    }

    public class HoleInfo {
        public final HoleManager.HolePos holePos;
        public boolean out;
        public long sys;
        public float size;

        public HoleInfo(final HoleManager.HolePos holePos) {
            this.holePos = holePos;
            this.out = false;
            this.sys = System.currentTimeMillis();
            this.size = 0.0f;
        }

        public void render() {
            size = MathUtil.lerp(size, out ? 0.0f : 1.0f, (0.02f * ForgeEventManager.deltaTime * speed.intValue() / 100.0f));
            final int index = holePos.isBedrock() ? 1 : 0;
            final Color color = Colors.INSTANCE.getGradient()[index];
            final AxisAlignedBB bb = new AxisAlignedBB(holePos.getPos());
            if (mode.value() == Mode.Normal && !slideAnimation.booleanValue()) {
                if (slideAnimation.booleanValue()) GradientShader.setup(0.5f);
                if (holePos.isDouble()) {
                    if (holePos.isWestDouble()) {
                        RenderUtil.renderGradientLine(bb.minX - 1, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);
                        RenderUtil.renderGradientLine(bb.minX - 1, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);
                        RenderUtil.renderGradientLine(bb.minX - 1, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);
                        RenderUtil.boxShader(bb.minX - 1, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);

                    } else {
                        RenderUtil.renderGradientLine(bb.minX, bb.minY, bb.minZ - 1, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);
                        RenderUtil.renderGradientLine(bb.minX, bb.minY, bb.minZ - 1, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);
                        RenderUtil.renderGradientLine(bb.minX, bb.minY, bb.minZ - 1, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);
                        RenderUtil.boxShader(bb.minX, bb.minY, bb.minZ - 1, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);

                    }
                } else {
                    RenderUtil.renderGradientLine(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);
                    RenderUtil.renderGradientLine(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);
                    RenderUtil.renderGradientLine(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);
                    RenderUtil.boxShader(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1 + size, bb.maxZ, color);
                }
                if (slideAnimation.booleanValue()) GradientShader.finish();
            } else {
                GradientShader.setup(0.5f);
                if (holePos.isDouble()) {
                    if (holePos.isWestDouble()) {
                        RenderUtil.outlineShader(bb.minX - 1, bb.minY, bb.minZ, bb.minX * (1.0f - size) + bb.maxX * size, bb.minY + 0.05f, bb.minZ * (1.0f - size) + bb.maxZ * size);
                        RenderUtil.boxShader(bb.minX - 1, bb.minY, bb.minZ, bb.minX * (1.0f - size) + bb.maxX * size, bb.minY + 0.05f, bb.minZ * (1.0f - size) + bb.maxZ * size);
                    } else {
                        RenderUtil.outlineShader(bb.minX, bb.minY, bb.minZ - 1, bb.minX * (1.0f - size) + bb.maxX * size, bb.minY + 0.05f, bb.minZ * (1.0f - size) + bb.maxZ * size);
                        RenderUtil.boxShader(bb.minX, bb.minY, bb.minZ - 1, bb.minX * (1.0f - size) + bb.maxX * size, bb.minY + 0.05f, bb.minZ * (1.0f - size) + bb.maxZ * size);
                    }
                } else {
                    RenderUtil.outlineShader(bb.minX, bb.minY, bb.minZ, bb.minX * (1.0f - size) + bb.maxX * size, bb.minY + 0.05f, bb.minZ * (1.0f - size) + bb.maxZ * size);
                    RenderUtil.boxShader(bb.minX, bb.minY, bb.minZ, bb.minX * (1.0f - size) + bb.maxX * size, bb.minY + 0.05f, bb.minZ * (1.0f - size) + bb.maxZ * size);
                }
                GradientShader.finish();
            }
        }
    }

    public enum Mode {
        Normal,
        Gradient
    }
}