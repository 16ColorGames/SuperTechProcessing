package com.rumaruka.simplegrinder.Init;

import com.rumaruka.simplegrinder.Core.GuiCore;
import com.rumaruka.simplegrinder.Core.simplygrinderCore;
import com.rumaruka.simplegrinder.Event.ConnectionEventSG;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EventRegister {
	
	
	public static void RegisetHandlers(){
		NetworkRegistry.INSTANCE.registerGuiHandler(simplygrinderCore.instance, new GuiCore());
		FMLCommonHandler.instance().bus().register(new ConnectionEventSG());
		
	}
}
