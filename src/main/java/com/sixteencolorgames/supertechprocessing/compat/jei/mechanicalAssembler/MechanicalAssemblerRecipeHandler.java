/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.jei.mechanicalAssembler;

import com.sixteencolorgames.supertechprocessing.compat.jei.UIDs;
import javax.annotation.Nonnull;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.ErrorUtil;
import mezz.jei.util.Log;

/**
 *
 * @author oa10712
 */
public class MechanicalAssemblerRecipeHandler implements IRecipeHandler<MechanicalAssemblerRecipe> {

    @Override
    public Class<MechanicalAssemblerRecipe> getRecipeClass() {
        // TODO Auto-generated method stub
        return MechanicalAssemblerRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        // TODO Auto-generated method stub
        return UIDs.MECHANICAL_ASSEMBLER;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull MechanicalAssemblerRecipe recipe) {
        return UIDs.MECHANICAL_ASSEMBLER;
    }

    @Override
    @Nonnull
    public IRecipeWrapper getRecipeWrapper(@Nonnull MechanicalAssemblerRecipe recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull MechanicalAssemblerRecipe recipe) {
        if (recipe.getInputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromRecipe(recipe, this);
            Log.error("Recipe has no inputs. {}", recipeInfo);
        }
        if (recipe.getOutputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromRecipe(recipe, this);
            Log.error("Recipe has no outputs. {}", recipeInfo);
        }
        return true;
    }

}
