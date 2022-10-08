package sk.adonikeoffice.particleprotection.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sk.adonikeoffice.particleprotection.PlayerCache;

public final class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        PlayerCache.from(player);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        PlayerCache.from(player).removeFromMemory();
    }

}