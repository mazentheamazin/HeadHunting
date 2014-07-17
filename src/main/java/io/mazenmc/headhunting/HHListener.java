package io.mazenmc.headhunting;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class HHListener implements Listener{

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        meta.setOwner(event.getEntity().getName());
        head.setItemMeta(meta);

        event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(), head);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        event.setLine(0, ChatColor.translateAlternateColorCodes('&', event.getLine(0)));
    }
}
