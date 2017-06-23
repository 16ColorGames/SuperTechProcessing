package com.sixteencolorgames.supertechprocessing.proxy;

import com.sixteencolorgames.supertechprocessing.SuperTechProcessingMod;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Clientside only functions
 *
 * @author oa10712
 *
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        List<ItemStack> subItems = new ArrayList();
        item.getSubItems(item, CreativeTabs.MISC, subItems);
        subItems.forEach((item2) -> {
            ModelLoader.setCustomModelResourceLocation(item, item2.getMetadata(),
                    new ModelResourceLocation(SuperTechProcessingMod.MODID + ":" + id, "inventory"));
        });
    }
}
