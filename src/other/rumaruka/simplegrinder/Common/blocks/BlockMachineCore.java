package com.rumaruka.simplegrinder.Common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockMachineCore extends Block {

	public BlockMachineCore( ) {
		super(Material.ROCK);
		setHarvestLevel("pickaxe", 0);
		
	}
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
}
