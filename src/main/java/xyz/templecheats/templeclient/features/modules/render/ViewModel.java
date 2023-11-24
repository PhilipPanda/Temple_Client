package xyz.templecheats.templeclient.features.modules.render;

import xyz.templecheats.templeclient.TempleClient;
import xyz.templecheats.templeclient.features.modules.Module;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import xyz.templecheats.templeclient.gui.clickgui.setting.Setting;
import xyz.templecheats.templeclient.gui.clickgui.setting.SettingsManager;

public class ViewModel extends Module {
    private Setting xPosMain;
    private Setting yPosMain;
    private Setting zPosMain;
    private Setting xSizeMain;
    private Setting ySizeMain;
    private Setting zSizeMain;
    private Setting rotateYawMain;

    public ViewModel() {
        super("ViewModel", Keyboard.KEY_NONE, Category.RENDER);

        SettingsManager settingsManager = TempleClient.instance.settingsManager;

        double defaultXMain = 0.0;
        double defaultYMain = 0.0;
        double defaultZMain = 0.0;
        double defaultSizeXMain = 1.0;
        double defaultSizeYMain = 1.0;
        double defaultSizeZMain = 1.0;
        double defaultRotateYawMain = 0.0;

        xPosMain = new Setting("OffSetXMain", this, defaultXMain, -3.0, 3.0, false);
        yPosMain = new Setting("OffSetYMain", this, defaultYMain, -3.0, 3.0, false);
        zPosMain = new Setting("OffSetZMain", this, defaultZMain, -3.0, 3.0, false);
        xSizeMain = new Setting("SizeXMain", this, defaultSizeXMain, 0.0, 4.0, false);
        ySizeMain = new Setting("SizeYMain", this, defaultSizeYMain, 0.0, 4.0, false);
        zSizeMain = new Setting("SizeZMain", this, defaultSizeZMain, 0.0, 4.0, false);
        rotateYawMain = new Setting("RotateYawMain", this, defaultRotateYawMain, 0.0, 360.0, false);

        settingsManager.rSetting(xPosMain);
        settingsManager.rSetting(yPosMain);
        settingsManager.rSetting(zPosMain);
        settingsManager.rSetting(xSizeMain);
        settingsManager.rSetting(ySizeMain);
        settingsManager.rSetting(zSizeMain);
        settingsManager.rSetting(rotateYawMain);
    }

    @SubscribeEvent
    public void onRender(RenderSpecificHandEvent event) {
        double xMain = TempleClient.instance.settingsManager.getSettingByName(this.name, "OffSetXMain").getValDouble();
        double yMain = TempleClient.instance.settingsManager.getSettingByName(this.name, "OffSetYMain").getValDouble();
        double zMain = TempleClient.instance.settingsManager.getSettingByName(this.name, "OffSetZMain").getValDouble();
        double xSize = TempleClient.instance.settingsManager.getSettingByName(this.name, "SizeXMain").getValDouble();
        double ySize = TempleClient.instance.settingsManager.getSettingByName(this.name, "SizeYMain").getValDouble();
        double zSize = TempleClient.instance.settingsManager.getSettingByName(this.name, "SizeZMain").getValDouble();
        double rotateYaw = TempleClient.instance.settingsManager.getSettingByName(this.name, "RotateYawMain").getValDouble();

        GL11.glTranslated(xMain, yMain, zMain);
        GL11.glScaled(xSize, ySize, zSize);
        GL11.glRotatef((float) rotateYaw, 0, 1, 0);
    }
}
