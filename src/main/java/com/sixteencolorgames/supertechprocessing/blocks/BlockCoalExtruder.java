package com.sixteencolorgames.supertechprocessing.blocks;

import com.sixteencolorgames.supertechprocessing.ModBlocks;
import com.sixteencolorgames.supertechprocessing.SuperTechProcessingMod;
import com.sixteencolorgames.supertechprocessing.network.GUIHandler;
import com.sixteencolorgames.supertechprocessing.tileentities.TileEntityCoalExtruder;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCoalExtruder extends BlockBase {

    private final boolean isBurning;
    private static boolean keepInventory;

    public BlockCoalExtruder(boolean isBurning) {
        super(Material.ROCK, "coal_extruder");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.isBurning = isBurning;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.extruder);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    @Override
    public void randomDisplayTick(IBlockState worldIn, World pos, BlockPos state, Random rand) {
        if (this.isBurning) {
            EnumFacing enumfacing = (EnumFacing) worldIn.getValue(FACING);
            double d0 = (double) state.getX() + 0.5D;
            double d1 = (double) state.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double) state.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            switch (enumfacing) {
                case WEST:

                    pos.spawnParticle(EnumParticleTypes.REDSTONE, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case EAST:

                    pos.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case NORTH:

                    pos.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case SOUTH:

                    pos.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            return false;
        } else {
            if (!worldIn.isRemote) {
                if (worldIn.getTileEntity(pos) instanceof TileEntityCoalExtruder) {
                    playerIn.openGui(SuperTechProcessingMod.instance, GUIHandler.EXTRUDER_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }
        return true;

    }

    public static void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;

        if (active) {
            // worldIn.setBlockState(pos, BlocksCore.lit_extruder.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            // worldIn.setBlockState(pos, BlocksCore.lit_extruder.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        } else {
            worldIn.setBlockState(pos, ModBlocks.extruder.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, ModBlocks.extruder.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing
     * the block.
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCoalExtruder();
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to
     * allow for adjustments to the IBlockstate
     */
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow
     * post-place logic
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityCoalExtruder) {
                ((TileEntityCoalExtruder) tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!keepInventory) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityCoalExtruder) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityCoalExtruder) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.extruder);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }
}
