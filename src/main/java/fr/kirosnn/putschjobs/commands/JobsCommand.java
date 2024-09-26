package fr.kirosnn.putschjobs.commands;

import fr.kirosnn.putschjobs.PutschJobs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JobsCommand implements CommandExecutor, Listener {

    private final PutschJobs plugin;

    public JobsCommand(PutschJobs plugin) {
        this.plugin = plugin;
        // Enregistrement de l'écouteur pour les événements liés à l'inventaire
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Cette commande ne peut être utilisée que par des joueurs.");
            return true;
        }

        Player player = (Player) sender;

        String title = ChatColor.translateAlternateColorCodes('&', plugin.getInventoryConfig().getString("main-menu.title"));
        int size = plugin.getInventoryConfig().getInt("main-menu.size") * 9;
        Inventory inventory = Bukkit.createInventory(null, size, title);

        plugin.getInventoryConfig().getConfigurationSection("main-menu.jobs").getKeys(false).forEach(jobKey -> {
            String jobName = plugin.getInventoryConfig().getString("main-menu.jobs." + jobKey + ".name");
            int slot = plugin.getInventoryConfig().getInt("main-menu.jobs." + jobKey + ".slot");
            Material itemMaterial = Material.valueOf(plugin.getInventoryConfig().getString("main-menu.jobs." + jobKey + ".item"));
            ItemStack item = new ItemStack(itemMaterial);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', jobName));
            meta.setLore(plugin.getInventoryConfig().getStringList("main-menu.jobs." + jobKey + ".lore"));
            item.setItemMeta(meta);

            inventory.setItem(slot, item);
        });

        player.openInventory(inventory);

        return true;
    }

    // Événement lors de la fermeture de l'inventaire
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', plugin.getInventoryConfig().getString("main-menu.title")))) {
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 1.0f);
        }
    }

    // Empêche les joueurs de prendre les items dans l'inventaire des jobs
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', plugin.getInventoryConfig().getString("main-menu.title")))) {
            event.setCancelled(true); // Empêche de prendre ou de déplacer des objets
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || !clickedItem.hasItemMeta()) {
                return; // Ignore si l'item n'a pas de méta
            }
            // Traitement du clic sur l'item (par exemple, ouvrir un menu de job)
            String jobName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
            event.getWhoClicked().sendMessage(ChatColor.GREEN + "Tu as sélectionné le job: " + jobName);
            // tu pourrais ajouter la logique pour ouvrir des sous-menus spécifiques aux jobs
        }
    }
}
