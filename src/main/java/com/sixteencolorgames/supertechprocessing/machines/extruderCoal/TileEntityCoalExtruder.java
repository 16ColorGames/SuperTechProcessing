package com.sixteencolorgames.supertechprocessing.machines.extruderCoal;

import com.sixteencolorgames.supertechprocessing.machines.extruderCoal.BlockCoalExtruder;
import com.sixteencolorgames.supertechprocessing.crafting.ExtruderManager;
import com.sixteencolorgames.supertechprocessing.machines.extruderCoal.ContainerCoalExtruder;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityBase;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import static net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCoalExtruder extends TileEntityBase {

    public static final int FUEL_SLOT = 1, INPUT_SLOT = 0, OUTPUT_SLOT = 2;
    private static final int[] SLOTS_TOP = new int[]{INPUT_SLOT};
    private static final int[] SLOTS_BOTTOM = new int[]{OUTPUT_SLOT, FUEL_SLOT};
    private static final int[] SLOTS_SIDES = new int[]{FUEL_SLOT};
    /**
     * The number of ticks that the furnace will keep burning
     */
    private int extrudeBurnTime;
    /**
     * The number of ticks that a fresh copy of the currently-burning item would
     * keep the furnace burning for
     */
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;

    public TileEntityCoalExtruder() {
        super("coal_extruder");
        itemStacks = new ItemStack[3];
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
            this.totalCookTime = this.getCookTime(stack);
            this.cookTime = 0;
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

        this.extrudeBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(itemStacks[FUEL_SLOT]);

        if (compound.hasKey("CustomName", 8)) {
            this.customName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", this.extrudeBurnTime);
        compound.setInteger("CookTime", this.cookTime);
        compound.setInteger("CookTimeTotal", this.totalCookTime);
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
     * Furnace isBurning
     */
    public boolean isBurning() {
        return this.extrudeBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.extrudeBurnTime;
        }
        if (!this.worldObj.isRemote) {
            if (this.isBurning() || itemStacks[FUEL_SLOT] != null && itemStacks[INPUT_SLOT] != null) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.currentItemBurnTime = this.extrudeBurnTime = getItemBurnTime(itemStacks[FUEL_SLOT]);
                    if (this.isBurning()) {
                        flag1 = true;

                        if (itemStacks[FUEL_SLOT] != null) {
                            --itemStacks[FUEL_SLOT].stackSize;

                            if (itemStacks[FUEL_SLOT].stackSize == 0) {
                                itemStacks[FUEL_SLOT] = itemStacks[FUEL_SLOT].getItem()
                                        .getContainerItem(itemStacks[FUEL_SLOT]);
                            }
                        }
                    }
                }
                if (this.isBurning() && this.canSmelt()) {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(itemStacks[INPUT_SLOT]);
                        this.smeltItem();
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
            }
            if (flag != this.isBurning()) {
                flag1 = true;
                BlockCoalExtruder.setState(this.isBurning(), this.worldObj, this.pos);
            }
        }
        if (flag1) {
            this.markDirty();
        }
    }

    /*Time cooking 
	 * Int - time
     */
    public int getCookTime(@Nullable ItemStack stack) {
        return ExtruderManager.instance().getCookTime(stack);
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item,
     * destination stack isn't full, etc.
     */
    private boolean canSmelt() {
        if (itemStacks[INPUT_SLOT] == null) {
            return false;
        } else {
            ItemStack itemstack = ExtruderManager.instance().getResult(itemStacks[INPUT_SLOT]);
            if (itemstack == null) {
                return false;
            }
            if (itemStacks[OUTPUT_SLOT] == null) {
                return true;
            }
            if (!itemStacks[OUTPUT_SLOT].isItemEqual(itemstack)) {
                return false;
            }
            int result = itemStacks[OUTPUT_SLOT].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= itemStacks[OUTPUT_SLOT].getMaxStackSize(); // Forge
            // BugFix:
            // Make
            // it
            // respect
            // stack
            // sizes
            // properly.
        }
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted
     * item in the furnace result stack
     */
    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = ExtruderManager.instance().getResult(itemStacks[INPUT_SLOT]);

            if (itemStacks[OUTPUT_SLOT] == null) {
                itemStacks[OUTPUT_SLOT] = itemstack.copy();
            } else if (itemStacks[OUTPUT_SLOT].getItem() == itemstack.getItem()) {
                itemStacks[OUTPUT_SLOT].stackSize += itemstack.stackSize; // Forge
                // BugFix:
                // Results
                // may
                // have
                // multiple
                // items
            }

            if (itemStacks[INPUT_SLOT].getItem() == Item.getItemFromBlock(Blocks.SPONGE)
                    && itemStacks[INPUT_SLOT].getMetadata() == 1 && itemStacks[FUEL_SLOT] != null
                    && itemStacks[FUEL_SLOT].getItem() == Items.BUCKET) {
                itemStacks[FUEL_SLOT] = new ItemStack(Items.WATER_BUCKET);
            }

            --itemStacks[INPUT_SLOT].stackSize;

            if (itemStacks[INPUT_SLOT].stackSize <= 0) {
                itemStacks[INPUT_SLOT] = null;
            }
        }
    }

    public static boolean isItemFuel(ItemStack stack) {
        /**
         * Returns the number of ticks that the supplied fuel item will keep the
         * furnace burning, or 0 if the item isn't fuel
         */
        return getItemBurnTime(stack) > 0;
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
        } else {
            ItemStack itemstack = itemStacks[FUEL_SLOT];
            return isItemFuel(stack)
                    || SlotFurnaceFuel.isBucket(stack) && (itemstack == null || itemstack.getItem() != Items.BUCKET);
        }
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
        return new ContainerCoalExtruder(playerInventory, this);
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.extrudeBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.extrudeBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
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
            if (facing == EnumFacing.DOWN) {
                return (T) handlerBottom;
            } else if (facing == EnumFacing.UP) {
                return (T) handlerTop;
            } else {
                return (T) handlerSide;
            }
        }
        return super.getCapability(capability, facing);
    }
}
