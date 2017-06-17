package com.rumaruka.simplegrinder.Init;

import com.rumaruka.simplegrinder.Client.gui.creativetabs.SimpleGrinderCreativeTabs;
import com.rumaruka.simplegrinder.Common.blocks.BlockCoalGrinder;
import com.rumaruka.simplegrinder.Common.blocks.BlockMachineCore;
import com.rumaruka.simplegrinder.Common.items.ItemCoalGrinder;
import com.rumaruka.simplegrinder.Common.items.ItemMachineCore;
import com.rumaruka.simplegrinder.Reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksCore {
	
	//Main 
	public static Block coal_grinder;
	public static Block lit_grinder;
	//For crafting
	public static Block machine_core;
	
	
	public static void init(){
		
		coal_grinder = new BlockCoalGrinder(false).setUnlocalizedName("coal_grinder").setCreativeTab(SimpleGrinderCreativeTabs.TABS_SIMPLE_GRINDER);
		lit_grinder = new BlockCoalGrinder(true).setUnlocalizedName("lit_grinder").setLightLevel(3.5F);
		machine_core = new BlockMachineCore().setUnlocalizedName("machine_core").setCreativeTab(SimpleGrinderCreativeTabs.TABS_SIMPLE_GRINDER);
	}
	public static void InGameRegister(){
		GameRegistry.registerBlock(coal_grinder, ItemCoalGrinder.class ,coal_grinder.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(lit_grinder,lit_grinder.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(machine_core, ItemMachineCore.class,machine_core.getUnlocalizedName().substring(5));
	}
	public static void Render(){
		registerRender(machine_core);
		registerRender(coal_grinder);
		registerRender(lit_grinder);
		
	}
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}

	
	