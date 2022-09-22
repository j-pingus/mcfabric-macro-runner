package lu.jpingus.fabricmc.macrorunner;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import net.minecraft.client.MinecraftClient;

public class InitHandler implements IInitializationHandler {
    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfigHandler(Reference.MOD_ID, new Configs());
        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        initKeyCallBacks();
    }

    private void initKeyCallBacks() {
        Configs.Generic.OPEN_CONFIG_GUI.getKeybind().setCallback((action, key) -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            if (mc.player == null)
            {
                return false;
            }
            //mc.getServer().getCommandManager().execute(mc.player.getCommandSource(),"help");
            if (key == Configs.Generic.OPEN_CONFIG_GUI.getKeybind())
            {
                GuiBase.openGui(new GuiConfigs());
            }
            return true;
        });
        KeyCallbackToggleBoolean.register(
                Configs.Generic.TOGGLE_SEND,
                ()-> MacroRunnerMod.LOGGER.info("Toggle send on"),
                ()-> MacroRunnerMod.LOGGER.info("Toggle send off")
        );
    }
}
