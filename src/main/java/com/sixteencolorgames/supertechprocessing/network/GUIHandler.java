/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.network;

import com.sixteencolorgames.supertechprocessing.machines.extruderCoal.ContainerCoalExtruder;
import com.sixteencolorgames.supertechprocessing.machines.extruderElectric.ContainerElectricExtruder;
import com.sixteencolorgames.supertechprocessing.machines.extruderCoal.GuiCoalExtruder;
import com.sixteencolorgames.supertechprocessing.machines.extruderElectric.GuiElectricExtruder;
import com.sixteencolorgames.supertechprocessing.machines.extruderCoal.TileEntityCoalExtruder;
import com.sixteencolorgames.supertechprocessing.machines.extruderElectric.TileEntityElectricExtruder;
import com.sixteencolorgames.supertechprocessing.machines.mechanicalAssembler.ContainerMechanicalAssembler;
import com.sixteencolorgames.supertechprocessing.machines.mechanicalAssembler.GuiMechanicalAssembler;
import com.sixteencolorgames.supertechprocessing.machines.mechanicalAssembler.TileEntityMechanicalAssembler;
import com.sixteencolorgames.supertechprocessing.machines.rollerElectric.ContainerElectricRoller;
import com.sixteencolorgames.supertechprocessing.machines.rollerElectric.GuiElectricRoller;
import com.sixteencolorgames.supertechprocessing.machines.rollerElectric.TileEntityElectricRoller;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 *
 * @author oa10712
 */
public class GUIHandler implements IGuiHandler {

    public static final int EXTRUDER_GUI = 0;
    public static final int EXTRUDER_ELECTRIC_GUI = 1;
    public static final int ROLLER_ELECTRIC_GUI = 2;
    public static final int MECHANICAL_ASSEMBLER_GUI = 3;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == EXTRUDER_GUI) {
            TileEntityCoalExtruder teCG = (TileEntityCoalExtruder) world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerCoalExtruder(player.inventory, (TileEntityCoalExtruder) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == EXTRUDER_ELECTRIC_GUI) {
            TileEntityElectricExtruder teCG = (TileEntityElectricExtruder) world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerElectricExtruder(player.inventory, (TileEntityElectricExtruder) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == ROLLER_ELECTRIC_GUI) {
            TileEntityElectricRoller teCG = (TileEntityElectricRoller) world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerElectricRoller(player.inventory, (TileEntityElectricRoller) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == MECHANICAL_ASSEMBLER_GUI) {
            TileEntityMechanicalAssembler teCG = (TileEntityMechanicalAssembler) world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerMechanicalAssembler(player.inventory, (TileEntityMechanicalAssembler) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == EXTRUDER_GUI) {
            TileEntityCoalExtruder teCG = (TileEntityCoalExtruder) world.getTileEntity(new BlockPos(x, y, z));
            return new GuiCoalExtruder(player.inventory, (TileEntityCoalExtruder) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == EXTRUDER_ELECTRIC_GUI) {
            TileEntityElectricExtruder teCG = (TileEntityElectricExtruder) world.getTileEntity(new BlockPos(x, y, z));
            return new GuiElectricExtruder(player.inventory, (TileEntityElectricExtruder) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == ROLLER_ELECTRIC_GUI) {
            TileEntityElectricRoller teCG = (TileEntityElectricRoller) world.getTileEntity(new BlockPos(x, y, z));
            return new GuiElectricRoller(player.inventory, (TileEntityElectricRoller) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == MECHANICAL_ASSEMBLER_GUI) {
            TileEntityMechanicalAssembler teCG = (TileEntityMechanicalAssembler) world.getTileEntity(new BlockPos(x, y, z));
            return new GuiMechanicalAssembler(player.inventory, (TileEntityMechanicalAssembler) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
