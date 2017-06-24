/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.jei.mechanicalAssembler;

import com.sixteencolorgames.supertechprocessing.ModBlocks;
import com.sixteencolorgames.supertechprocessing.SuperTechProcessingMod;
import com.sixteencolorgames.supertechprocessing.compat.jei.UIDs;
import com.sixteencolorgames.supertechprocessing.machines.mechanicalAssembler.GuiMechanicalAssembler;
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
public class MechanicalAssemblerRecipeCategory extends BlankRecipeCategory {
    
    protected static final int inputSlot = 0;
    protected static final int outputSlot = 11;
    
    public static void init(IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new MechanicalAssemblerRecipeCategory(guiHelper));
        registry.addRecipeHandlers(new MechanicalAssemblerRecipeHandler());
        
        registry.addRecipes(MechanicalAssemblerRecipeMaker.getRecipes(jeiHelpers));
        registry.addRecipeClickArea(GuiMechanicalAssembler.class, 145, 60, 20, 12, UIDs.MECHANICAL_ASSEMBLER);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.mechanicalAssembler), UIDs.MECHANICAL_ASSEMBLER);
    }
    
    protected final ResourceLocation backgroundLocation;
    @Nonnull
    protected final IDrawableAnimated arrow;
    
    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;
    
    public MechanicalAssemblerRecipeCategory(IGuiHelper guiHelper) {
        backgroundLocation = new ResourceLocation(SuperTechProcessingMod.MODID, "textures/gui/mechanicalAssemblerGui.png");
        
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(backgroundLocation, 176, 14, 24, 17);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
        
        background = guiHelper.createDrawable(backgroundLocation, 27, 15, 142, 54);
        localizedName = Translator.translateToLocal("gui.jei.category.mechanicalAssembler");
    }
    
    @Override
    public String getUid() {
        // TODO Auto-generated method stub
        return UIDs.MECHANICAL_ASSEMBLER;
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
        arrow.draw(minecraft, 117, 44);
    }
    
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        
        guiItemStacks.init(0, true, 0, 0);
        guiItemStacks.init(1, true, 0, 18);
        guiItemStacks.init(2, true, 0, 36);
        guiItemStacks.init(3, true, 50, 0);
        guiItemStacks.init(4, true, 69, 0);
        guiItemStacks.init(4, true, 42, 18);
        guiItemStacks.init(4, true, 59, 18);
        guiItemStacks.init(4, true, 78, 17);
        guiItemStacks.init(4, true, 50, 35);
        guiItemStacks.init(4, true, 69, 35);
        guiItemStacks.init(outputSlot, false, 115, 14);
        
        for (int i = 0; i < 10; i++) {
            guiItemStacks.setFromRecipe(i, recipeWrapper.getInputs().get(i));
        }
        guiItemStacks.setFromRecipe(outputSlot, recipeWrapper.getOutputs());
    }
}
