package com.rumaruka.simplegrinder.Common.items;

import java.util.List;

import com.rumaruka.simplegrinder.Init.BlocksCore;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ItemCoalGrinder extends ItemBlock {

	public ItemCoalGrinder(Block block) {
		super(BlocksCore.coal_grinder);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(TextFormatting.GRAY + "Vanilla crasher ores!");
	}
}
