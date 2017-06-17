package com.rumaruka.simplegrinder.Event;

import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import scala.swing.TextComponent;

public class ConnectionEventSG {

	private static String url = "http://minecraft.curseforge.com/projects/simple-grinder/files";

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

		event.player.addChatComponentMessage(
				new TextComponentString(TextFormatting.DARK_PURPLE + "Thank you for dowload my mod"));

		event.player.addChatComponentMessage(new TextComponentString(TextFormatting.DARK_RED
				+"Check update "+TextFormatting.WHITE+url));

	}

}
