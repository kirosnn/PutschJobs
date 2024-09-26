package fr.kirosnn.putschjobs.commands;

import fr.kirosnn.putschjobs.Jobs.Job;
import fr.kirosnn.putschjobs.Jobs.JobObjective;
import fr.kirosnn.putschjobs.Managers.JobManager;
import fr.kirosnn.putschjobs.PutschJobs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class JobsCommand implements CommandExecutor {

    private final PutschJobs plugin;
    private final JobManager jobManager;

    public JobsCommand(PutschJobs plugin, JobManager jobManager) {
        this.plugin = plugin;
        this.jobManager = jobManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seuls les joueurs peuvent utiliser cette commande !");
            return true;
        }

        Player player = (Player) sender;

        Inventory jobMenu = Bukkit.createInventory(null, 54, plugin.getConfig().getString("interface.name"));

        Map<String, Job> jobs = jobManager.getJobs();
        int slot = 0;

        for (Map.Entry<String, Job> entry : jobs.entrySet()) {
            Job job = entry.getValue();

            ItemStack item = new ItemStack(Material.DIAMOND_AXE);

            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(job.getFriendlyName());

            List<String> lore = meta.getLore();
            for (Map.Entry<String, JobObjective> objectiveEntry : job.getObjectives().entrySet()) {
                JobObjective objective = objectiveEntry.getValue();

                lore.add(objective.getDescription() + " (" + objective.getXp() + " XP");
            }

            meta.setLore(lore);
            item.setItemMeta(meta);

            jobMenu.setItem(slot++, item);
        }

        player.openInventory(jobMenu);
        return true;
    }
}
