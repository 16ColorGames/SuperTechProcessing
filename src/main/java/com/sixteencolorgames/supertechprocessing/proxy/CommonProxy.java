/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.proxy;

import com.sixteencolorgames.supertechprocessing.ModBlocks;
import com.sixteencolorgames.supertechprocessing.Recipes;
import com.sixteencolorgames.supertechprocessing.SuperTechProcessingMod;
import com.sixteencolorgames.supertechprocessing.compat.CraftTweaker.MineTweaker;
import com.sixteencolorgames.supertechprocessing.network.GUIHandler;
import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import java.io.File;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 *
 * @author oa10712
 */
public class CommonProxy {

    public static Configuration config;

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(SuperTechProcessingMod.instance, new GUIHandler());
    }

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.init();
        PacketHandler.registerMessages(SuperTechProcessingMod.MODID + "Channel");
        File configFolder = new File(event.getModConfigurationDirectory().toString() + "/supertechprocessing/");
        config = new Configuration(new File(configFolder.getPath(), "config.cfg"));
        //Config.readConfig();

    }

    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("MineTweaker3")) {
            MineTweaker.INSTANCE.init();
        }
        Recipes.init();
    }

    private static void initDefault(File configFolder) {
    }

    public void registerItemRenderer(Item item, int meta, String id) {

    }

}
