/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.jei.extruder;

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
public class ExtruderRecipeHandler implements IRecipeHandler<ExtruderJEIRecipe> {

    @Override
    public Class<ExtruderJEIRecipe> getRecipeClass() {
        // TODO Auto-generated method stub
        return ExtruderJEIRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        // TODO Auto-generated method stub
        return UIDs.ELECTRIC_EXTRUDER;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull ExtruderJEIRecipe recipe) {
        return UIDs.ELECTRIC_EXTRUDER;
    }

    @Override
    @Nonnull
    public IRecipeWrapper getRecipeWrapper(@Nonnull ExtruderJEIRecipe recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull ExtruderJEIRecipe recipe) {
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
