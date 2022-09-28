package lu.jpingus.fabricmc.macrorunner;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import lu.jpingus.fabricmc.macrorunner.config.Configs;
import lu.jpingus.fabricmc.macrorunner.config.Generic;
import lu.jpingus.fabricmc.macrorunner.config.MacroConfigGroup;
import net.minecraft.client.MinecraftClient;

public class InitHandler implements IInitializationHandler {
    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfigHandler(Reference.MOD_ID, new Configs());
        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        initKeyCallBacks();
    }

    private void initKeyCallBacks() {
        Generic.OPEN_CONFIG_GUI.getKeybind().setCallback((action, key) -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            if (mc.player == null) {
                return false;
            }
            if (key == Generic.OPEN_CONFIG_GUI.getKeybind()) {
                GuiBase.openGui(new GuiConfigs());
            }
            return true;
        });
        for (MacroConfigGroup group : Configs.MACROS) {
            KeyCallbackToggleBoolean.register(
                    group.toggleSend,
                    () -> MacroRunner.getInstance().run(new Macro(
                            group.serverCommand,
                            group.sendCooldown,
                            group.loopSend,
                            group.toggleSend
                    ))
            );
        }
    }
}
