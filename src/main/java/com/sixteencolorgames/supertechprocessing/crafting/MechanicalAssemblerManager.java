/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.crafting;

import com.sixteencolorgames.supertechtweaks.crafting.RecipeIngredient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;

/**
 *
 * @author oa10712
 */
public class MechanicalAssemblerManager {

    //HashMap<List<RecipeIngredient>, ItemStack> assemblies = new HashMap();
    List<MechanicalAssemblerRecipe> recipes = new ArrayList();
    private static final MechanicalAssemblerManager INSTANCE = new MechanicalAssemblerManager();

    public static MechanicalAssemblerManager getInstance() {
        return INSTANCE;
    }

    public List<MechanicalAssemblerRecipe> getRecipies() {
        return recipes;
    }

    public ItemStack getResult(ItemStack[] in) {
        for (MechanicalAssemblerRecipe rec : recipes) {
            if (rec.matches(in)) {
                return rec.getOutput();
            }
        }
        return null;
    }

    public int getEnergy(ItemStack[] itemStacks) {
        int time = 0;
        List<RecipeIngredient> list = null;
        for (MechanicalAssemblerRecipe rec : recipes) {
            if (rec.matches(itemStacks)) {
                return rec.getPower();
            }
        }
        return time;
    }

    public boolean addAssembly(MechanicalAssemblerRecipe add) {
        recipes.add(add);
        return true;
    }

    public ItemStack[] getProcessed(ItemStack[] itemStacks) {
        for (MechanicalAssemblerRecipe rec : recipes) {
            if (rec.matches(itemStacks)) {
                return rec.processStacks(itemStacks);
            }
        }
        return itemStacks;
    }
}
