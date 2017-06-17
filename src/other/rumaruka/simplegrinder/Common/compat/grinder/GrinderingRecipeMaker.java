package com.rumaruka.simplegrinder.Common.compat.grinder;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.rumaruka.simplegrinder.Init.GrinderRecipes;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

public class GrinderingRecipeMaker {
	@Nonnull
	public static List<GrinderingRecipe> getFurnaceRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
		GrinderRecipes furnaceRecipes = GrinderRecipes.instance();
		Map<ItemStack, ItemStack> smeltingMap = furnaceRecipes.getSmeltingList();

		List<GrinderingRecipe> recipes = new ArrayList<GrinderingRecipe>();

		for (Map.Entry<ItemStack, ItemStack> itemStackItemStackEntry : smeltingMap.entrySet()) {
			ItemStack input = itemStackItemStackEntry.getKey();
			ItemStack output = itemStackItemStackEntry.getValue();

			float experience = furnaceRecipes.getSmeltingExperience(output);

			List<ItemStack> inputs = stackHelper.getSubtypes(input);
			GrinderingRecipe recipe = new GrinderingRecipe(inputs, output, experience);
			recipes.add(recipe);
		}

		return recipes;
	}

}
