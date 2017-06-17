package com.rumaruka.simplegrinder.Common.compat.grinder;

import javax.annotation.Nonnull;

import com.rumaruka.simplegrinder.Common.compat.SimpleGrinderRecipeUID;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.plugins.vanilla.furnace.SmeltingRecipe;
import mezz.jei.util.ErrorUtil;
import mezz.jei.util.Log;

public class GrinderingRecipeHandler implements IRecipeHandler<GrinderingRecipe> {

	@Override
	public Class<GrinderingRecipe> getRecipeClass() {
		// TODO Auto-generated method stub
		return GrinderingRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		// TODO Auto-generated method stub
		return SimpleGrinderRecipeUID.GRINDER_UID;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull GrinderingRecipe recipe) {
		return SimpleGrinderRecipeUID.GRINDER_UID;
	}

	@Override
	@Nonnull
	public IRecipeWrapper getRecipeWrapper(@Nonnull GrinderingRecipe recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull GrinderingRecipe recipe) {
		if (recipe.getInputs().isEmpty()) {
			String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
			Log.error("Recipe has no inputs. {}", recipeInfo);
		}
		if (recipe.getOutputs().isEmpty()) {
			String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
			Log.error("Recipe has no outputs. {}", recipeInfo);
		}
		return true;
	}

}
