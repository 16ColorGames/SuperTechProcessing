package com.rumaruka.simplegrinder.Common.compat.grinder;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.rumaruka.simplegrinder.Common.compat.SimpleGrinderWrapper;
import mezz.jei.api.ingredients.IIngredients;

import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class GrinderingRecipe extends SimpleGrinderWrapper{
	@Nonnull
	private final List<List<ItemStack>> input;
	@Nonnull
	private final List<ItemStack> outputs;

	@Nullable
	private final String experienceString;

	public GrinderingRecipe(@Nonnull List<ItemStack> input, @Nonnull ItemStack output, float experience) {
		this.input = Collections.singletonList(input);
		this.outputs = Collections.singletonList(output);

		if (experience > 0.0) {
			experienceString = Translator.translateToLocalFormatted("gui.jei.category.smelting.experience", experience);
		} else {
			experienceString = null;
		}
	}

	@Nonnull
	public List<List<ItemStack>> getInputs() {
		return input;
	}

	@Nonnull
	public List<ItemStack> getOutputs() {
		return outputs;
	}
	@Override	
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		if (experienceString != null) {
			FontRenderer fontRendererObj = minecraft.fontRendererObj;
			int stringWidth = fontRendererObj.getStringWidth(experienceString);
			fontRendererObj.drawString(experienceString, recipeWidth - stringWidth, 0, Color.gray.getRGB());
		}
	}

    @Override
    public void getIngredients(IIngredients ingredients) {
    }
}
