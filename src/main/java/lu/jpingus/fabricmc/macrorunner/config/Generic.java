package lu.jpingus.fabricmc.macrorunner.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

public class Generic extends ConfigGroup {
    public static final ConfigHotkey OPEN_CONFIG_GUI = new ConfigHotkey("openConfigGui", "R,C", "A hotkey to open the in-game Config GUI");

    Generic() {
        super(
                ImmutableList.of(OPEN_CONFIG_GUI),
                ImmutableList.of(OPEN_CONFIG_GUI)
        );
    }
}
