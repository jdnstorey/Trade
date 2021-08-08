package me.BlackThunder.Trade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class TradeCommand implements CommandExecutor {

    HashMap<Player, Player> requestTrade = new HashMap<Player, Player>();

    TradeListener tradeList;
    public TradeCommand(TradeListener listener){
        tradeList = listener;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("trade")){
                if(args.length == 2){
                    if(args[0].equals("request")){
                         Player tradeWith = Bukkit.getPlayer(args[1]);
                        if(tradeWith != null){
                            p.sendMessage(ChatColor.GOLD + "Trade request sent to " + args[1]);
                            requestTrade.put(tradeWith, p);
                            tradeWith.sendMessage(ChatColor.BLUE + p.getName() + " would like to trade with you");
                            tradeWith.sendMessage(ChatColor.GREEN + "Type /trade accept");
                        } else {
                            p.sendMessage(ChatColor.RED + args[1] + " not found");
                        }
                    }
                } else if(args.length == 1){
                     if(args[0].equals("accept")){
                         if(requestTrade.containsKey(p)){
                             Player tradeWith = requestTrade.get(p);
                             if(tradeWith != null){
                                 Inventory tradeInv = Bukkit.createInventory(null, 27, ChatColor.BLUE + "" + ChatColor.BOLD + "TRADE MENU");

                                 ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                                 ItemStack block = new ItemStack(Material.RED_CONCRETE);
                                 tradeInv.setItem(9, glass);
                                 tradeInv.setItem(10, glass);
                                 tradeInv.setItem(11, glass);
                                 tradeInv.setItem(12, glass);
                                 tradeInv.setItem(13, glass);
                                 tradeInv.setItem(14, glass);
                                 tradeInv.setItem(15, glass);
                                 tradeInv.setItem(16, glass);
                                 tradeInv.setItem(17, block);

                                 p.openInventory(tradeInv);
                                 tradeWith.openInventory(tradeInv);

                                 tradeList.addPlayersToTradeList(p, tradeWith);
                             } else {
                                 p.sendMessage(ChatColor.RED + "Player is no longer online");
                                 requestTrade.remove(p);
                             }

                         } else {
                             p.sendMessage(ChatColor.RED + "You do not have any incoming trade requests");
                         }
                     }
                }
            }
            return true;
        }
        return false;
    }
}
