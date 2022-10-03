package lu.jpingus.fabricmc.macrorunner.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import lu.jpingus.fabricmc.macrorunner.Reference;

import java.io.File;

public class Configs implements IConfigHandler {
    public static final Generic GENERIC = new Generic();
    private static final MacroConfigGroup MACRO_1 = new MacroConfigGroup(1);
    private static final MacroConfigGroup MACRO_2 = new MacroConfigGroup(2);
    private static final MacroConfigGroup MACRO_3 = new MacroConfigGroup(3);
    private static final MacroConfigGroup MACRO_4 = new MacroConfigGroup(4);
    private static final MacroConfigGroup MACRO_5 = new MacroConfigGroup(5);
    private static final MacroConfigGroup MACRO_6 = new MacroConfigGroup(6);
    private static final MacroConfigGroup MACRO_7 = new MacroConfigGroup(7);
    private static final MacroConfigGroup MACRO_8 = new MacroConfigGroup(8);
    private static final MacroConfigGroup MACRO_9 = new MacroConfigGroup(9);
    public static final MacroConfigGroup[] MACROS = {
            MACRO_1, MACRO_2, MACRO_3, MACRO_4, MACRO_5, MACRO_6, MACRO_7, MACRO_8, MACRO_9};
    public static final ConfigGroupInterface[] GROUPS = {
            GENERIC,
            MACRO_1, MACRO_2, MACRO_3, MACRO_4, MACRO_5, MACRO_6, MACRO_7, MACRO_8, MACRO_9};
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";
    private static final int CONFIG_VERSION = 1;

    @Override
    public void load() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);
            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();
                ConfigUtils.readConfigBase(root, GENERIC.getName(), GENERIC.getOptions());
                for (ConfigGroupInterface group : MACROS) {
                    ConfigUtils.readConfigBase(root, group.getName(), group.getOptions());
                }
            }
        }
    }

    @Override
    public void save() {
        File dir = FileUtils.getConfigDirectory();
        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();
            ConfigUtils.writeConfigBase(root, GENERIC.getName(), GENERIC.getOptions());
            for (MacroConfigGroup group : MACROS) {
                group.resetConfigBeforeSave();
                ConfigUtils.writeConfigBase(root, group.getName(), group.getOptions());
            }
            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }


}
