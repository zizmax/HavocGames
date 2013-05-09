
package com.zizmax.HavocGames;


import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import com.zizmax.HavocGames.MySQL;
import com.zizmax.HavocGames.Chat;


//TODO ****** Log in any-time, may not be able to play (TP's and abilities set correctly based on state of game) *******
// TODO Seperate command executor
// TODO Admin mode chat channel
// TODO Admin mode updates and stats
// TODO Staff perms (/broadcast, /start.....)
// TODO Selecting world, copying world, deleting world, repeat

// BUG Teams are not correctly made *** Not test yet, may be working now ***
// BUG Not dropping coins when killed by player bow
// BUG Not correctly checking for/assigning the staff names String list, or tips or perks
// BUG MySQL doesn't connect, etc....

public final class HavocGames extends JavaPlugin{
	// pre uses public to be accessed from other classes because its value never changes. I'm pretty sure the other stuff needs to use getters and setters.
    public String pre = ChatColor.GRAY + "[" + ChatColor.GOLD + "HG" + ChatColor.GRAY + "] "; // ALWAYS use in GUI strings!
    public boolean gameHasStarted = false; // A boolean showing if the game has started or not.
    public static int secs = 0; // Time, in seconds, that the game has lasted
    public static ArrayList<Player> players;
    public static ArrayList<Player> watchers;
   
    public static List<String> owners;
    public static List<String> helpers;
    public static List<String> srmods;
    public static List<String> mods;
    public static List<String> sradmins;
    
    public static List<String> green; 
    public static List<String> red;
    

    
	@Override
    public void onEnable(){
        //MySQL.connect();
		this.saveDefaultConfig();
		List<String>ownerslist = this.getConfig().getStringList("staff.owners");
		
		String[] owners = (String[]) ownerslist.toArray();
	    //for (String name : ownersnames){
		//      owners.add(name);
	    //}
		sradmins= this.getConfig().getStringList("staff.sradmins");
		helpers = this.getConfig().getStringList("staff.helpers");
		getLogger().info(owners.toString());
		getServer().getPluginManager().registerEvents(new listener(), this);
		getLogger().info("HavocGames successfully enabled!");
		scheduleAnnouncerTask(0L, 20L);
	}
    /*
	public List<String> getHelpers(){
		helpers = this.getConfig().getStringList("staff.helpers");
		return helpers;
	}
	public List<String> getSrAdmins(){
		sradmins = this.getConfig().getStringList("staff.sradmins");
		return sradmins;
	}
	public List<String> getMods(){
		mods = this.getConfig().getStringList("staff.sradmins");
		return mods;
	}
	public List<String> getSrMods(){
		srmods = this.getConfig().getStringList("staff.sradmins");
		return srmods;
	}
	*/
	public boolean getGame(){
		// Is supposed to be used to check what state the game is in, but also not able to be correctly used in listener.java
        return this.gameHasStarted;
	}
	public void setGame(boolean gameHasStarted){
		this.gameHasStarted = gameHasStarted;
	}
    @Override
    public void onDisable() {
    	getLogger().info("HavocGames successfully disabled!");

    }
    
    public BukkitTask scheduleAnnouncerTask(long initialWait, long repeatInterval){
    	return getServer().getScheduler().runTaskTimerAsynchronously(this, 
	    new Runnable(){
    		public void run() {
    			count();
    		}
    	} , initialWait, repeatInterval);
    }
    

    
    public int getTime(){
         // Again, this works for getting the time in this class, but not in others. Listener.java gets 0, its initial value.
        return secs;
    }
    
    
    public void setTime(int passedSecs){
         // I really can't tell what exactly is happening with this, in some cases it seems to work, in others, it doesn't...
         secs = passedSecs + 1;
    }
    
    public void count(){
        setTime(secs);
        Chat.sayTimeMsg(secs);
    }
    
	public void startGame(){
		Chat.sayInfoMsg("Initializing gameplay...");
		Chat.sayInfoMsg(ChatColor.WHITE + "May lag....");
		Location loc = new Location(Bukkit.getWorld("world"), 25,  12, 62 );
		World world = loc.getWorld();
		int i = 0;
		while(i != 2){
			Block blockToChange = world.getBlockAt(loc);
			blockToChange.setTypeId(49);
			loc.setX(225);
			i++;
		}
		loc.setX(126);
		loc.setY(60);
		loc.setZ(62);
		int playerAmount = Bukkit.getServer().getOnlinePlayers().length; // int of # of players
		int half = playerAmount / 2; // half of the # of players
		for (i = 0; i < Bukkit.getServer().getOnlinePlayers().length; i++){
	      Player temp = Bukkit.getPlayer(Bukkit.getServer().getOnlinePlayers()[i].getName());
	      temp.setGameMode(GameMode.SURVIVAL);
	      temp.setFoodLevel(20);
	      temp.setHealth(20);
	      temp.setFlying(false);
	      temp.teleport(loc);
	      PlayerInventory inv = temp.getInventory();
	      inv.clear();
	      inv.setBoots(new ItemStack(Material.AIR));
	      inv.setLeggings(new ItemStack(Material.AIR));
	      inv.setChestplate(new ItemStack(Material.AIR));
	      ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
	      LeatherArmorMeta lam = (LeatherArmorMeta)lhelmet.getItemMeta();
	      lam.setColor(Color.fromRGB(205, 175, 149));
	      lhelmet.setItemMeta(lam);
	      inv.setHelmet(lhelmet);
	      temp.setExp(-temp.getExp());
	      if(i < half){
	    	  temp.sendMessage(pre + "You are on team " + ChatColor.GREEN + "GREEN" + ChatColor.GRAY + "!");
	    	  green.add(temp.getName());
	      }
	      if(i >= half){
	    	  temp.sendMessage(pre + "You are on team " + ChatColor.RED + "RED" + ChatColor.GRAY + "!");
	    	  red.add(temp.getName());
	      }

	    }
		
	    Chat.sayInfoMsg("The game has started!");
	}
	

	
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) sender;
		String noperm = pre + "You do not have permission!";
    	if(cmd.getName().equalsIgnoreCase("buy")){
    		if(args.length == 0){
    			player.sendMessage(pre + ChatColor.YELLOW + "------ " + ChatColor.DARK_PURPLE + "Perks" + ChatColor.YELLOW + "------");
    			player.sendMessage(pre + ChatColor.WHITE + "    - " + ChatColor.BLUE + "Pick");
    			player.sendMessage(pre + ChatColor.WHITE + "    - " + ChatColor.RED + "Life");
    			player.sendMessage(pre + ChatColor.WHITE + "    - " + ChatColor.DARK_AQUA + "Axe");
    			player.sendMessage(pre + ChatColor.WHITE + "    - " + ChatColor.LIGHT_PURPLE + "Title");
    			player.sendMessage(pre + ChatColor.YELLOW + "-----------------");
    		}
    		else {
    			String arg = args[0];
    		    if (arg.equals("pick")){
        			player.sendMessage(pre + "You will get a " + ChatColor.GREEN + ChatColor.BOLD + arg + ChatColor.GRAY+ " when the game starts!");
    		    }
    		    if (arg.equals("life")){
    		    	player.sendMessage(pre + "You got an extra " + ChatColor.GREEN + ChatColor.BOLD + arg + ChatColor.GRAY + "!");
    		    }
    		}
    	    //player.sendMessage(pre + "You have the " + this.getConfig().getStringList("titles") + ChatColor.GRAY + " titles!");
    	    return true;
    	}
    	if(cmd.getName().equalsIgnoreCase("stats")){
    		if (args.length == 0){
    			Chat.sayPlayerMsg(player, "Your score is " + "VALUE" + "!");
                
                
    		}
    		    else{
    			    Player target = (Bukkit.getServer().getPlayer(args[0]));
    			    if (target == null){
    			    	Chat.sayPlayerMsg(player, ChatColor.GREEN + args[0] + ChatColor.GRAY + " is not online!");
                    }
                        else{
                        	Chat.sayPlayerMsg(player, args[0] + "'s score is " + "VALUE" + "!");
                        }
    		}
    	}
    	
    	if(cmd.getName().equalsIgnoreCase("coins")){
    		Chat.sayPlayerMsg(player, "Balance: " + "VALUE");
    	}
    	
    	if (cmd.getName().equalsIgnoreCase("broadcast")) {
    	      if (player.isOp()){
    	        if (args.length >= 1) {
    	          String bcast = "";
    	          for (int x = 0; x < args.length; x++) {
    	            bcast = bcast + args[x] + " ";
    	          }
    	          bcast = ChatColor.translateAlternateColorCodes('&', bcast);
    	          Chat.sayInfoMsg(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "ADMIN" + ChatColor.GRAY + "] " + bcast);
    	        }
    	        else
    	        {
    	        	Chat.sayPlayerMsg(player, "Must have arguments!");
    	        }
    	      }
    	      else sender.sendMessage(noperm);

    	      return true;
    	}
    	if(cmd.getName().equalsIgnoreCase("admin")){
    		if(player.isOp()){
    			player.setGameMode(GameMode.CREATIVE);
    			Chat.sayPlayerMsg(player, "You are now in " + ChatColor.AQUA + "admin " + ChatColor.GRAY +  "mode.");
    		}
    		else{
    			player.sendMessage(noperm);
    		}
    	}
    	if(cmd.getName().equalsIgnoreCase("play")){
    		if(player.isOp()){
    			player.setGameMode(GameMode.SURVIVAL);
    			Bukkit.dispatchCommand(player, "spawn");
    			Bukkit.dispatchCommand(player, "clear");
    			Chat.sayPlayerMsg(player, "You are now in " + ChatColor.AQUA + "player" + ChatColor.GRAY +  " mode.");	
    		}
    		else{
    			player.sendMessage(noperm);
    		}
    	}
    	if(cmd.getName().equalsIgnoreCase("spawn")){
    		
    		Location loc = player.getLocation();
    		loc.setX(126); loc.setY(60); loc.setZ(62); //Maybe a Location var should start these values to make spawning easier.
    		player.teleport(loc);
    	}
    	if(cmd.getName().equalsIgnoreCase("donate")){
    		if(args.length < 1){
    			Chat.sayPlayerMsg(player, ChatColor.YELLOW + "------- " + ChatColor.RED + "Donation Ranks" + ChatColor.YELLOW + " -------");
    		    Chat.sayPlayerMsg(player, ChatColor.WHITE + "    - " + ChatColor.AQUA + ChatColor.BOLD + "VIP");
    		    Chat.sayPlayerMsg(player, ChatColor.WHITE + "    - " + ChatColor.GREEN + ChatColor.BOLD + "VIP" + ChatColor.WHITE + "+");
    		    Chat.sayPlayerMsg(player, ChatColor.WHITE + "    - " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "KING");
    		    Chat.sayPlayerMsg(player, ChatColor.YELLOW + "----------------------------");
    		    Chat.sayPlayerMsg(player, ChatColor.BLUE + "Use /donate <rank> to learn more.");
    		    Chat.sayPlayerMsg(player, ChatColor.DARK_GRAY + "Donating helps keep the server online!");
    		}
    		else{
    		    if(args[0].equalsIgnoreCase("vip")){
    		    	Chat.sayPlayerMsg(player, ChatColor.YELLOW + "------- " + ChatColor.AQUA + "VIP" + ChatColor.YELLOW + " -------");
    		    	Chat.sayPlayerMsg(player, ChatColor.WHITE + "    - " + ChatColor.DARK_AQUA + "VALUE coins!");
    		    	Chat.sayPlayerMsg(player, ChatColor.WHITE + "    - " + ChatColor.DARK_AQUA + "Join full server!");
    		    	Chat.sayPlayerMsg(player, ChatColor.WHITE + "    - " + ChatColor.AQUA + "VIP" + ChatColor.DARK_AQUA + " title!");
    		    	Chat.sayPlayerMsg(player, ChatColor.WHITE + "    - " + ChatColor.AQUA + "Aqua chat color!");
    		    }
    		    else if(args[0].equalsIgnoreCase("vip+")){
    		    	Chat.sayPlayerMsg(player, "VIP+ Stuff");
    		    }	
    		    else if(args[0].equalsIgnoreCase("king")){
    		    	Chat.sayPlayerMsg(player, "King Stuff");
    		    }
    			else{
    				Chat.sayPlayerMsg(player, ChatColor.YELLOW + "------- " + ChatColor.RED + "Donation Ranks" + ChatColor.YELLOW + " -------");
    				Chat.sayPlayerMsg(player, ChatColor.WHITE + "    - " + ChatColor.AQUA + ChatColor.BOLD + "VIP");
        		    Chat.sayPlayerMsg(player, ChatColor.WHITE + "    - " + ChatColor.GREEN + ChatColor.BOLD + "VIP" + ChatColor.WHITE + "+");
        		    Chat.sayPlayerMsg(player, ChatColor.WHITE + "    - " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "KING");
        		    Chat.sayPlayerMsg(player, ChatColor.YELLOW + "----------------------------");
        		    Chat.sayPlayerMsg(player, ChatColor.BLUE + "Use /donate <rank> to learn more.");
        		    Chat.sayPlayerMsg(player, ChatColor.DARK_GRAY + "Donating helps keep the server online!");
    			}
    		}
    		
    	}
    	if(cmd.getName().equalsIgnoreCase("game")){
    		if(!getGame()){
    			Chat.sayPlayerMsg(player, "The game has not yet begun!");
    			Chat.sayPlayerMsg(player, "Game Length: " + getTime());
    		}
    		else{
    			Chat.sayPlayerMsg(player, "Game Length: " + getTime());
    			Chat.sayPlayerMsg(player, "Players Left: " + "VALUE");
    		}
    	}
    	/*
    	if(cmd.getName().equalsIgnoreCase("ally")){
    		if(args.length == 0){
    			player.sendMessage(pre + "Error! Use /ally <name>");
    		}
    		else{
    			Player target = (Bukkit.getServer().getPlayer(args[0]));
			    if (target == null){
                    player.sendMessage(pre + ChatColor.GREEN + args[0] + ChatColor.GRAY + " is not online!");
                }
                    else{
                    	if(target.getName() == player.getName()){
                    		player.sendMessage(pre + "You can't ally yourself silly!");
                    	}
                    	else{
                            //score = this.getConfig().getInt("players." + target.getName() + ".score");
            	            player.sendMessage(pre + "You just allied " + ChatColor.YELLOW + args[0] + ChatColor.GRAY + "!");
            	            target.sendMessage(pre + ChatColor.YELLOW + args[0] + ChatColor.GRAY + " just allied you!");
            	            target.sendMessage(pre + "Now ally " + ChatColor.YELLOW + args[0] + ChatColor.GRAY + " with " + ChatColor.BOLD + "/ally " + args[0]);
                    	}
                    }
    		}
    		
    	}
    	*/
    	if(cmd.getName().equalsIgnoreCase("ping")){
    		Chat.sayPlayerMsg(player, "Pong!");
    	}
    	
    	if(cmd.getName().equalsIgnoreCase("ref")){
    		if(args.length < 1){
    			Chat.sayPlayerMsg(player, "Use /ref <player name> to get gold!");
    		}
    		else{
    			Player target = (Bukkit.getServer().getPlayer(args[0]));
    			if (target == null){
    				Chat.sayPlayerMsg(player, args[0] + " is not online!");
    			}
    			else{
    				if(target.getName() == player.getName()){
    					Chat.sayPlayerMsg(player, "You can't refer yourself silly!");
                	}
    				else{
                        //score = this.getConfig().getInt("players." + target.getName() + ".score");
    					Chat.sayPlayerMsg(player, "You just referred " + ChatColor.YELLOW + args[0] + ChatColor.GRAY + " and got " + ChatColor.DARK_PURPLE + "VALUE" + ChatColor.GRAY + " coins!");
        	            Chat.sayPlayerMsg(target, ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " just referred you!");
        	            Chat.sayPlayerMsg(target, "You got " + ChatColor.DARK_PURPLE + "VALUE" + ChatColor.GRAY + " coins!");
        	            Chat.sayInfoMsg(ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " and " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " just used the referral system and got VALUE coins!");
        	            Chat.sayInfoMsg("Use " + ChatColor.DARK_GRAY + "/ref" + ChatColor.GRAY + " to do the same!");
        	            
                	}
    			}
    		}
    	}
    	if(cmd.getName().equalsIgnoreCase("pick")){
    		PlayerInventory inventory = player.getInventory();
    		ItemStack itemstack = new ItemStack(Material.DIAMOND_PICKAXE, 1);
    		if(inventory.contains(itemstack)){
    			Chat.sayPlayerMsg(player, "You can't get more than 1 pickaxe!");
    			
    		}
    		else{
    			ItemMeta itemmeta = itemstack.getItemMeta();
    			itemmeta.setDisplayName("¤cObsidian Destroyer");
    			itemstack.setItemMeta(itemmeta);
    			inventory.addItem(itemstack);
    			Chat.sayPlayerMsg(player, "There you go! Break the obsidian to win!");
    		}
    	}
    	if(cmd.getName().equalsIgnoreCase("spectate")){
    		Chat.sayPlayerMsg(player, "Not implemented!");
    	}
    	if(cmd.getName().equalsIgnoreCase("shout")){
    		Chat.sayPlayerMsg(player, "Not implemented!");
    	}
    	if(cmd.getName().equalsIgnoreCase("start")){
    		// Need to check for Owner here.
    		secs = 59;
    	}
    	return false; 
    }
}
