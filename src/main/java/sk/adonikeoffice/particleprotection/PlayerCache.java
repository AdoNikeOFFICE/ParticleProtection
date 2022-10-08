package sk.adonikeoffice.particleprotection;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.mineacademy.fo.constants.FoConstants;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Getter
public final class PlayerCache extends YamlConfig {

    private static final Map<UUID, PlayerCache> cacheMap = new HashMap<>();

    private final UUID uniqueId;
    private final String playerName;

    @Getter
    private boolean seeingAnimation;

    private PlayerCache(final String name, final UUID uniqueId) {
        this.playerName = name;
        this.uniqueId = uniqueId;

        this.setPathPrefix("Players." + uniqueId.toString());
        this.loadConfiguration(NO_DEFAULT, FoConstants.File.DATA);

        this.save();
    }

    @Override
    protected void onLoad() {
        this.seeingAnimation = this.getBoolean("Seeing_Animation", false);
    }

    @Override
    protected void onSave() {
        this.set("Seeing_Animation", this.seeingAnimation);
    }

    public void setSeeingAnimation(final boolean seeingAnimation) {
        this.seeingAnimation = seeingAnimation;

        this.save();
    }

    public void removeFromMemory() {
        synchronized (cacheMap) {
            cacheMap.remove(this.uniqueId);
        }
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof PlayerCache && ((PlayerCache) obj).getUniqueId().equals(this.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uniqueId);
    }

    @Override
    public String toString() {
        return "PlayerCache{" + this.playerName + ", " + this.uniqueId + "}";
    }

    /* ------------------------------------------------------------------------------- */
    /* Static access */
    /* ------------------------------------------------------------------------------- */

    public static PlayerCache from(final Player player) {
        synchronized (cacheMap) {
            final UUID uniqueId = player.getUniqueId();
            final String playerName = player.getName();

            PlayerCache cache = cacheMap.get(uniqueId);

            if (cache == null) {
                cache = new PlayerCache(playerName, uniqueId);

                cacheMap.put(uniqueId, cache);
            }

            return cache;
        }
    }

}