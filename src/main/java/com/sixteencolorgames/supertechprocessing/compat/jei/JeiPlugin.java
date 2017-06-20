/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.jei;

import com.sixteencolorgames.supertechprocessing.compat.jei.extruder.ExtruderRecipeCategory;
import com.sixteencolorgames.supertechprocessing.compat.jei.roller.RollerRecipeCategory;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

/**
 *
 * @author oa10712
 */
@JEIPlugin
public class JeiPlugin extends BlankModPlugin {

    @Override
    public void register(IModRegistry registry) {
        RollerRecipeCategory.init(registry);
        ExtruderRecipeCategory.init(registry);
    }

}
