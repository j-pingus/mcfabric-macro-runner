package lu.jpingus.fabricmc.macrorunner.config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.hotkeys.IHotkey;

import java.util.List;

public class ConfigGroup implements ConfigGroupInterface {
    final List<IConfigBase> options;
    final List<IHotkey> hotkeys;

    protected ConfigGroup(List<IConfigBase> options, List<IHotkey> hotkeys) {
        this.options = options;
        this.hotkeys = hotkeys;
    }

    public List<IConfigBase> getOptions() {
        return options;
    }

    public List<IHotkey> getHotkeys() {
        return hotkeys;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
