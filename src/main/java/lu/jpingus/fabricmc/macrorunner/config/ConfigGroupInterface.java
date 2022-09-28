package lu.jpingus.fabricmc.macrorunner.config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.hotkeys.IHotkey;

import java.util.List;

public interface ConfigGroupInterface {
    List<IConfigBase> getOptions();

    List<IHotkey> getHotkeys();

    String getName();

}
