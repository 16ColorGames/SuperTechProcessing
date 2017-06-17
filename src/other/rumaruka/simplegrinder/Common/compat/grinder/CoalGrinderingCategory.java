package com.rumaruka.simplegrinder.Common.compat.grinder;

import javax.annotation.Nonnull;

import com.rumaruka.simplegrinder.Common.compat.SimpleGrinderRecipeUID;
import com.rumaruka.simplegrinder.Reference.Reference;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class CoalGrinderingCategory extends GrinderingRecipeCategory<GrinderingRecipe> {
	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;
	public CoalGrinderingCategory(IGuiHelper guiHelper) {
		super(guiHelper);
	
		ResourceLocation location = new ResourceLocation(Reference.MODID, "textures/gui/container/grindGui.png");
		background = guiHelper.createDrawable(location, 55, 16, 82, 54);
		localizedName = Translator.translateToLocal("gui.jei.category.grinder");
	}

	@Override
	public String getUid() {
		// TODO Auto-generated method stub
		return SimpleGrinderRecipeUID.GRINDER_UID;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return localizedName;
	}
	@Override
	@Nonnull
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawAnimations(@Nonnull Minecraft minecraft) {
		flame.draw(minecraft, 2, 20);
		arrow.draw(minecraft, 24, 18);
	}


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, GrinderingRecipe recipeWrapper) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 0, 0);
		guiItemStacks.init(outputSlot, false, 60, 18);

		guiItemStacks.setFromRecipe(inputSlot, recipeWrapper.getInputs());
		guiItemStacks.setFromRecipe(outputSlot, recipeWrapper.getOutputs());
		
	}

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, GrinderingRecipe recipeWrapper, IIngredients ingredients) {
        
    }

}
