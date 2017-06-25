/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.crafting;

import com.sixteencolorgames.supertechprocessing.machines.mechanicalAssembler.TileEntityMechanicalAssembler;
import static com.sixteencolorgames.supertechprocessing.machines.mechanicalAssembler.TileEntityMechanicalAssembler.*;
import com.sixteencolorgames.supertechtweaks.crafting.RecipeIngredient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.item.ItemStack;

/**
 *
 * @author oa10712
 */
public class MechanicalAssemblerRecipe {

    RecipeIngredient wire;
    RecipeIngredient circuit;
    RecipeIngredient base;
    List<RecipeIngredient> misc = new ArrayList();
    private final ItemStack output;
    private int power = 0;

    public MechanicalAssemblerRecipe(ItemStack output, RecipeIngredient wire, RecipeIngredient circuit, RecipeIngredient base, RecipeIngredient... other) {
        this.output = output;
        this.wire = wire;
        power += wire.getExamples().get(0).stackSize * 150;
        this.circuit = circuit;
        power += circuit.getExamples().get(0).stackSize * 400;

        this.base = base;
        power += base.getExamples().get(0).stackSize * 800;
        misc.addAll(Arrays.asList(other));
    }

    public boolean matches(ItemStack[] check) {
        if (check.length < 4) {
            return false;
        }
        if (!wire.matches(check[TileEntityMechanicalAssembler.WIRE_SLOT])) {
            return false;
        }
        if (!circuit.matches(check[TileEntityMechanicalAssembler.CIRCUIT_SLOT])) {
            return false;
        }
        if (!base.matches(check[TileEntityMechanicalAssembler.BASE_SLOT])) {
            return false;
        }
        for (RecipeIngredient ing : misc) {
            boolean validIngredient = false;
            for (int i = 3; i < check.length; i++) {
                if (ing.matches(check[i])) {
                    validIngredient = true;
                    break;
                }
            }
            if (!validIngredient) {
                return false;
            }
        }
        return true;
    }

    int getPower() {
        return power;
    }

    public List<RecipeIngredient> getInputList() {
        List<RecipeIngredient> ret = new ArrayList();
        ret.add(wire);
        ret.add(circuit);
        ret.add(base);
        ret.addAll(misc);
        return ret;
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    ItemStack[] processStacks(ItemStack[] itemStacks) {
        if (itemStacks[WIRE_SLOT] != null) {
            itemStacks[WIRE_SLOT].stackSize -= wire.getMatch(itemStacks[WIRE_SLOT]).stackSize;
        }
        if (itemStacks[CIRCUIT_SLOT] != null) {
            itemStacks[CIRCUIT_SLOT].stackSize -= circuit.getMatch(itemStacks[CIRCUIT_SLOT]).stackSize;
        }
        if (itemStacks[BASE_SLOT] != null) {
            itemStacks[BASE_SLOT].stackSize -= base.getMatch(itemStacks[BASE_SLOT]).stackSize;
        }
        for (RecipeIngredient ing : misc) {
            for (int i = 3; i < itemStacks.length; i++) {
                ItemStack match = ing.getMatch(itemStacks[i]);
                if (match != null) {
                    itemStacks[i].stackSize -= match.stackSize;
                    break;
                }
            }
        }
        return itemStacks;
    }
}
