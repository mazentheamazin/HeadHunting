package io.mazenmc.headhunting;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class HeadHunting extends JavaPlugin{

    private static HeadHunting instance;

    private Economy economy;


    public void onEnable() {
        instance = this;

        setupEconomy();
    }

    public void onDisable() {
        instance = null;
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public void processSale(Player player, SkullMeta meta) {
        //
    }

    static HeadHunting getInstance() {
        return instance;
    }
}
