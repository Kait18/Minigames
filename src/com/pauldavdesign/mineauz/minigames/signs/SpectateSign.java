package com.pauldavdesign.mineauz.minigames.signs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

import com.pauldavdesign.mineauz.minigames.MinigamePlayer;
import com.pauldavdesign.mineauz.minigames.MinigameUtils;
import com.pauldavdesign.mineauz.minigames.Minigames;
import com.pauldavdesign.mineauz.minigames.minigame.Minigame;

public class SpectateSign implements MinigameSign {
	
	private Minigames plugin = Minigames.plugin;

	@Override
	public String getName() {
		return "Spectate";
	}

	@Override
	public String getCreatePermission() {
		return "minigame.sign.create.spectate";
	}

	@Override
	public String getCreatePermissionMessage() {
		return MinigameUtils.getLang("sign.spectate.createPermission");
	}

	@Override
	public String getUsePermission() {
		return "minigame.sign.use.spectate";
	}

	@Override
	public String getUsePermissionMessage() {
		return MinigameUtils.getLang("sign.spectate.usePermission");
	}

	@Override
	public boolean signCreate(SignChangeEvent event) {
		if(plugin.mdata.hasMinigame(event.getLine(2))){
			event.setLine(1, ChatColor.GREEN + "Spectate");
			event.setLine(2, plugin.mdata.getMinigame(event.getLine(2)).getName());
			return true;
		}
		event.getPlayer().sendMessage(ChatColor.RED + MinigameUtils.formStr("minigame.error.noMinigameName", event.getLine(2)));
		return false;
	}

	@Override
	public boolean signUse(Sign sign, MinigamePlayer player) {
		if(player.getPlayer().getItemInHand().getType() == Material.AIR && !player.isInMinigame()){
			Minigame mgm = plugin.mdata.getMinigame(sign.getLine(2));
			if(mgm != null){
				if(mgm.isEnabled()){
					plugin.pdata.spectateMinigame(player, mgm);
					return true;
				}
				else if(!mgm.isEnabled()){
					player.sendMessage(ChatColor.AQUA + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("minigame.error.notEnabled"));
				}
			}
			else if(mgm == null){
				player.sendMessage(ChatColor.RED + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("minigame.error.noMinigame"));
			}
		}
		else if(!player.isInMinigame())
			player.sendMessage(ChatColor.AQUA + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("sign.emptyHand"));
		return false;
	}

}
