package me.BlackThunder.Trade;

import org.bukkit.plugin.java.JavaPlugin;

public class TradeGUI extends JavaPlugin {

    public void onEnable() {
        TradeListener trader = new TradeListener();
        getCommand("trade").setExecutor(new TradeCommand(trader));
        getServer().getPluginManager().registerEvents(trader, this);
    }

}
