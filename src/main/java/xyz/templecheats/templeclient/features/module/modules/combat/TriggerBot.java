package xyz.templecheats.templeclient.features.module.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import xyz.templecheats.templeclient.TempleClient;
import xyz.templecheats.templeclient.features.module.Module;

public class TriggerBot extends Module {

    private EntityLivingBase renderTarget;

    public TriggerBot() {
        super("TriggerBot", "Automatically attack entities that are on your crosshair", Keyboard.KEY_NONE, Category.Combat);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        RayTraceResult objectMouseOver = Minecraft.getMinecraft().objectMouseOver;

        if (objectMouseOver != null) {
            if (objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {

                Entity entity = objectMouseOver.entityHit;

                if (entity instanceof EntityPlayer && !TempleClient.friendManager.isFriend(entity.getName())) {
                    this.renderTarget = (EntityLivingBase) entity;

                    if (mc.player.getCooledAttackStrength(0) == 1) {
                        mc.playerController.attackEntity(Minecraft.getMinecraft().player, entity);
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                        mc.player.resetCooldown();
                    }
                } else {
                    this.renderTarget = null;
                }
            }
        }
    }

    @Override
    public String getHudInfo() {
        if(this.renderTarget !=null)
            return this.renderTarget.getName();
        return"";
    }
}