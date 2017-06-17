package com.rumaruka.simplegrinder.Init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingCore {
	
	
	public static void CraftingCoreInit(){
		
		
		ItemStack outPut;
		
		outPut = new ItemStack(BlocksCore.machine_core);
		GameRegistry.addRecipe(outPut, new Object[]{
				"SSS",
				"RPR",
				"SSS", 'S', Blocks.STONE, 'R', Items.REDSTONE, 'P', Blocks.PISTON
		});
		ItemStack outPUT;
		outPUT = new ItemStack(BlocksCore.coal_grinder);
		GameRegistry.addRecipe(outPUT, new Object[]{
				"SIS",
				"SMS",
				"IPI", 'S', Blocks.STONE, 'I', Items.IRON_INGOT, 'P', Blocks.PISTON, 'M', BlocksCore.machine_core
		});
		
		ItemStack output;
		output = new ItemStack(ItemsCore.i_fuel);
		GameRegistry.addRecipe(output, new Object[]{
				"EIE",
				"GCG",
				"EIE", 'I',Items.DIAMOND, 'G', Items.GOLD_INGOT,  'C', new ItemStack(Items.COAL), 'E', ItemsCore.wood_chips
		});
		
		
	}

}
