package fr.kirosnn.putschjobs.listeners;

import fr.kirosnn.putschjobs.PutschJobs;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuListener implements Listener {

    private final PutschJobs plugin;

    public MenuListener(PutschJobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        Inventory inv = event.getInventory();

        if (inv.getName().equals(plugin.getConfig().getString("interface.name"))) {
            event.setCancelled(true);

            if (clickedItem.getType() == Material.DIAMOND_AXE) {
                openGuerrierMenu(player);
            }
        }
    }

    private void openGuerrierMenu(Player player) {
        Inventory guerrierMenu = Bukkit.createInventory(null, 36, plugin.getConfig().getString("interface.guerrier_menu.name"));

        ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        ItemMeta headMeta = playerHead.getItemMeta();
        String playerName = PlaceholderAPI.setPlaceholders(player, "%player_name%");

        headMeta.setDisplayName(playerName);

        playerHead.setItemMeta(headMeta);

        guerrierMenu.setItem(plugin.getConfig().getInt("interface.guerrier_menu.items.player_head.slot"), playerHead);

        ItemStack waterPotion = new ItemStack(Material.POTION, 1, (short)0);

        guerrierMenu.setItem(plugin.getConfig().getInt("interface.guerrier_menu.items.potion.slot"), waterPotion);

        ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT);

        guerrierMenu.setItem(plugin.getConfig().getInt("interface.guerrier_menu.items.gold_ingot.slot"), goldIngot);

        player.openInventory(guerrierMenu);
    }
}
