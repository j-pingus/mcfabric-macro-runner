package lu.jpingus.fabricmc.macrorunner;

import com.mojang.brigadier.ParseResults;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.config.options.ConfigString;
import net.minecraft.command.CommandSource;

public class Macro {
    private final String command;
    private final int delay;
    private final boolean repeat;
    private final ConfigBoolean active;
    private ParseResults<CommandSource> parseResult;

    public void setParseResult(ParseResults<CommandSource> parseResult) {
        this.parseResult = parseResult;
    }

    public ParseResults<CommandSource> getParseResult() {
        return parseResult;
    }

    public Macro(ConfigString command, ConfigInteger delay, ConfigBoolean repeat, ConfigBoolean active) {
        this.command = command.getStringValue();
        this.delay = delay.getIntegerValue();
        this.repeat = repeat.getBooleanValue();
        this.active=active;
        this.parseResult=null;
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
