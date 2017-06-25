/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.jei.mechanicalAssembler;

import static com.sixteencolorgames.supertechprocessing.compat.jei.mechanicalAssembler.MechanicalAssemblerRecipeCategory.outputSlot;
import com.sixteencolorgames.supertechtweaks.crafting.RecipeIngredient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

/**
 *
 * @author oa10712
 */
public class MechanicalAssemblerJEIRecipe extends BlankRecipeWrapper {

    @Nonnull
    private final List<List<ItemStack>> input;
    @Nonnull
    private final List<ItemStack> outputs;
    private final List<ItemStack> wire;
    private final List<ItemStack> circuit;
    private final List<ItemStack> base;

    public MechanicalAssemblerJEIRecipe(@Nonnull List<RecipeIngredient> input, @Nonnull ItemStack output) {
        this.input = new ArrayList();
        wire = input.remove(0).getExamples();
        circuit = input.remove(0).getExamples();
        base = input.remove(0).getExamples();
        input.forEach((ri) -> {
            this.input.add(ri.getExamples());
        });

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
        //ingredients.setInputLists(ItemStack.class, input);
        ingredients.setOutputs(ItemStack.class, outputs);
    }

    void setupStacks(IGuiItemStackGroup guiItemStacks) {
        if (wire.get(0).getItem() != null) {
            guiItemStacks.setFromRecipe(0, wire);
        }
        if (circuit.get(0).getItem() != null) {
            guiItemStacks.setFromRecipe(1, circuit);
        }
        if (base.get(0).getItem() != null) {
            guiItemStacks.setFromRecipe(2, base);
        }
        for (int i = 0; i < input.size(); i++) {
            guiItemStacks.setFromRecipe(i + 3, input.get(i));
        }
        guiItemStacks.set(outputSlot, getOutputs());
    }
}
