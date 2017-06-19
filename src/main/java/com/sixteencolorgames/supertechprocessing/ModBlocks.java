/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing;

import com.sixteencolorgames.supertechprocessing.blocks.BlockCoalExtruder;
import com.sixteencolorgames.supertechprocessing.blocks.BlockElectricExtruder;
import com.sixteencolorgames.supertechprocessing.tileentities.TileEntityCoalExtruder;
import com.sixteencolorgames.supertechprocessing.tileentities.TileEntityElectricExtruder;
import com.sixteencolorgames.supertechtweaks.blocks.BlockBase;
import com.sixteencolorgames.supertechtweaks.blocks.BlockTileEntity;
import com.sixteencolorgames.supertechtweaks.items.ItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author oa10712
 */
public class ModBlocks {

    public static Block extruder;
    public static Block extruderElectric;

    public static void init() {
        extruder = register(new BlockCoalExtruder(false));
        GameRegistry.registerTileEntity(TileEntityCoalExtruder.class, SuperTechProcessingMod.MODID + ".te_extruder");

        extruderElectric = register(new BlockElectricExtruder());
        GameRegistry.registerTileEntity(TileEntityElectricExtruder.class, SuperTechProcessingMod.MODID + ".te_extruder_electric");
    }

    /**
     * Registers a block and associated requirements, such as model and tile
     * entity
     *
     * @param block The block to register
     * @param itemBlock the item block to register
     * @return the block registered
     */
    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        if (itemBlock != null) {
            GameRegistry.register(itemBlock);

            if (block instanceof ItemModelProvider) {
                ((ItemModelProvider) block).registerItemModel(itemBlock);
            }
            if (block instanceof BlockBase) {
                ((BlockBase) block).setItemBlock(itemBlock);
            }
            // if (block instanceof ItemOreDict) {
            // ((ItemOreDict)block).initOreDict();
            // } else if (itemBlock instanceof ItemOreDict) {
            // ((ItemOreDict)itemBlock).initOreDict();
            // }
        }

        if (block instanceof BlockTileEntity) {
            GameRegistry.registerTileEntity(((BlockTileEntity<?>) block).getTileEntityClass(),
                    block.getRegistryName().toString());
        }

        return block;
    }

    /**
     * Registers a block and associated requirements
     *
     * @param block the block to register
     * @return the block registered
     */
    public static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
    }
}
