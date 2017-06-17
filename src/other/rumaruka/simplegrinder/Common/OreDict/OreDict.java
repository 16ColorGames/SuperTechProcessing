package com.rumaruka.simplegrinder.Common.OreDict;

import com.rumaruka.simplegrinder.Init.BlocksCore;
import com.rumaruka.simplegrinder.Init.ItemsCore;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDict {
	
	
	

	public static void registerOre(){
		OreDictionary.registerOre("dustIron", new ItemStack(ItemsCore.dust_iron));
		OreDictionary.registerOre("dustGold", new ItemStack(ItemsCore.dust_gold));
	}
	
	


}