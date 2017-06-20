/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing;

import com.sixteencolorgames.supertechprocessing.crafting.ExtruderManager;
import com.sixteencolorgames.supertechprocessing.crafting.RollerManager;
import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.FOIL;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.PLATE;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.ROD;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.WIRE;
import net.minecraft.item.ItemStack;

/**
 *
 * @author oa10712
 */
public class Recipes {

    public static void init() {
        Material.materials.forEach((ore) -> {
            ExtruderManager.instance().addExtrusion("rod" + ore.getName(), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + WIRE), 200);
            ExtruderManager.instance().addExtrusion("ingot" + ore.getName(), new ItemStack(ModItems.itemMaterialObject, 2, ore.ordinal() + ROD), 200);
            RollerManager.instance().addRolling("ingot" + ore.getName(), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + PLATE), 200);
            RollerManager.instance().addRolling("plate" + ore.getName(), new ItemStack(ModItems.itemMaterialObject, 2, ore.ordinal() + FOIL), 200);
        });
    }

}
