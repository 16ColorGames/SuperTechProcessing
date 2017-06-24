/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing;

import com.sixteencolorgames.supertechprocessing.blocks.BlockContainerBase;
import com.sixteencolorgames.supertechprocessing.machines.extruderCoal.BlockCoalExtruder;
import com.sixteencolorgames.supertechprocessing.machines.extruderElectric.BlockElectricExtruder;
import com.sixteencolorgames.supertechprocessing.machines.extruderCoal.TileEntityCoalExtruder;
import com.sixteencolorgames.supertechprocessing.machines.extruderElectric.TileEntityElectricExtruder;
import com.sixteencolorgames.supertechprocessing.machines.mechanicalAssembler.BlockMechanicalAssembler;
import com.sixteencolorgames.supertechprocessing.machines.mechanicalAssembler.TileEntityMechanicalAssembler;
import com.sixteencolorgames.supertechprocessing.machines.rollerElectric.BlockElectricRoller;
import com.sixteencolorgames.supertechprocessing.machines.rollerElectric.TileEntityElectricRoller;
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
    public static Block rollerElectric;
    public static Block mechanicalAssembler;

    public static void init() {
        extruder = register(new BlockCoalExtruder(false));
        GameRegistry.registerTileEntity(TileEntityCoalExtruder.class, SuperTechProcessingMod.MODID + ".te_extruder");

        extruderElectric = register(new BlockElectricExtruder());
        GameRegistry.registerTileEntity(TileEntityElectricExtruder.class, SuperTechProcessingMod.MODID + ".te_extruder_electric");

        rollerElectric = register(new BlockElectricRoller());
        GameRegistry.registerTileEntity(TileEntityElectricRoller.class, SuperTechProcessingMod.MODID + ".te_roller_electric");
        mechanicalAssembler = register(new BlockMechanicalAssembler());
        GameRegistry.registerTileEntity(TileEntityMechanicalAssembler.class, SuperTechProcessingMod.MODID + ".te_mechanical_assembler");
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
            if (block instanceof BlockContainerBase) {
                ((BlockContainerBase) block).setItemBlock(itemBlock);
            }
            // if (block instanceof ItemOreDict) {
            // ((ItemOreDict)block).initOreDict();
            // } else if (itemBlock instanceof ItemOreDict) {
            // ((ItemOreDict)itemBlock).initOreDict();
            // }
        }

        return block;
    }

    /**
     * Registers a block and associated requirements
     *
     * @param <T>
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
