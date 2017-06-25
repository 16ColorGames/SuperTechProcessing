/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.machines.mechanicalAssembler;

import com.sixteencolorgames.supertechprocessing.crafting.MechanicalAssemblerManager;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityPoweredBase;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;

/**
 *
 * @author oa10712
 */
public class TileEntityMechanicalAssembler extends TileEntityPoweredBase {
    
    public static final int WIRE_SLOT = 0, CIRCUIT_SLOT = 1, BASE_SLOT = 2, OUTPUT_SLOT = 10;
    private static final int[] SLOTS_TOP = new int[]{WIRE_SLOT};
    private static final int[] SLOTS_BOTTOM = new int[]{OUTPUT_SLOT};
    private static final int[] SLOTS_SIDES = new int[]{OUTPUT_SLOT};
    private int cookEnergy;
    private int totalCookEnergy;
    
    public TileEntityMechanicalAssembler() {
        super("mechanical_assembler", 40, 32000);
        itemStacks = new ItemStack[11];
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be
     * crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        boolean flag = stack != null && stack.isItemEqual(itemStacks[index])
                && ItemStack.areItemStackTagsEqual(stack, itemStacks[index]);
        itemStacks[index] = stack;
        
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
        
        if (index == 0 && !flag) {
            this.totalCookEnergy = this.getTotalCookEnergy(stack);
            this.cookEnergy = 0;
            this.markDirty();
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        itemStacks = new ItemStack[this.getSizeInventory()];
        
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");
            
            if (j >= 0 && j < itemStacks.length) {
                itemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }
        
        this.cookEnergy = compound.getInteger("CookEnergy");
        this.totalCookEnergy = compound.getInteger("CookEnergyTotal");
        
        if (compound.hasKey("CustomName", 8)) {
            this.customName = compound.getString("CustomName");
        }
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("CookEnergy", this.cookEnergy);
        compound.setInteger("CookEnergyTotal", this.totalCookEnergy);
        NBTTagList nbttaglist = new NBTTagList();
        
        for (int i = 0; i < itemStacks.length; ++i) {
            if (itemStacks[i] != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                itemStacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        
        compound.setTag("Items", nbttaglist);
        
        if (this.hasCustomName()) {
            compound.setString("CustomName", this.customName);
        }
        
        return compound;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        if (canProcess()) {//If we can do a process tick
            if (!worldObj.isRemote) {
                if (cookEnergy == 0) {//If this is the start of processing
                    totalCookEnergy = getTotalCookEnergy(itemStacks[WIRE_SLOT]);//setup the total energy required
                }
                int consumedEnergy = consumeEnergy(this.energyRate);//Consume some energy
                cookEnergy += consumedEnergy;//Mark the increase in progress
                if (cookEnergy >= totalCookEnergy) {//If we are done with this item
                    cookEnergy = 0;
                    processItem();
                    markDirty();
                }
            }
        } else {
            if (itemStacks[WIRE_SLOT] == null) {//If the input is empty
                cookEnergy = 0;//Clear out any progress that had been made
            }
        }
    }

    /*Time cooking 
	 * Int - time
     */
    public int getTotalCookEnergy(@Nullable ItemStack stack) {
        return MechanicalAssemblerManager.getInstance().getEnergy(itemStacks);
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item,
     * destination stack isn't full, etc.
     */
    private boolean canProcess() {
        if (this.getEnergyStored() <= 0) {//if there is no energy left
            return false;
        }
        ItemStack itemstack = MechanicalAssemblerManager.getInstance().getResult(itemStacks);
        if (itemstack == null) {//if there is no valid recipe
            return false;
        }
        if (itemStacks[OUTPUT_SLOT] == null) {
            return true;
        }
        if (!itemStacks[OUTPUT_SLOT].isItemEqual(itemstack)) {//if the output has a different output
            return false;
        }
        int result = itemStacks[OUTPUT_SLOT].stackSize + itemstack.stackSize;//new stack size
        return result <= getInventoryStackLimit() && result <= itemStacks[OUTPUT_SLOT].getMaxStackSize();//Finally, return if the new stack is small enough to fit

    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted
     * item in the furnace result stack
     */
    public void processItem() {
        ItemStack itemstack = MechanicalAssemblerManager.getInstance().getResult(itemStacks);
        if (itemStacks[OUTPUT_SLOT] == null) {
            itemStacks[OUTPUT_SLOT] = itemstack.copy();
        } else if (itemStacks[OUTPUT_SLOT].getItem() == itemstack.getItem()) {
            itemStacks[OUTPUT_SLOT].stackSize += itemstack.stackSize; // Forge
        }
        itemStacks = MechanicalAssemblerManager.getInstance().getProcessed(itemStacks);
        for (int i = 0; i < itemStacks.length; i++) {
            if (itemStacks[i] != null && itemStacks[i].stackSize == 0) {
                itemStacks[i] = null;
            }
        }
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring
     * stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        }
        return false;
    }
    
    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);
    }

    /**
     * Returns true if automation can extract the given item in the given slot
     * from the given side.
     */
    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == 1) {
            Item item = stack.getItem();
            
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:furnace";
    }
    
    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerMechanicalAssembler(playerInventory, this);
    }
    
    public final static int ENERGY = 0, MAX_ENERGY = 1, COOK_TIME = 2, TOTAL_COOK_TIME = 3;
    
    @Override
    public int getField(int id) {
        switch (id) {
            case ENERGY:
                return this.getEnergyStored();
            case MAX_ENERGY:
                return this.getMaxEnergyStored();
            case COOK_TIME:
                return this.cookEnergy;
            case TOTAL_COOK_TIME:
                return this.totalCookEnergy;
            default:
                return 0;
        }
    }
    
    @Override
    public void setField(int id, int value) {
        switch (id) {
            case ENERGY:
                this.setEnergy(value);
                break;
            case MAX_ENERGY:
                break;
            case COOK_TIME:
                this.cookEnergy = value;
                break;
            case TOTAL_COOK_TIME:
                this.totalCookEnergy = value;
        }
    }
    
    @Override
    public int getFieldCount() {
        return 4;
    }
    
    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this,
            net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this,
            net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this,
            net.minecraft.util.EnumFacing.WEST);
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability,
            net.minecraft.util.EnumFacing facing) {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            switch (facing) {
                case DOWN:
                    return (T) handlerBottom;
                case UP:
                    return (T) handlerTop;
                default:
                    return (T) handlerSide;
            }
        }
        return super.getCapability(capability, facing);
    }
    
}
