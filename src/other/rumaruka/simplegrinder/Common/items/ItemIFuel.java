package com.rumaruka.simplegrinder.Common.items;

import java.util.List;

import com.rumaruka.simplegrinder.Common.tileentity.TileEntityCoalGrinder;
import com.rumaruka.simplegrinder.Init.ItemsCore;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCoal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemIFuel extends Item implements IFuelHandler {

	public ItemIFuel() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.MATERIALS);
		GameRegistry.registerFuelHandler(this);
	}
	
	@Override
	/*
	 *New fuel for grinder and furnace*
	*/
	public int getBurnTime(ItemStack fuel) {
		return fuel.getItem() == ItemsCore.i_fuel? TileEntityCoalGrinder.getItemBurnTime(new ItemStack(ItemsCore.i_fuel)):0;
				
	}
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(TextFormatting.GOLD+"This joke fule");
	}



}
