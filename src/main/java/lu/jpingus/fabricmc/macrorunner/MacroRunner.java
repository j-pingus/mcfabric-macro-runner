package lu.jpingus.fabricmc.macrorunner;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MacroRunner {
    private static MacroRunner INSTANCE;
    private final ExecutorService executor;
    private CommandDispatcher<CommandSource> commandDispatcher;

    private MacroRunner() {
        this.executor = Executors.newSingleThreadExecutor();
        this.commandDispatcher = null;
    }

    public static MacroRunner getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MacroRunner();
        }
        return INSTANCE;
    }

    public boolean isReady() {
        return this.commandDispatcher != null;
    }

    public void setCommandDispatcher(CommandDispatcher<CommandSource> commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    public void run(Macro macro) {
        this.executor.submit(() -> {
            MacroRunnerMod.LOGGER.info("Running Macro {}", macro);
            if (!isReady()) {
                MacroRunnerMod.printPlayerWarningMessage("This plugin will only run when playing in multiplayer mode");
            }
            try {
                MinecraftClient mc = MinecraftClient.getInstance();

                if (mc == null) {
                    MacroRunnerMod.LOGGER.warn("No good conditions to run macro mc:{} ", mc);
                    return;
                }
                if (mc.player == null) {
                    MacroRunnerMod.LOGGER.warn("No player");
                    return;
                }
                ClientPlayerEntity player = mc.player;
                if(macro.getParseResult()==null){
                    MacroRunnerMod.LOGGER.info("parsing command {}", macro);
                    macro.setParseResult(this.commandDispatcher.parse(macro.getCommand(), player.getCommandSource()));
                }
                MacroRunnerMod.LOGGER.info("before command {}", macro);
                MinecraftClient.getInstance().player.sendCommand(macro.getCommand());
                MacroRunnerMod.LOGGER.info("after command {}", macro);
                if (macro.isRepeat() && macro.isActive()) {
                    Thread.currentThread().wait(macro.getDelay() * 1000L);
                    MacroRunnerMod.LOGGER.info("repeating {}", macro);
                    MacroRunner.getInstance().run(macro);
                }
            } catch (InterruptedException e) {
                MacroRunnerMod.LOGGER.error("Macro interrupted");
            } catch (Throwable e) {
                MacroRunnerMod.LOGGER.error("Macro interrupted by an error :{}", e.getLocalizedMessage(), e);
            } finally {
                MacroRunnerMod.LOGGER.info("End of macro run {}", macro);
                //Always deactivate after a run.
                macro.deactivate();
            }
        });
    }
}
