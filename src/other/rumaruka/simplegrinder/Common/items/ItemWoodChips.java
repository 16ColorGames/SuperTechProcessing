package com.rumaruka.simplegrinder.Common.items;

import com.rumaruka.simplegrinder.Common.tileentity.TileEntityCoalGrinder;
import com.rumaruka.simplegrinder.Init.ItemsCore;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWoodChips extends Item implements IFuelHandler{

	 public ItemWoodChips() {
		super();
		GameRegistry.registerFuelHandler(this);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	
	@Override
	public int getBurnTime(ItemStack fuel) {
		// TODO Auto-generated method stub
		return fuel.getItem() == ItemsCore.wood_chips? TileEntityCoalGrinder.getItemBurnTime(new ItemStack(ItemsCore.wood_chips)):0;
	}

}
