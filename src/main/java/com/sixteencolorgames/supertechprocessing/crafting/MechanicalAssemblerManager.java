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

    HashMap<List<RecipeIngredient>, ItemStack> assemblies = new HashMap();
    private static final MechanicalAssemblerManager INSTANCE = new MechanicalAssemblerManager();

    public static MechanicalAssemblerManager getInstance() {
        return INSTANCE;
    }

    public Map<List<RecipeIngredient>, ItemStack> getRecipies() {
        return assemblies;
    }

    public ItemStack getResult(ItemStack[] in) {
        for (Map.Entry<List<RecipeIngredient>, ItemStack> entry : assemblies.entrySet()) {
            boolean validRecipe = true;
            for (RecipeIngredient ing : entry.getKey()) {
                boolean validIngredient = false;
                for (ItemStack stack : in) {
                    if (ing.matches(stack)) {
                        validIngredient = true;
                        break;
                    }
                }
                if (!validIngredient) {
                    validRecipe = false;
                    break;
                }
            }
            if (validRecipe) {
                return entry.getValue();
            }
        }
        return null;
    }

    public int getTime(ItemStack stack) {
        int time = 100;
        return time;
    }

    public boolean addAssembly(List<RecipeIngredient> in, ItemStack out) {
        assemblies.put(in, out);
        return true;
    }

    public boolean addAssembly(ItemStack out, Object... in) {
        List<RecipeIngredient> inputs = new ArrayList();
        for (Object o : in) {
            if (o instanceof RecipeIngredient) {
                inputs.add((RecipeIngredient) o);
                continue;
            }
            if (o instanceof String) {
                inputs.add(new RecipeIngredient((String) o));
                continue;
            }
        }
        return addAssembly(inputs, out);
    }

    public ItemStack[] getProcessed(ItemStack[] itemStacks) {
        List<RecipeIngredient> list = null;
        for (Map.Entry<List<RecipeIngredient>, ItemStack> entry : assemblies.entrySet()) {
            boolean validRecipe = true;
            for (RecipeIngredient ing : entry.getKey()) {
                boolean validIngredient = false;
                for (ItemStack stack : itemStacks) {
                    if (ing.matches(stack)) {
                        validIngredient = true;
                        break;
                    }
                }
                if (!validIngredient) {
                    validRecipe = false;
                    break;
                }
            }
            if (validRecipe) {
                list = entry.getKey();
            }
        }

        //we found the correct recipe, now process it
        if (list != null) {
            for (RecipeIngredient ing : list) {//for each ingredient
                for (int i = 0; i < itemStacks.length; i++) {//loop through the supplied inputs
                    if (ing.matches(itemStacks[i])) {//if the input is a match for the ingredient
                        itemStacks[i].stackSize -= ing.getQuantity();
                        break;
                    }
                }
            }
        }

        return itemStacks;
    }
}
