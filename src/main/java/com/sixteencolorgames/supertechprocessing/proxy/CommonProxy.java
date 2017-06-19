/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.proxy;

import com.sixteencolorgames.supertechprocessing.ModBlocks;
import com.sixteencolorgames.supertechprocessing.SuperTechProcessingMod;
import com.sixteencolorgames.supertechprocessing.compat.MineTweaker;
import com.sixteencolorgames.supertechprocessing.crafting.ExtruderManager;
import com.sixteencolorgames.supertechprocessing.network.GUIHandler;
import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import java.io.File;
import net.minecraft.item.ItemStack;
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
            MineTweaker.init();
        }
        Material.materials.forEach((ore) -> {
            ExtruderManager.instance().addExtrusion(new ItemStack(ModItems.itemMaterialObject, 2, ore.ordinal() + ROD), new ItemStack(ModItems.itemMaterialObject, 2, ore.ordinal() + WIRE), 200);
            ExtruderManager.instance().addExtrusion(new ItemStack(ModItems.itemMaterialObject, 2, ore.ordinal() + INGOT), new ItemStack(ModItems.itemMaterialObject, 2, ore.ordinal() + ROD), 200);
        });
    }

    private static void initDefault(File configFolder) {
    }
}
