/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.jei.roller;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 *
 * @author oa10712
 */
public class RollerRecipe extends BlankRecipeWrapper {

    @Nonnull
    private final List<List<ItemStack>> input;
    @Nonnull
    private final List<ItemStack> outputs;

    public RollerRecipe(@Nonnull List<ItemStack> input, @Nonnull ItemStack output) {
        this.input = Collections.singletonList(input);
        this.outputs = Collections.singletonList(output);
    }

    @Nonnull
    @Override
    public List<List<ItemStack>> getInputs() {
        return input;
    }

    @Nonnull
    @Override
    public List<ItemStack> getOutputs() {
        return outputs;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, input);
        ingredients.setOutputs(ItemStack.class, outputs);
    }
}
