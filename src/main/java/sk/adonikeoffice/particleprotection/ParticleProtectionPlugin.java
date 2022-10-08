package sk.adonikeoffice.particleprotection;

import lombok.Getter;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;
import sk.adonikeoffice.particleprotection.command.AnimationCommand;
import sk.adonikeoffice.particleprotection.listener.PlayerListener;
import sk.adonikeoffice.particleprotection.task.ParticleTask;

public final class ParticleProtectionPlugin extends SimplePlugin {

    @Getter
    private ParticleTask particleTask;

    @Override
    protected void onPluginStart() {

    }

    @Override
    protected void onReloadablesStart() {
        particleTask = new ParticleTask();
        Common.runTimer(20, particleTask);

        this.registerEvents(new PlayerListener());
        this.registerCommand(new AnimationCommand());
    }

    public static ParticleProtectionPlugin getInstance() {
        return (ParticleProtectionPlugin) SimplePlugin.getInstance();
    }

}
