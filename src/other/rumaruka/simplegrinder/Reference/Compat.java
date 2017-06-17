package com.rumaruka.simplegrinder.Reference;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Compat {
	  public static class JEI
	    {
	        public static final String JEI_CATEGORY_COALGRINDER = Reference.MODID + ":coalgrinder";
	   

	        public static final Item THAUMCRAFT_GOGGLES = ForgeRegistries.ITEMS.getValue(new ResourceLocation("Thaumcraft", "goggles"));
	    }
}
