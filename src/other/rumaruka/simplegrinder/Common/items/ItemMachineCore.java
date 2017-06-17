package com.rumaruka.simplegrinder.Common.items;

import java.util.List;

import com.rumaruka.simplegrinder.Init.BlocksCore;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemMachineCore extends ItemBlock {

	public ItemMachineCore(Block block) {
		super(BlocksCore.machine_core);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add("For crafting Coal Grinder");
	}

}
