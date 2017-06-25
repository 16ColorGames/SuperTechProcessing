/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.jei.mechanicalAssembler;

import com.sixteencolorgames.supertechprocessing.crafting.MechanicalAssemblerManager;
import com.sixteencolorgames.supertechprocessing.crafting.MechanicalAssemblerRecipe;
import com.sixteencolorgames.supertechtweaks.crafting.RecipeIngredient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

/**
 *
 * @author oa10712
 */
public class MechanicalAssemblerRecipeMaker {
    
    @Nonnull
    public static List<MechanicalAssemblerJEIRecipe> getRecipes(IJeiHelpers helpers) {
        IStackHelper stackHelper = helpers.getStackHelper();
        MechanicalAssemblerManager furnaceRecipes = MechanicalAssemblerManager.getInstance();
        List<MechanicalAssemblerRecipe> recipiesList = furnaceRecipes.getRecipies();
        
        List<MechanicalAssemblerJEIRecipe> recipes = new ArrayList();
        
        for (MechanicalAssemblerRecipe rec : recipiesList) {
            MechanicalAssemblerJEIRecipe newRec = new MechanicalAssemblerJEIRecipe(rec.getInputList(), rec.getOutput());
            recipes.add(newRec);
        }
        
        return recipes;
    }
    
}
