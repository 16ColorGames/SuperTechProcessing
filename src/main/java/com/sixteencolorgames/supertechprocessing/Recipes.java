/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing;

import com.sixteencolorgames.supertechprocessing.crafting.ExtruderManager;
import com.sixteencolorgames.supertechprocessing.crafting.MechanicalAssemblerManager;
import com.sixteencolorgames.supertechprocessing.crafting.MechanicalAssemblerRecipe;
import com.sixteencolorgames.supertechprocessing.crafting.RollerManager;
import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.crafting.RecipeIngredient;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 *
 * @author oa10712
 */
public class Recipes {

    public static void init() {
        Material.materials.forEach((ore) -> {
            ExtruderManager.instance().addExtrusion("rod" + ore.getName(), new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + WIRE), 200);
            ExtruderManager.instance().addExtrusion("ingot" + ore.getName(), new ItemStack(ModRegistry.itemMaterialObject, 2, ore.ordinal() + ROD), 200);
            RollerManager.instance().addRolling("ingot" + ore.getName(), new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + PLATE), 200);
            RollerManager.instance().addRolling("plate" + ore.getName(), new ItemStack(ModRegistry.itemMaterialObject, 2, ore.ordinal() + FOIL), 200);
        });

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mechanicalAssembler),
                "zxz", "xyx", "zxz", 'x', "barsIron", 'y', "casingBasic", 'z', "workbench"));//Craft an assembler

        MechanicalAssemblerRecipe rec = new MechanicalAssemblerRecipe(
                new ItemStack(ModBlocks.rollerElectric),
                new RecipeIngredient("wireCopper", 5),
                new RecipeIngredient("circuitBasic"),
                new RecipeIngredient("casingBasic"),
                new RecipeIngredient(new ItemStack(Blocks.PISTON, 4, 0)));
        MechanicalAssemblerManager.getInstance().addAssembly(rec);
        
        rec = new MechanicalAssemblerRecipe(
                new ItemStack(ModBlocks.extruderElectric),
                new RecipeIngredient("wireCopper", 5),
                new RecipeIngredient("circuitBasic"),
                new RecipeIngredient("casingBasic"),
                new RecipeIngredient("heatingElement",2),
                new RecipeIngredient(new ItemStack(Blocks.PISTON, 2, 0)));
        MechanicalAssemblerManager.getInstance().addAssembly(rec);
    }
}
