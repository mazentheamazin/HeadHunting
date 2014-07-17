package io.mazenmc.headhunting;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SellHeadCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(cs instanceof Player) {
            Player player = (Player) cs;
            ItemStack inHand = player.getItemInHand();

            if(inHand.getItemMeta() == null || !(inHand.getItemMeta() instanceof SkullMeta)) {
                player.sendMessage(ChatColor.DARK_RED + "The item in hand cannot be sold!");
                return true;
            }

            HeadHunting.getInstance().processSale(player, (SkullMeta) inHand.getItemMeta());
        }else{
            cs.sendMessage(ChatColor.DARK_RED + "Only players can run this command!");
        }
        return false;
    }
}
