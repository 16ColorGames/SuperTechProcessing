/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.CraftTweaker;

import static com.blamejared.mtlib.helpers.InputHelper.toStack;
import com.sixteencolorgames.supertechprocessing.crafting.ExtruderManager;
import com.sixteencolorgames.supertechprocessing.crafting.RollerManager;
import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.compat.crafttweaker.CraftTweaker;
import com.sixteencolorgames.supertechtweaks.compat.crafttweaker.IMaterialListener;
import com.sixteencolorgames.supertechtweaks.crafting.RecipeIngredient;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.FOIL;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.PLATE;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.ROD;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.WIRE;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IngredientStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author oa10712
 */
@ZenClass("mods.supertech.processing")
public class MineTweaker implements IMaterialListener {

    public static final MineTweaker INSTANCE = new MineTweaker();

    public void init() {
        MineTweakerAPI.registerClass(this.getClass());
        CraftTweaker.listeners.add(new MineTweaker());
    }

    @ZenMethod
    public static void addExtrusion(IItemStack output, IIngredient ingredient, int time) {
        if (ingredient.getAmount() < 0) {
            MineTweakerAPI.logWarning("invalid ingredient: " + ingredient + " - stack size not known");
        } else {
            MineTweakerAPI.apply(new AddExtrusion(toStack(output), toRecipeIngredient(ingredient), time));
        }
    }

    @ZenMethod
    public static void addRolling(IItemStack output, IIngredient ingredient, int time) {
        if (ingredient.getAmount() < 0) {
            MineTweakerAPI.logWarning("invalid ingredient: " + ingredient + " - stack size not known");
        } else {
            MineTweakerAPI.apply(new AddRolling(toStack(output), toRecipeIngredient(ingredient), time));
        }
    }

    @ZenMethod
    public static void addAssembly(IItemStack output, IIngredient wire, IIngredient circuit, IIngredient base, IIngredient... other) {
        RecipeIngredient[] misc = new RecipeIngredient[other.length];
        for (int i = 0; i < other.length; i++) {
            RecipeIngredient toRecipeIngredient = toRecipeIngredient(other[i]);
            misc[i] = toRecipeIngredient;
        }
        AddAssembly action = new AddAssembly(
                toStack(output),
                toRecipeIngredient(wire),
                toRecipeIngredient(circuit),
                toRecipeIngredient(base),
                misc);
        MineTweakerAPI.apply(action);
    }

    @Override
    public void addMaterial(Material mtrl) {
        ExtruderManager.instance().addExtrusion("rod" + mtrl.getName(), new ItemStack(ModRegistry.itemMaterialObject, 1, mtrl.ordinal() + WIRE), 200);
        ExtruderManager.instance().addExtrusion("ingot" + mtrl.getName(), new ItemStack(ModRegistry.itemMaterialObject, 2, mtrl.ordinal() + ROD), 200);
        RollerManager.instance().addRolling("ingot" + mtrl.getName(), new ItemStack(ModRegistry.itemMaterialObject, 1, mtrl.ordinal() + PLATE), 200);
        RollerManager.instance().addRolling("plate" + mtrl.getName(), new ItemStack(ModRegistry.itemMaterialObject, 2, mtrl.ordinal() + FOIL), 200);
    }

    @Override
    public void removeMaterial(String string) {
        //TODO handle this
    }

    public static RecipeIngredient toRecipeIngredient(IIngredient iStack) {
       
        if (iStack == null) {
            return new RecipeIngredient();
        }
        if ((iStack instanceof IOreDictEntry)) {
            return new RecipeIngredient(((IOreDictEntry) iStack).getName(), ((IOreDictEntry) iStack).getAmount());
        }
        if ((iStack instanceof IItemStack)) {
            return new RecipeIngredient(toStack((IItemStack) iStack), ((IItemStack) iStack).getAmount());
        }
        if ((iStack instanceof IngredientStack)) {
            List<ItemStack> stacks = new ArrayList();
            stacks.addAll(Arrays.asList(CraftTweaker.getItemStacks(((IngredientStack) iStack).getItems())));
            return new RecipeIngredient(stacks, ((IngredientStack) iStack).getAmount());
        }
        return new RecipeIngredient();
    }
}
