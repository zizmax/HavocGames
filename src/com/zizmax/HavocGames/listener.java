package com.zizmax.HavocGames;

import com.zizmax.HavocGames.HavocGames;
import com.zizmax.HavocGames.MySQL;
import com.zizmax.HavocGames.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;


public class listener implements Listener{
	HavocGames main = new HavocGames();
	String pre = main.pre;
	
	
	@EventHandler
	public void playerLogin(PlayerJoinEvent event){
		String name = event.getPlayer().getName();
		Player player = event.getPlayer();
	    Chat.sayPlayerMsg(player, "Welcome to " + ChatColor.GOLD + "HavocGames" + ChatColor.GRAY + ", " + ChatColor.AQUA + name + ChatColor.GRAY + "!");
	    Chat.sayPlayerMsg(player, "Website: " + ChatColor.DARK_AQUA +"zizmax.com" + ChatColor.GRAY + " <--- Click This");
	    Chat.sayPlayerMsg(player, "Have fun and good luck!");
	    player.sendMessage(pre + "Game will begin in " + ChatColor.WHITE + main.getTime() + ChatColor.GRAY + " seconds!");
	    //MySQL.write(name, 27,0,0,0,0);
		
		// The line below is supposed to check if the player is an owner, but because the value isn't actually accessed it can't.
		if(HavocGames.owners.contains(player.getName().toString())){
			event.setJoinMessage(pre + ChatColor.DARK_RED + player.getName() + ChatColor.YELLOW + " the [" + ChatColor.DARK_RED + "Owner" + ChatColor.YELLOW + "] has joined the game!");
		}
		if(HavocGames.sradmins.contains(player.getName().toString())){
			event.setJoinMessage(pre + ChatColor.DARK_GRAY + player.getName() + ChatColor.YELLOW + " the [" + ChatColor.DARK_RED + "Sr" + ChatColor.DARK_GRAY + "Admin" + ChatColor.YELLOW + "] has joined the game!");
		}
		if(HavocGames.helpers.contains(player.getName().toString())){
			event.setJoinMessage(pre + ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " the [" + ChatColor.GREEN + "Helper" + ChatColor.YELLOW + "] has joined the game!");
		}

			else{
				event.setJoinMessage(pre + ChatColor.YELLOW + "Some normal idiot joined the game!");
			}
		
		Location loc = player.getLocation();
		loc.setX(126); loc.setY(60); loc.setZ(62);
		player.teleport(loc);
		player.setAllowFlight(true);	
	}
	
	
	// TODO For some reason, and I can't really see why, the coins do not drop when killed by a player's bow.
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		if ((event.getEntity() instanceof Player)) {
	    Player player = (Player)event.getEntity();
	    Bukkit.dispatchCommand(player, "spawn");
	    player.getWorld().setSpawnLocation(0, 21, 0);
        player.setFlying(true);
	    EntityDamageEvent deathCause = player.getLastDamageCause();
	    ItemStack itemstack = new ItemStack(Material.GOLD_INGOT, 1);
	    Location loc = player.getLocation();
		int i = 0;
		Location newloc = loc;
		newloc.setY(loc.getY() + .5);
		loc.getWorld().playEffect(newloc, Effect.ENDER_SIGNAL, 20);
		loc.getWorld().playEffect(newloc, Effect.MOBSPAWNER_FLAMES, 20);		
	    if (deathCause.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || deathCause.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
	      Entity entity = ((EntityDamageByEntityEvent)deathCause).getDamager();
	      if ((entity instanceof Player)) {
	        Player playerKiller = event.getEntity().getKiller();
	        playerKiller.sendMessage(pre + "You got " + "VALUE" + " points!");
	        //HavocGames method = new HavocGames();
	        //method.addScore(playerKiller);
	        while (i < 20){
				player.getWorld().dropItemNaturally(loc, itemstack);
				i++;
			}
	        playerKiller.sendMessage(pre + "Pick up the " + ChatColor.GOLD + "gold" + ChatColor.GRAY + " for coins!");
	      }
	    }
		}
	}
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
    	Player player = event.getPlayer();
	    if(event.getMessage().equals("/plugins") || event.getMessage().equals("/pl")){
	        player.sendMessage(pre + "-- Plugins --");
	    	player.sendMessage(pre + "    - " + ChatColor.GREEN + "HavocGames");
	    	player.sendMessage(pre + "    - " + ChatColor.GREEN + "HavocSpawn");
	    	player.sendMessage(pre + "    - " + ChatColor.GREEN + "HavocDatabase");
	    	player.sendMessage(pre + "-----------");
	    	event.setCancelled(true);
        if(event.getMessage().equals("/help")){
		    player.sendMessage(pre + "-- Help --");
		    player.sendMessage(pre + "    - " + ChatColor.GREEN + "Do this!");
		    player.sendMessage(pre + "    - " + ChatColor.GREEN + "Do that!");
		    player.sendMessage(pre + "    - " + ChatColor.GREEN + "Commands:");
		    player.sendMessage(pre + "-----------");
		    	event.setCancelled(true);
		    }

	    }
	    	
    }
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event){
		Player player = event.getPlayer();
		Item item = event.getItem();
		
		if(item.getItemStack().getType().toString().equals("GOLD_INGOT")){
			if(item.getItemStack().getAmount() == 1){
				player.sendMessage(pre + "You got " + ChatColor.DARK_PURPLE + "1" + ChatColor.GRAY + " coin!");
				event.setCancelled(true);
				item.remove();
			}
			else{
			player.sendMessage(pre + "You got " + ChatColor.DARK_PURPLE + item.getItemStack().getAmount() + ChatColor.GRAY +  " coins!");
			event.setCancelled(true);
			item.remove();
			}
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		
		// TODO Make this look for title and score and rank
		//MySQL.getPoints(event.getPlayer().getName())
		event.setFormat(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "SCORE" + ChatColor.GRAY + "] [" + ChatColor.DARK_GRAY + "TITLE" + ChatColor.GRAY + "]" + "%s: %s");
	}
	@EventHandler
	public void onServerPing(ServerListPingEvent event){
		event.setMaxPlayers(10);
		if(!main.getGame()){
			
			event.setMotd(ChatColor.GREEN + "Game starting in " + ChatColor.WHITE + (60 - main.getTime()) + ChatColor.GREEN + " seconds!");
		}
		else{
			event.setMotd(ChatColor.GREEN + "Game in progress. Login to spectate!");
		}
	}
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event){
		Block block = event.getBlock();
		Player player = event.getPlayer();
		ItemStack item = event.getItemInHand();
		ItemStack itemstack = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		if(block.getTypeId() == 49){
			if(item.getTypeId() == (itemstack.getTypeId())){
				player.sendMessage(pre + ChatColor.RED + "Break the obsidian to win!");
			}
			    else{
				    player.sendMessage(pre + ChatColor.RED + "You need a diamond pick to break the obsidian!");
				    player.sendMessage(pre + ChatColor.RED + "You can get one with " + ChatColor.AQUA + "/pick");
			    }
		}
		if(item.getTypeId() == (itemstack.getTypeId()) && block.getTypeId() != 49){
		    player.sendMessage(pre + ChatColor.RED + "Nice try! You can " + ChatColor.DARK_RED + "ONLY" + ChatColor.RED + " break obsidian with the pickaxe!");
			player.setItemInHand(null);
			player.sendMessage(pre + ChatColor.RED + "You can get another pickaxe with " + ChatColor.AQUA + "/pick");
		    	
		}
			
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Block block = event.getBlock();
		Player player = event.getPlayer();
		Location loc = new Location(Bukkit.getWorld("world"), 225,  12, 62);
		Location loc2 = new Location(Bukkit.getWorld("world"), 25,  12, 62);
		loc2.setZ(-21);
		if(block.getLocation().equals(loc)){
			player.sendMessage(pre + "You just won!");
			// TODO Check for team, no breaking your own! Unfortunately, teams can't be viewed here.
			Bukkit.broadcastMessage(pre + ChatColor.GREEN + player.getName() + ChatColor.GRAY +  " just broke the obsidian! Game over!");
			main.setTime(1900);
		}
		if(block.getLocation().equals(loc2)){
			player.sendMessage(pre + "You just won!");
			Bukkit.broadcastMessage(pre + ChatColor.RED + player.getName() + ChatColor.GRAY + " just broke the obsidian! Game over!");
			main.setTime(1900);
		}
		
	}
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		int lives = 1;
		if(lives == 0){
			loc.setX(0);
			loc.setY(21);
			loc.setZ(0);
			event.setRespawnLocation(loc);
			player.sendMessage(pre + "You have lost all of your lives! Game over for you.");
		}
		else{
			Bukkit.dispatchCommand(player, "spawn");
			player.sendMessage(pre + "You have " + ChatColor.WHITE + lives + ChatColor.GRAY + " lives left!");
		}
		
	}
	

}
