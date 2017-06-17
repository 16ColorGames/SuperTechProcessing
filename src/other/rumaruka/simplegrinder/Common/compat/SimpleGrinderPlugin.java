package com.rumaruka.simplegrinder.Common.compat;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.INbtRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mezz.jei.plugins.vanilla.brewing.BrewingRecipeCategory;
import mezz.jei.plugins.vanilla.brewing.BrewingRecipeHandler;
import mezz.jei.plugins.vanilla.brewing.BrewingRecipeMaker;
import mezz.jei.plugins.vanilla.crafting.CraftingRecipeCategory;
import mezz.jei.plugins.vanilla.crafting.ShapedOreRecipeHandler;
import mezz.jei.plugins.vanilla.crafting.ShapedRecipesHandler;
import mezz.jei.plugins.vanilla.crafting.ShapelessOreRecipeHandler;
import mezz.jei.plugins.vanilla.crafting.ShapelessRecipesHandler;
import mezz.jei.plugins.vanilla.crafting.TippedArrowRecipeHandler;
import mezz.jei.plugins.vanilla.crafting.TippedArrowRecipeMaker;
import mezz.jei.plugins.vanilla.furnace.FuelRecipeHandler;
import mezz.jei.plugins.vanilla.furnace.FuelRecipeMaker;
import mezz.jei.plugins.vanilla.furnace.FurnaceFuelCategory;
import mezz.jei.plugins.vanilla.furnace.FurnaceSmeltingCategory;
import mezz.jei.plugins.vanilla.furnace.SmeltingRecipeHandler;
import mezz.jei.plugins.vanilla.furnace.SmeltingRecipeMaker;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.rumaruka.simplegrinder.Client.gui.GuiCoalGrinder;
import com.rumaruka.simplegrinder.Common.compat.grinder.CoalGrinderingCategory;
import com.rumaruka.simplegrinder.Common.compat.grinder.GrinderingRecipeCategory;
import com.rumaruka.simplegrinder.Common.compat.grinder.GrinderingRecipeHandler;
import com.rumaruka.simplegrinder.Common.compat.grinder.GrinderingRecipeMaker;
import com.rumaruka.simplegrinder.Init.BlocksCore;

@JEIPlugin
public class SimpleGrinderPlugin extends BlankModPlugin {
	@Override
	public void register(@Nonnull IModRegistry registry) {
		IItemRegistry itemRegistry = registry.getItemRegistry();
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();

		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(BlocksCore.lit_grinder));
		registry.addRecipeCategories(
			
				new CoalGrinderingCategory(guiHelper)
			
				
		);

		registry.addRecipeHandlers(
				
				
				new GrinderingRecipeHandler()
				
		);

		
		registry.addRecipeClickArea(GuiCoalGrinder.class, 78, 32, 28, 23, SimpleGrinderRecipeUID.GRINDER_UID, VanillaRecipeCategoryUid.FUEL);

		

		registry.addRecipes(GrinderingRecipeMaker.getFurnaceRecipes(jeiHelpers));
	
	}
}
