package lu.jpingus.fabricmc.macrorunner;

import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.InfoUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacroRunnerMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(Reference.MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Initializing {}", Reference.MOD_NAME);
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
    }

    public static void printPlayerWarningMessage(String message) {
        String toPrint = GuiBase.TXT_DARK_RED + StringUtils.translate(message) + GuiBase.TXT_RST;
        InfoUtils.printActionbarMessage(toPrint);
    }
    public static void printPlayerMessage(String message) {
        String toPrint = GuiBase.TXT_BLUE + StringUtils.translate(message) + GuiBase.TXT_RST;
        InfoUtils.printActionbarMessage(toPrint);
    }
}
