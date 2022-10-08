package sk.adonikeoffice.particleprotection.command;

import org.mineacademy.fo.command.SimpleCommand;
import sk.adonikeoffice.particleprotection.PlayerCache;

public class AnimationCommand extends SimpleCommand {

    public AnimationCommand() {
        super("animation|anim|a");

        this.setPermission(null);
    }

    @Override
    protected void onCommand() {
        this.checkConsole();

        final PlayerCache cache = PlayerCache.from(this.getPlayer());
        final boolean isSeeingAnimation = cache.isSeeingAnimation();

        cache.setSeeingAnimation(!isSeeingAnimation);
        this.tell("You have " + (isSeeingAnimation ? "disabled" : "enabled") + " the animation.");
    }

}