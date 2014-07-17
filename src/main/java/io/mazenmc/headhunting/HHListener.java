package io.mazenmc.headhunting;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HHListener implements Listener {

    /*                                                 Listeners                                                     */

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        meta.setOwner(event.getEntity().getName());
        head.setItemMeta(meta);

        event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(), head);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        event.setLine(0, ChatColor.translateAlternateColorCodes('&', event.getLine(0)));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Sign && ChatColor.stripColor(((Sign) event.getClickedBlock().getState()).getLine(0)).equals("[Head]") &&
                event.getItem().getItemMeta() != null && event.getItem().getItemMeta() instanceof SkullMeta) {
            HeadHunting.getInstance().processSale(event.getPlayer(), (SkullMeta) event.getItem().getItemMeta());
        }
    }
}
