/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.jei.extruder;

import com.sixteencolorgames.supertechprocessing.ModBlocks;
import com.sixteencolorgames.supertechprocessing.SuperTechProcessingMod;
import com.sixteencolorgames.supertechprocessing.compat.jei.UIDs;
import com.sixteencolorgames.supertechprocessing.machines.extruderCoal.GuiCoalExtruder;
import com.sixteencolorgames.supertechprocessing.machines.extruderElectric.GuiElectricExtruder;
import javax.annotation.Nonnull;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author oa10712
 */
public class ExtruderRecipeCategory extends BlankRecipeCategory {

    protected static final int inputSlot = 0;
    protected static final int outputSlot = 1;

    public static void init(IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new ExtruderRecipeCategory(guiHelper));
        registry.addRecipeHandlers(new ExtruderRecipeHandler());

        registry.addRecipes(ExtruderRecipeMaker.getRecipes(jeiHelpers));
        registry.addRecipeClickArea(GuiElectricExtruder.class, 80, 38, 20, 12, UIDs.ELECTRIC_EXTRUDER);
        registry.addRecipeClickArea(GuiCoalExtruder.class, 80, 38, 20, 12, UIDs.ELECTRIC_EXTRUDER);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.extruder), UIDs.ELECTRIC_EXTRUDER);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.extruderElectric), UIDs.ELECTRIC_EXTRUDER);
    }

    protected final ResourceLocation backgroundLocation;
    @Nonnull
    protected final IDrawableAnimated arrow;

    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;

    public ExtruderRecipeCategory(IGuiHelper guiHelper) {
        backgroundLocation = new ResourceLocation(SuperTechProcessingMod.MODID, "textures/gui/extruderElectricGui.png");

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(backgroundLocation, 176, 14, 24, 17);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);

        background = guiHelper.createDrawable(backgroundLocation, 55, 16, 82, 54);
        localizedName = Translator.translateToLocal("gui.jei.category.extruder");
    }

    @Override
    public String getUid() {
        // TODO Auto-generated method stub
        return UIDs.ELECTRIC_EXTRUDER;
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
        arrow.draw(minecraft, 24, 18);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(inputSlot, true, 0, 18);
        guiItemStacks.init(outputSlot, false, 60, 18);

        guiItemStacks.setFromRecipe(inputSlot, recipeWrapper.getInputs());
        guiItemStacks.setFromRecipe(outputSlot, recipeWrapper.getOutputs());
    }
}
