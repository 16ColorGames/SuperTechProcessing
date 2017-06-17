package com.rumaruka.simplegrinder.Common.tileentity;

import javax.annotation.Nullable;

import com.rumaruka.simplegrinder.Common.blocks.BlockCoalGrinder;
import com.rumaruka.simplegrinder.Common.containers.ContainerCoaGrinder;
import com.rumaruka.simplegrinder.Init.BlocksCore;
import com.rumaruka.simplegrinder.Init.GrinderRecipes;
import com.rumaruka.simplegrinder.Init.ItemsCore;

import net.minecraft.block.Block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCoalGrinder extends TileEntityLockable implements ITickable, ISidedInventory {

    private static final int[] SLOTS_TOP = new int[]{0};
    private static final int[] SLOTS_BOTTOM = new int[]{2, 1};
    private static final int[] SLOTS_SIDES = new int[]{1};
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStack[] grindItemStacks = new ItemStack[3];
    /**
     * The number of ticks that the furnace will keep burning
     */
    private int grindBurnTime;
    /**
     * The number of ticks that a fresh copy of the currently-burning item would
     * keep the furnace burning for
     */
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;
    private String grindCustomName;

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory() {
        return this.grindItemStacks.length;
    }

    /**
     * Returns the stack in the given slot.
     */
    @Nullable
    public ItemStack getStackInSlot(int index) {
        return this.grindItemStacks[index];
    }

    /**
     * Removes up to a specified number of items from an inventory slot and
     * returns them in a new stack.
     */
    @Nullable
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.grindItemStacks, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    @Nullable
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.grindItemStacks, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be
     * crafting or armor sections).
     */
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        boolean flag = stack != null && stack.isItemEqual(this.grindItemStacks[index])
                && ItemStack.areItemStackTagsEqual(stack, this.grindItemStacks[index]);
        this.grindItemStacks[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

        if (index == 0 && !flag) {
            this.totalCookTime = this.getCookTime(stack);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName() {
        return this.hasCustomName() ? this.grindCustomName : "container.grinder";
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName() {
        return this.grindCustomName != null && !this.grindCustomName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_) {
        this.grindCustomName = p_145951_1_;
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.grindItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < this.grindItemStacks.length) {
                this.grindItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }

        this.grindBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(this.grindItemStacks[1]);

        if (compound.hasKey("CustomName", 8)) {
            this.grindCustomName = compound.getString("CustomName");
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", this.grindBurnTime);
        compound.setInteger("CookTime", this.cookTime);
        compound.setInteger("CookTimeTotal", this.totalCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.grindItemStacks.length; ++i) {
            if (this.grindItemStacks[i] != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                this.grindItemStacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Items", nbttaglist);

        if (this.hasCustomName()) {
            compound.setString("CustomName", this.grindCustomName);
        }

        return compound;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be
     * 64, possibly will be extended.
     */
    public int getInventoryStackLimit() {
        return 64;
    }

    /**
     * Furnace isBurning
     */
    public boolean isBurning() {
        return this.grindBurnTime > 0;
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
            --this.grindBurnTime;
        }
        if (!this.worldObj.isRemote) {
            if (this.isBurning() || this.grindItemStacks[1] != null && this.grindItemStacks[0] != null) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.currentItemBurnTime = this.grindBurnTime = getItemBurnTime(this.grindItemStacks[1]);
                    if (this.isBurning()) {
                        flag1 = true;

                        if (this.grindItemStacks[1] != null) {
                            --this.grindItemStacks[1].stackSize;

                            if (this.grindItemStacks[1].stackSize == 0) {
                                this.grindItemStacks[1] = grindItemStacks[1].getItem()
                                        .getContainerItem(grindItemStacks[1]);
                            }
                        }
                    }
                }
                if (this.isBurning() && this.canSmelt()) {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.grindItemStacks[0]);
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
                BlockCoalGrinder.setState(this.isBurning(), this.worldObj, this.pos);
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
        return 160;
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item,
     * destination stack isn't full, etc.
     */
    private boolean canSmelt() {
        if (this.grindItemStacks[0] == null) {
            return false;
        } else {
            ItemStack itemstack = GrinderRecipes.instance().getSmeltingResult(this.grindItemStacks[0]);
            if (itemstack == null) {
                return false;
            }
            if (this.grindItemStacks[2] == null) {
                return true;
            }
            if (!this.grindItemStacks[2].isItemEqual(itemstack)) {
                return false;
            }
            int result = grindItemStacks[2].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.grindItemStacks[2].getMaxStackSize(); // Forge
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
            ItemStack itemstack = GrinderRecipes.instance().getSmeltingResult(this.grindItemStacks[0]);

            if (this.grindItemStacks[2] == null) {
                this.grindItemStacks[2] = itemstack.copy();
            } else if (this.grindItemStacks[2].getItem() == itemstack.getItem()) {
                this.grindItemStacks[2].stackSize += itemstack.stackSize; // Forge
                // BugFix:
                // Results
                // may
                // have
                // multiple
                // items
            }

            if (this.grindItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.SPONGE)
                    && this.grindItemStacks[0].getMetadata() == 1 && this.grindItemStacks[1] != null
                    && this.grindItemStacks[1].getItem() == Items.BUCKET) {
                this.grindItemStacks[1] = new ItemStack(Items.WATER_BUCKET);
            }

            --this.grindItemStacks[0].stackSize;

            if (this.grindItemStacks[0].stackSize <= 0) {
                this.grindItemStacks[0] = null;
            }
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the
     * furnace burning, or 0 if the item isn't fuel
     */
    public static int getItemBurnTime(ItemStack stack) {
        if (stack == null) {
            return 0;
        } else {
            Item item = stack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.WOODEN_SLAB) {
                    return 100;
                }

                if (block.getDefaultState().getMaterial() == Material.WOOD) {
                    return 250;
                }

                if (block == Blocks.COAL_BLOCK) {
                    return 10000;
                }
            }

            if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) {
                return 100;
            }
            if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) {
                return 100;
            }
            if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD")) {
                return 100;
            }
            if (item == Items.STICK) {
                return 25;
            }
            if (item == Items.COAL) {
                return 1400;
            }
            if (item == Items.LAVA_BUCKET) {
                return 20000;
            }
            if (item == Item.getItemFromBlock(Blocks.SAPLING)) {
                return 25;
            }
            if (item == Items.BLAZE_ROD) {
                return 3000;
            }
            if (item == ItemsCore.i_fuel) {
                return 30000;
            }
            if (item == ItemsCore.wood_chips) {
                return 10;
            }
            return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
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
     * Do not make give this method the name canInteractWith because it clashes
     * with Container
     */
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) != this ? false
                : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
                        (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void openInventory(EntityPlayer player) {
    }

    public void closeInventory(EntityPlayer player) {
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring
     * stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.grindItemStacks[1];
            return isItemFuel(stack)
                    || SlotFurnaceFuel.isBucket(stack) && (itemstack == null || itemstack.getItem() != Items.BUCKET);
        }
    }

    public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);
    }

    /**
     * Returns true if automation can insert the given item in the given slot
     * from the given side.
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot
     * from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == 1) {
            Item item = stack.getItem();

            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    public String getGuiID() {
        return "minecraft:furnace";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerCoaGrinder(playerInventory, this);
    }

    public int getField(int id) {
        switch (id) {
            case 0:
                return this.grindBurnTime;
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

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.grindBurnTime = value;
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

    public int getFieldCount() {
        return 4;
    }

    public void clear() {
        for (int i = 0; i < this.grindItemStacks.length; ++i) {
            this.grindItemStacks[i] = null;
        }
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
