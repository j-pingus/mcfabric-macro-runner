package lu.jpingus.fabricmc.macrorunner;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;

import java.io.File;
import java.util.List;

public class Configs implements IConfigHandler {
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";
    private static final int CONFIG_VERSION = 1;

    @Override
    public void load() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);
            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();
                ConfigUtils.readConfigBase(root, "Generic", Configs.Generic.OPTIONS);
            }
        }
    }

    @Override
    public void save() {
        File dir = FileUtils.getConfigDirectory();
        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();
            ConfigUtils.writeConfigBase(root, "Generic", Configs.Generic.OPTIONS);
            Generic.TOGGLE_SEND.setBooleanValue(false);
            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    public static class Generic {
        public static final ConfigHotkey OPEN_CONFIG_GUI = new ConfigHotkey("openConfigGui", "R,C", "A hotkey to open the in-game Config GUI");
        public static final ConfigBooleanHotkeyed TOGGLE_SEND = new ConfigBooleanHotkeyed("toggleSend", false, "R", KeybindSettings.RELEASE_EXCLUSIVE, "A hotkey to start/stop the command sending", "Sending command");
        public static final ConfigString SERVER_COMMAND = new ConfigString("serverCommand", "help", "The command to be sent to the server (without '/' to start)");
        public static final ConfigBoolean LOOP_SEND = new ConfigBoolean("loopSend",false,"Send again after cooldown","Loop sending command");
        public static final ConfigInteger SEND_COOLDOWN = new ConfigInteger("sendCooldown", 5, 1, 60, "How many seconds to wait before sending again");
        public static final List<IConfigBase> OPTIONS = ImmutableList.of(
                 OPEN_CONFIG_GUI, SERVER_COMMAND, SEND_COOLDOWN, TOGGLE_SEND, LOOP_SEND
        );
        public static final List<IHotkey> HOTKEY_LIST = ImmutableList.of(
                OPEN_CONFIG_GUI, TOGGLE_SEND
        );

    }
}
