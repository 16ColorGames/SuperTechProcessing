package com.sixteencolorgames.supertechprocessing;

import com.sixteencolorgames.supertechprocessing.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SuperTechProcessingMod.MODID, name = SuperTechProcessingMod.MODNAME, version = SuperTechProcessingMod.VERSION, acceptedMinecraftVersions = "[1.10.2]")
public class SuperTechProcessingMod {

    public static final String MODID = "supertechprocessing";
    public static final String MODNAME = "Super Tech Processing";
    public static final String VERSION = "1.0.1";
    @Mod.Instance(MODID)
    public static SuperTechProcessingMod instance;

    /**
     * The proxy to be used. Holds various functions and objects that may need
     * to be different based on side.
     */
    @SidedProxy(clientSide = "com.sixteencolorgames.supertechprocessing.proxy.ClientProxy", serverSide = "com.sixteencolorgames.supertechprocessing.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println(MODNAME + " is loading!");
        SuperTechProcessingMod.proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        SuperTechProcessingMod.proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        SuperTechProcessingMod.proxy.postInit(event);
    }

}
