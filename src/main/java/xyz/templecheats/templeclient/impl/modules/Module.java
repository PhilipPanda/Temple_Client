package xyz.templecheats.templeclient.impl.modules;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import xyz.templecheats.templeclient.TempleClient;
import xyz.templecheats.templeclient.api.setting.SettingHolder;

public class Module extends SettingHolder {
    private final String description;
    public boolean toggled;
    public int KeyCode;
    public Category category;
    public static Minecraft mc = Minecraft.getMinecraft();
    private boolean queueEnable;
    
    public Module(String name, String description, int keyCode, Category c) {
        super(name);
        this.description = description;
        this.KeyCode = keyCode;
        this.category = c;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public boolean isEnabled() {
        return toggled;
    }
    
    public int getKey() {
        return KeyCode;
    }
    
    //do not override
    public final void onUpdateInternal() {
        if(mc.player != null) {
            if(this.queueEnable) {
                this.queueEnable = false;
                this.onEnable();
            }
            
            if(this.isToggled()) {
                this.onUpdate();
            }
        }
        
        this.onUpdateConstant();
    }
    
    public void onUpdate() {}
    
    public void onUpdateConstant() {}
    
    public void onRenderWorld(float partialTicks) {}
    
    public void onEnable() {}
    
    public void onDisable() {}
    
    public String getHudInfo() {
        return "";
    }
    
    public void enable() {
        this.toggled = true;
        MinecraftForge.EVENT_BUS.register(this);
        TempleClient.eventBus.addEventListener(this);
        
        if(mc.player != null) {
            this.onEnable();
        } else {
            this.queueEnable = true;
        }
    }
    
    public void disable() {
        this.toggled = false;
        MinecraftForge.EVENT_BUS.unregister(this);
        TempleClient.eventBus.removeEventListener(this);
        
        if(mc.player != null) {
            this.onDisable();
        }
    }
    
    public void toggle() {
        this.setToggled(!this.isToggled());
    }
    
    public void setToggled(boolean toggled) {
        //dont do anything if the toggled state is the same
        if(toggled == this.toggled) return;
        
        if(toggled) {
            this.enable();
        } else {
            this.disable();
        }
    }
    
    public boolean isToggled() {
        return toggled;
    }
    
    public void setKey(int key) {
        this.KeyCode = key;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public enum Category {
        Chat,
        Combat,
        Miscelleaneous,
        Movement,
        Player,
        Render,
        World,
        Client
    }
}