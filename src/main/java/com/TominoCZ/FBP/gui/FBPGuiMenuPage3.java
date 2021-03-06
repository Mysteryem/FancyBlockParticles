package com.TominoCZ.FBP.gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URI;

import com.TominoCZ.FBP.FBP;
import com.TominoCZ.FBP.handler.FBPConfigHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FBPGuiMenuPage3 extends GuiScreen {

	GuiButton Reload, Done, Defaults, Back, ReportBug, Enable, b1, b2, b3;

	String b1Text = "Random Fade Speed                 ";
	String b2Text = "Collide With Entities                  ";
	String b3Text = "Bounce Off Walls                     ";

	String description = "";

	double offsetX = 0;

	public void initGui() {
		this.buttonList.clear();

		b1 = new GuiButton(1, this.width / 2 - (96 * 2 + 8) / 2, (int) (this.height / 5) - 10,
				b1Text + (FBP.randomFadingSpeed ? FBPGuiHelper.on : FBPGuiHelper.off));
		b2 = new GuiButton(2, b1.xPosition, (int) b1.yPosition + b1.height + 1,
				b2Text + (FBP.entityCollision ? FBPGuiHelper.on : FBPGuiHelper.off));
		b3 = new GuiButton(3, b1.xPosition, (int) b2.yPosition + b2.height + 6,
				b3Text + (FBP.bounceOffWalls ? FBPGuiHelper.on : FBPGuiHelper.off));

		Back = new GuiButton(-3, this.width / 2 - 125, (int) 6 * b1.height + b1.yPosition - 5, "<<");
		Defaults = new GuiButton(0, this.width / 2 + 2, Back.yPosition + Back.height + 24, "Defaults");
		Done = new GuiButton(-1, this.width / 2 - 100, (int) Defaults.yPosition, "Done");
		Reload = new GuiButton(-2, this.width / 2 - 100, (int) Defaults.yPosition + Defaults.height + 1,
				"Reload Config");
		ReportBug = new FBPGuiButtonBugReport(-4, this.width - 27, 2, new Dimension(width, height),
				this.fontRendererObj);
		Enable = new FBPGuiButtonEnable(-5, (this.width - 25 - 27) - 4, 2, new Dimension(width, height),
				this.fontRendererObj);

		Defaults.width = Done.width = 98;
		Reload.width = b1.width = 200;

		Back.width = 22;

		this.buttonList.addAll(java.util.Arrays
				.asList(new GuiButton[] { b1, b2, b3, Defaults, Done, Reload, Back, Enable, ReportBug }));
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case -5:
			FBP.enabled = !FBP.enabled;
			break;
		case -4:
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/TominoCZ/FancyBlockParticles/issues"));
			} catch (Exception e) {

			}
			break;
		case -3:
			this.mc.displayGuiScreen(new FBPGuiMenuPage2());
			break;
		case -2:
			FBPConfigHandler.init();
			break;
		case -1:
			this.mc.displayGuiScreen((GuiScreen) null);
			break;
		case 0:
			this.mc.displayGuiScreen(new FBPGuiYesNo(this));
			break;
		case 1:
			b1.displayString = b1Text
					+ ((FBP.randomFadingSpeed = !FBP.randomFadingSpeed) ? FBPGuiHelper.on : FBPGuiHelper.off);
			break;
		case 2:
			b2.displayString = b2Text
					+ ((FBP.entityCollision = !FBP.entityCollision) ? FBPGuiHelper.on : FBPGuiHelper.off);
			break;
		case 3:
			b3.displayString = b3Text + ((FBP.bounceOffWalls = !FBP.bounceOffWalls) ? FBPGuiHelper.on : FBPGuiHelper.off);
			break;
		}

		FBPConfigHandler.check();
		FBPConfigHandler.write();
	}

	public boolean doesGuiPauseGame() {
		return true;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawBackground(0);
		FBPGuiHelper.background(b1.yPosition - 6, Done.yPosition - 4, width, height);

		int posY = Done.yPosition - 18;

		getDescription();

		if ((mouseX >= b1.xPosition && mouseX < b1.xPosition + b1.width)
				&& (mouseY >= b1.yPosition && mouseY < /* TODO */b3.yPosition + b1.height)) {

			moveText();

			this.drawCenteredString(fontRendererObj, description, (int) (this.width / 2 + offsetX), posY,
					fontRendererObj.getColorCode('a'));
		}

		FBPGuiHelper.drawTitle(b1.yPosition, width, height, fontRendererObj);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private void getDescription() {
		this.buttonList.stream().filter(button -> button.isMouseOver()).forEach(b -> {
			switch (b.id) {
			case 1:
				description = "Enables \u00A76random \u00A7aparticle \u00A76fade away\u00A7a-transition speed.";
				break;
			case 2:
				description = "Enables \u00A76entity collisions \u00A7awith the particles.";
				break;
			case 3:
				description = "Makes the particles \u00A76ricochet/bounce\u00A7a off walls.";
				break;
			}
		});
	}

	private void moveText() {
		int textWidth = this.fontRendererObj.getStringWidth(description);
		int outsideSizeX = textWidth - this.width;

		if (textWidth > width) // TODO
		{
			double speedOfSliding = 2400;
			long time = System.currentTimeMillis();

			float normalValue = (float) ((time / speedOfSliding) % 2);

			if (normalValue > 1)
				normalValue = 2 - normalValue;

			offsetX = (outsideSizeX * 2) * normalValue - outsideSizeX;
		} else
			offsetX = 0;
	}
}
