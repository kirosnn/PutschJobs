package fr.kirosnn.putschjobs.listeners;

import fr.kirosnn.putschjobs.PutschJobs;
import fr.kirosnn.putschjobs.Job;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class JobListener implements Listener {

    private final PutschJobs plugin;

    public JobListener(PutschJobs plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        String blockType = event.getBlock().getType().name();

        for (Job job : plugin.getJobs().values()) {
            if (job.isObjective("block-place-" + blockType)) {
                job.addExperience(player, "block-place-" + blockType);
            } else if (job.isObjective("block-place")) {
                job.addExperience(player, "block-place");
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String blockType = event.getBlock().getType().name();

        for (Job job : plugin.getJobs().values()) {
            if (job.isObjective("block-break-" + blockType)) {
                job.addExperience(player, "block-break-" + blockType);
            } else if (job.isObjective("block-break")) {
                job.addExperience(player, "block-break");
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity().getKiller();
            EntityType entityType = event.getEntity().getType();

            for (Job job : plugin.getJobs().values()) {
                if (job.isObjective("mob-kill-" + entityType.name())) {
                    job.addExperience(player, "mob-kill-" + entityType.name());
                } else if (job.isObjective("mob-kill")) {
                    job.addExperience(player, "mob-kill");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();

        for (Job job : plugin.getJobs().values()) {
            if (job.isObjective("fishing")) {
                job.addExperience(player, "fishing");
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Ajoute d'autres événements si nécessaire
    }
}
