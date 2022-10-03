package lu.jpingus.fabricmc.macrorunner.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.config.options.ConfigString;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

import java.util.List;

public class MacroConfigGroup implements ConfigGroupInterface {
    public final ConfigBooleanHotkeyed toggleSend;
    public final ConfigString serverCommand;
    public final ConfigBoolean loopSend;
    public final ConfigInteger sendCooldown;
    final List<IConfigBase> options;
    final List<IHotkey> hotkeys;
    final int number;

    protected MacroConfigGroup(int number) {
        this.number = number;
        toggleSend = new ConfigBooleanHotkeyed("toggleSend", false, "KP_" + number, KeybindSettings.RELEASE_EXCLUSIVE, "A hotkey to start/stop the command sending", "Sending command");
        serverCommand = new ConfigString("serverCommand", "help", "The command to be sent to the server (without '/' to start)");
        loopSend = new ConfigBoolean("loopSend", false, "Send again after cooldown", "Loop sending command");
        sendCooldown = new ConfigInteger("sendCooldown", 5, 1, 60, "How many seconds to wait before sending again");
        options = ImmutableList.of(toggleSend, serverCommand, loopSend, sendCooldown);
        hotkeys = ImmutableList.of(toggleSend);
    }

    @Override
    public String getName() {
        return "Macro"+number;
    }

    public void resetConfigBeforeSave() {
        toggleSend.setBooleanValue(false);
    }

    @Override
    public List<IConfigBase> getOptions() {
        return options;
    }

    @Override
    public List<IHotkey> getHotkeys() {
        return hotkeys;
    }
}
