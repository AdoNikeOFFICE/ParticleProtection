package sk.adonikeoffice.particleprotection.task;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.mineacademy.fo.MathUtil;
import org.mineacademy.fo.remain.CompParticle;
import org.mineacademy.fo.remain.Remain;
import sk.adonikeoffice.particleprotection.PlayerCache;

import java.util.ArrayList;
import java.util.List;

public class ParticleTask extends BukkitRunnable {

    @Override
    public void run() {
        for (final Player player : Remain.getOnlinePlayers())
            if (PlayerCache.from(player).isSeeingAnimation()) {
                final List<Location> actualCircles = new ArrayList<>();
                final Location location = player.getLocation().add(player.getLocation().getDirection().normalize());

                final int degreeStep = 1;

                for (int degree = 0; degree < 360; degree += degreeStep) {
                    final double radians = Math.toRadians(degree);

                    final double circleRadius = 3;

                    final double x = Math.cos(radians) * circleRadius;
                    final double z = Math.sin(radians) * circleRadius;

                    final org.bukkit.util.Vector vector = new Vector(x, 0, z);

                    MathUtil.rotateAroundAxisX(vector, location.getPitch() + 90);
                    MathUtil.rotateAroundAxisY(vector, location.getYaw());

                    actualCircles.add(location.clone());
                    CompParticle.VILLAGER_HAPPY.spawn(location.clone().add(vector));
                }

                for (final Location actualCircle : actualCircles)
                    for (final Entity entity : Remain.getNearbyEntities(actualCircle, 2))
                        if (!(entity instanceof Player)) {
                            final Location entityLocation = entity.getLocation().clone();
                            final Vector unitVector = entityLocation.toVector().subtract(player.getLocation().toVector()).normalize();

                            entity.setVelocity(unitVector.multiply(3));
                        }
            }
    }

}
