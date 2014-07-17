package io.mazenmc.headhunting;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class HeadHunting extends JavaPlugin {

    /*                                                   Objects                                                     */
    private static HeadHunting instance;
    private Economy economy;


    /*                                                Override Methods                                               */

    @Override
    public void onEnable() {
        //Set instances
        instance = this;

        //Setup economy
        setupEconomy();

        //Register commands and listeners
        getCommand("sellhead").setExecutor(new HeadSellCommand());
        getServer().getPluginManager().registerEvents(new HHListener(), this);
    }

    @Override
    public void onDisable() {
        //Avoid memory leaks
        instance = null;
    }

    /*                                                   Methods                                                     */

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public void processSale(final Player player, final SkullMeta meta) {
        player.sendMessage(ChatColor.GRAY + "(Processing Payment)...");

        new BukkitRunnable() {
            @Override
            public void run() {
                OfflinePlayer target = Bukkit.getOfflinePlayer(meta.getOwner());
                double deductable = economy.getBalance(target) * 0.15;

                economy.withdrawPlayer(target, deductable);
                economy.depositPlayer(player, deductable);

                player.sendMessage(ChatColor.GOLD + "Processed payment! You have sold " + target.getName() + "'s head for " + deductable);

                player.getInventory().remove(Material.SKULL_ITEM);
            }
        }.runTaskAsynchronously(this);
    }

    /**
     * Singleton
     * @return Plugin instance
     */
    static HeadHunting getInstance() {
        return instance;
    }
}
