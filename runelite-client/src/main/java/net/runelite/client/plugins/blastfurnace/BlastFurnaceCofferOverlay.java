/*
 * Copyright (c) 2018, Seth <Sethtroll3@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.blastfurnace;

import java.awt.*;
import java.util.Arrays;
import javax.inject.Inject;
import net.runelite.api.Client;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.api.Varbits.BLAST_FURNACE_COFFER;

import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import net.runelite.api.kit.KitType;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.util.QuantityFormatter;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

class BlastFurnaceCofferOverlay extends OverlayPanel
{
	private static final float COST_PER_HOUR = 72000.0f;

	private final Client client;
	private final BlastFurnacePlugin plugin;
	private final BlastFurnaceConfig config;

	@Inject
	private BlastFurnaceCofferOverlay(Client client, BlastFurnacePlugin plugin, BlastFurnaceConfig config)
	{
		super(plugin);
		setPosition(OverlayPosition.TOP_LEFT);
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Coffer overlay"));
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (plugin.getConveyorBelt() == null)
		{
			return null;
		}

		Widget sack = client.getWidget(WidgetInfo.BLAST_FURNACE_COFFER);

		if (sack != null)
		{
			final int coffer = client.getVar(BLAST_FURNACE_COFFER);

			sack.setHidden(true);

			panelComponent.getChildren().add(LineComponent.builder()
				.left("Coffer:")
				.right(QuantityFormatter.quantityToStackSize(coffer) + " gp")
				.build());

			panelComponent.getChildren().add(LineComponent.builder()
					.left("Glove:")
					.right(QuantityFormatter.quantityToStackSize(coffer) + " gp")
					.build());

			int goldBars = client.getVar(BarsOres.GOLD_BAR.getVarbit());
			boolean goldGloves = client.getLocalPlayer().getPlayerComposition().getEquipmentId(KitType.HANDS) == ItemID.GOLDSMITH_GAUNTLETS;

			ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
			boolean hasGoldOre = inventory != null && Arrays.stream(inventory.getItems()).anyMatch(i -> i.getId() == ItemID.GOLD_ORE);

			if (goldBars > 0 && goldGloves)
			{

				panelComponent.getChildren().add(LineComponent.builder()
					.left("Glove:")
					.right("Ice")
					.build());
			}
			else if (hasGoldOre && !goldGloves) {
				panelComponent.setBackgroundColor(new Color(255,233,0));
				panelComponent.getChildren().add(LineComponent.builder()
						.left("Glove:")
						.right("Gold")
						.build());

			}
			else {
				panelComponent.setBackgroundColor(ComponentConstants.STANDARD_BACKGROUND_COLOR);
			}
		}

		return super.render(graphics);
	}
}
