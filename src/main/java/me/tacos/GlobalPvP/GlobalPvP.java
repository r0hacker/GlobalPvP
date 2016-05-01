package me.tacos.GlobalPvP;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GlobalPvP extends JavaPlugin {

    private Location loc;

    @Override
    public void onEnable() {
        loc = loadLocation(getConfig().getString("loc"));
    }

    @Override
    public void onDisable() {
        getConfig().set("loc", saveLocation(loc));
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;

        Player p = (Player) sender;
        if(args.length > 0 && args[0].equalsIgnoreCase("set") && sender.hasPermission("GlobalPVP.set")){
            loc = p.getLocation();
            sender.sendMessage("Точка установлена");
            return true;
        }

        if(loc != null){
            p.teleport(loc);
        } else {
            p.sendMessage("Точка не установлена");
        }

        return true;
    }

    private Location loadLocation(String s){
        if(s == null)
            return null;

        String spl[] = s.split(":");
        return new Location(Bukkit.getWorld(spl[0]), Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), Integer.parseInt(spl[3]));
    }

    private String saveLocation(Location l){
        return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }
}
