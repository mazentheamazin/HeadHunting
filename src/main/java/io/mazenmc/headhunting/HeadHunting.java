package io.mazenmc.headhunting;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class HeadHunting extends JavaPlugin {

    private static HeadHunting instance;

    private Economy economy;


    public void onEnable() {
        //Set instances
        instance = this;

        //Setup economy
        setupEconomy();

        //Register commands and listeners
        getCommand("sellhead").setExecutor(new HeadSellCommand());
        getServer().getPluginManager().registerEvents(new HHListener(), this);
    }

    public void onDisable() {
        //Avoid memory leaks
        instance = null;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public void processSale(final Player player, final SkullMeta meta) {
        player.sendMessage(ChatColor.GRAY + "(Processing Payment)...");

        new Thread() {
            @Override
            public void run() {
                OfflinePlayer target = Bukkit.getOfflinePlayer(meta.getOwner());
                double deductable = economy.getBalance(target) * 0.15;

                economy.withdrawPlayer(target, deductable);
                economy.depositPlayer(player, deductable);

                player.sendMessage(ChatColor.GOLD + "Processed payment! You have sold " + target.getName() + "'s head for " + deductable);
            }
        }.start();
    }

    static HeadHunting getInstance() {
        return instance;
    }
}
