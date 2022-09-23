package lu.jpingus.fabricmc.macrorunner;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.config.options.ConfigString;

public class Macro {
    private final String command;
    private final int delay;
    private final boolean repeat;
    private final ConfigBoolean active;

    public Macro(ConfigString command, ConfigInteger delay, ConfigBoolean repeat,ConfigBoolean active) {
        this.command = command.getStringValue();
        this.delay = delay.getIntegerValue();
        this.repeat = repeat.getBooleanValue();
        this.active=active;
    }

    public String getCommand() {
        return command;
    }

    public int getDelay() {
        return delay;
    }

    public boolean isRepeat() {
        return repeat;
    }
    public boolean isActive() {
        return active.getBooleanValue();
    }

    @Override
    public String toString() {
        return "Macro{" +
                "command='" + command + '\'' +
                ", delay=" + delay +
                ", repeat=" + repeat +
                ", active=" + active.getBooleanValue() +
                '}';
    }

    public void deactivate() {
        active.setBooleanValue(false);
    }
}
