package lu.jpingus.fabricmc.macrorunner;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.integrated.IntegratedServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MacroRunner {
    private static MacroRunner INSTANCE;
    private final ExecutorService executor;

    private MacroRunner() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    public static MacroRunner getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MacroRunner();
        }
        return INSTANCE;
    }

    public void run(Macro macro) {
        this.executor.submit(() -> {
            MacroRunnerMod.LOGGER.info("Running Macro {}", macro);
            try {
                MinecraftClient mc = MinecraftClient.getInstance();
                if (mc == null) {
                    MacroRunnerMod.LOGGER.warn("No good conditions to run macro mc:{} ", mc);
                    return;
                }
                if (mc.player == null || mc.getServer() == null) {
                    MacroRunnerMod.LOGGER.warn("No good conditions to run macro player:{} server:{}", mc.player, mc.getServer());
                    return;
                }

                IntegratedServer server = mc.getServer();
                ClientPlayerEntity player = mc.player;
                if (server.getCommandManager() == null || player.getCommandSource() == null) {
                    MacroRunnerMod.LOGGER.warn("No good conditions to run macro server command:{} player command:{}", server.getCommandManager(), player.getCommandSource());
                    return;
                }
                MacroRunnerMod.LOGGER.info("before command {}", macro);
                server.getCommandManager().execute(player.getCommandSource(), macro.getCommand());
                MacroRunnerMod.LOGGER.info("after command {}", macro);
                if (macro.isRepeat() && macro.isActive()) {
                    Thread.currentThread().wait(macro.getDelay() * 1000L);
                    MacroRunnerMod.LOGGER.info("repeating {}", macro);
                    MacroRunner.getInstance().run(macro);
                } else {
                    MacroRunnerMod.LOGGER.info("End of macro run {}", macro);
                }
            } catch (InterruptedException e) {
                MacroRunnerMod.LOGGER.error("Macro interrupted");
            } catch (Throwable e) {
                MacroRunnerMod.LOGGER.error("Macro interrupted by an error :{}", e.getLocalizedMessage(), e);
            } finally {
                //Always deactivate after a run.
                macro.deactivate();
            }
        });
    }
}
