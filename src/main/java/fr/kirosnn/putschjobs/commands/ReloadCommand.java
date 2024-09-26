package fr.kirosnn.putschjobs.commands;

import fr.kirosnn.putschjobs.PutschJobs;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final PutschJobs plugin;

    public ReloadCommand(PutschJobs plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("jobs")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("jobs.admin")) {
                    sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission pour exécuter cette commande.");
                    return true;
                }

                try {
                    plugin.reloadConfig();
                    plugin.loadInventoryConfig();
                    plugin.loadJobs();

                    sender.sendMessage(ChatColor.GREEN + "Le plugin a été rechargé avec succès.");
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + "Une erreur s'est produite lors du rechargement du plugin.");
                    plugin.getLogger().severe(ChatColor.RED + "Erreur lors du rechargement : " + e.getMessage());
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }
}
