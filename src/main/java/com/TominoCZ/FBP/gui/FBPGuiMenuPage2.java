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
public class FBPGuiMenuPage2 extends GuiScreen {

	GuiButton Reload, Done, Defaults, Back, Next, ReportBug, Enable, b1, b2, b3, b4, b5, b6;

	String b1Text = "Legacy Mode                          ";
	String b2Text = "Cartoon Mode                         ";

	String b3Text = "Smooth Transitions                   ";
	String b4Text = "Inherit Block's Top Texture        ";

	String b5Text = "Spawn Redstone Block Particles   ";
	String b6Text = "Spawn Particles in Freeze Mode   ";

	String description = "";

	boolean reachedEnd = false;

	long time, lastTime;

	double offsetX = 0;

	public void initGui() {
		this.buttonList.clear();

		b1 = new GuiButton(1, this.width / 2 - (96 * 2 + 8) / 2, (int) (this.height / 5) - 10,
				b1Text + (FBP.legacyMode ? FBPGuiHelper.on : FBPGuiHelper.off));
		b2 = new GuiButton(2, this.width / 2 - (96 * 2 + 8) / 2, (int) b1.yPosition + b1.height + 1,
				b2Text + (FBP.cartoonMode ? FBPGuiHelper.on : FBPGuiHelper.off));

		b3 = new GuiButton(3, this.width / 2 - (96 * 2 + 8) / 2, (int) b2.yPosition + b2.height + 6,
				b3Text + (FBP.smoothTransitions ? FBPGuiHelper.on : FBPGuiHelper.off));
		b4 = new GuiButton(4, this.width / 2 - (96 * 2 + 8) / 2, (int) b3.yPosition + b3.height + 1,
				b4Text + (FBP.inheritBlockTopTexture ? FBPGuiHelper.on : FBPGuiHelper.off));

		b5 = new GuiButton(5, this.width / 2 - (96 * 2 + 8) / 2, (int) b4.yPosition + b4.height + 6,
				b5Text + (FBP.spawnRedstoneBlockParticles ? FBPGuiHelper.on : FBPGuiHelper.off));
		b6 = new GuiButton(6, this.width / 2 - (96 * 2 + 8) / 2, (int) b5.yPosition + b5.height + 1,
				b6Text + (FBP.spawnWhileFrozen ? FBPGuiHelper.on : FBPGuiHelper.off));

		Back = new GuiButton(-3, this.width / 2 - 125, (int) b6.yPosition, "<<");
		Next = new GuiButton(-5, b6.xPosition + b6.width + 3, (int) b6.yPosition, ">>");

		Defaults = new GuiButton(0, this.width / 2 + 2, b6.yPosition + b6.height + 24, "Defaults");
		Done = new GuiButton(-1, this.width / 2 - 100, (int) Defaults.yPosition, "Done");
		Reload = new GuiButton(-2, this.width / 2 - 100, (int) Defaults.yPosition + Defaults.height + 1,
				"Reload Config");
		ReportBug = new FBPGuiButtonBugReport(-4, this.width - 27, 2, new Dimension(width, height),
				this.fontRendererObj);
		Enable = new FBPGuiButtonEnable(-6, (this.width - 25 - 27) - 4, 2, new Dimension(width, height),
				this.fontRendererObj);
		Defaults.width = Done.width = 98;
		Reload.width = b1.width = b2.width = b3.width = b4.width = b5.width = b6.width = 200;

		Back.width = Next.width = 22;

		this.buttonList.addAll(java.util.Arrays.asList(
				new GuiButton[] { b1, b2, b3, b4, b5, b6, Defaults, Done, Reload, Back, Next, Enable, ReportBug }));
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case -6:
			FBP.enabled = !FBP.enabled;
			break;
		case -5:
			this.mc.displayGuiScreen(new FBPGuiMenuPage3());
			break;
		case -4:
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/TominoCZ/FancyBlockParticles/issues"));
			} catch (Exception e) {

			}
			break;
		case -3:
			this.mc.displayGuiScreen(new FBPGuiMenuPage1());
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
			b1.displayString = b1Text + ((FBP.legacyMode = !FBP.legacyMode) ? FBPGuiHelper.on : FBPGuiHelper.off);
			break;
		case 2:
			b2.displayString = b2Text + ((FBP.cartoonMode = !FBP.cartoonMode) ? FBPGuiHelper.on : FBPGuiHelper.off);
			break;
		case 3:
			b3.displayString = b3Text
					+ ((FBP.smoothTransitions = !FBP.smoothTransitions) ? FBPGuiHelper.on : FBPGuiHelper.off);
			break;
		case 4:
			b4.displayString = b4Text
					+ ((FBP.inheritBlockTopTexture = !FBP.inheritBlockTopTexture) ? FBPGuiHelper.on : FBPGuiHelper.off);
			break;
		case 5:
			b5.displayString = b5Text + ((FBP.spawnRedstoneBlockParticles = !FBP.spawnRedstoneBlockParticles)
					? FBPGuiHelper.on : FBPGuiHelper.off);
			break;
		case 6:
			b6.displayString = b6Text
					+ ((FBP.spawnWhileFrozen = !FBP.spawnWhileFrozen) ? FBPGuiHelper.on : FBPGuiHelper.off);
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
				&& (mouseY >= b1.yPosition && mouseY < b6.yPosition + b1.height)) {

			moveText();

			this.drawCenteredString(fontRendererObj, description, (int) (this.width / 2 + offsetX), posY,
					fontRendererObj.getColorCode('a'));
		}

		FBPGuiHelper.drawTitle(b1.yPosition, width, height, fontRendererObj);

		super.drawScreen(mouseX, mouseY, partialTicks);
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

	private void getDescription() {
		this.buttonList.stream().filter(button -> button.isMouseOver()).forEach(b -> {
			switch (b.id) {
			case 1:
				description = "Enables the \u00A76old\u00A7a, \u00A76random \u00A7aand \u00A76simple rotation \u00A7amath.";
				break;
			case 2:
				description = "Makes the particles look \u00A76cartoon\u00A7a-ish.";
				break;
			case 3:
				description = "Makes the particles \u00A76rotate\u00A7a, \u00A76scale \u00A7aand \u00A76fade \u00A7asmoothly.";
				break;
			case 4:
				description = "Makes the particles use the \u00A76top texture\u00A7a of the block you hit from the top.";
				break;
			case 5:
				description = "Allows spawning \u00A76redstone block\u00A7a particles.";
				break;
			case 6:
				description = "Allows spawning particles in \u00A76freeze mode\u00A7a.";
				break;
			}
		});
	}
}
