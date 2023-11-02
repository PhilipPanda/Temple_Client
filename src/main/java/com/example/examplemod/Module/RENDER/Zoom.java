package com.example.examplemod.Module.RENDER;

import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Zoom extends Module {
    private float originalFov;

    public Zoom() {
        super("Zoom[Z]", Keyboard.KEY_Z, Category.RENDER);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && isEnabled()) {
            // Set the FOV to 5 when the module is enabled
            Minecraft.getMinecraft().gameSettings.fovSetting = 5.0f;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        originalFov = Minecraft.getMinecraft().gameSettings.fovSetting;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        // Restore the original FOV when the module is disabled
        Minecraft.getMinecraft().gameSettings.fovSetting = originalFov;
    }
}
