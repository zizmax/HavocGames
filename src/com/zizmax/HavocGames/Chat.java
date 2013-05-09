package com.zizmax.HavocGames;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.zizmax.HavocGames.HavocGames;

public class Chat {
	static String timeMsg = "";
	static String tipsMsg = "";
	static String infoMsg = "";
	static String chatMsg = "";
	static String playerMsg = "";
	static ArrayList<String> tips = new ArrayList<String>();
    //List<String> tiplist = new List<String>()//BGFiles.config.getStringList("tips"); // Add config getter!
    //for (String tip : tiplist){
    //  tips.add(tip);
    //}

    public static void sayInfoMsg(String text){
        Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "HG" + ChatColor.GRAY + "] " + text);
    }
    
    public static void sayPlayerMsg(Player player, String text){
    	String pre = ChatColor.GRAY + "[" + ChatColor.GOLD + "HG" + ChatColor.GRAY + "] ";
    	player.sendMessage(pre + text);
    }
    public static void sayStaffMsg(Player player, String text){
    	String pre = ChatColor.GRAY + "[" + ChatColor.DARK_RED + "STAFF" + ChatColor.GRAY + "] ";
    	// Add for loop to go through all staff here!
    	player.sendMessage(pre + text);
    }
    
    public static void sayTimeMsg(int secs){
    	HavocGames main = new HavocGames();
        String pre = ChatColor.GRAY + "[" + ChatColor.GOLD + "HG" + ChatColor.GRAY + "] ";
    	if(secs == 1){
    		Bukkit.broadcastMessage(pre + ChatColor.GREEN + "The " + ChatColor.GOLD + "HavocGames" + ChatColor.GREEN + " starts in " + ChatColor.WHITE + "1" + ChatColor.GREEN + " minute!");
    	}
    	if(secs == 30){
    		Bukkit.broadcastMessage(pre + ChatColor.GREEN + "The " + ChatColor.GOLD + "HavocGames" + ChatColor.GREEN + " starts in " + ChatColor.WHITE + "30" + ChatColor.GREEN + " seconds!");
    	}
    	if(secs == 60){
    		if(Bukkit.getOnlinePlayers().length < 1){
    			Bukkit.broadcastMessage(pre + ChatColor.RED + "There are not enough players to start the game!");
    		    main.setTime(0);
    		}
    		else{
    		main.startGame();
    		main.setGame(true);
    		}
    	}
    	if(secs == 120){
    		Bukkit.broadcastMessage(pre + ChatColor.GREEN + "Killing everybody and breaking obisidian begins in " + ChatColor.WHITE + "2" + ChatColor.GREEN + " minutes!");
    	}
    	if(secs == 180){
    		Bukkit.broadcastMessage(pre + ChatColor.GREEN + "Killing everybody and breaking obisidian begins in " + ChatColor.WHITE + "1" + ChatColor.GREEN + " minute!");
    	}
    	if(secs == 210){
    		Bukkit.broadcastMessage(pre + ChatColor.GREEN + "Killing everybody and breaking obisidian begins in " + ChatColor.WHITE + "30" + ChatColor.GREEN + " seconds!");
    	}
    	if(secs == 240){
    		Bukkit.broadcastMessage(pre + ChatColor.GREEN + "Killing everybody and breaking obisidian begins " + ChatColor.WHITE + "NOW" + ChatColor.GREEN + "!");
    	}
    	if(secs > 1000 && !main.getGame()){
    		  // Because getGame() isn't working, this doesn't work.....
			  Bukkit.broadcastMessage(pre + "Game has officially ended, setting everyone up for game end....");
        }
        
    }

}


