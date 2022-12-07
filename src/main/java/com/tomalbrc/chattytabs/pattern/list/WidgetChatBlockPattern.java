package com.tomalbrc.stm.pattern.list;

import com.tomalbrc.stm.gui.GuiEditPattern;
import com.tomalbrc.stm.pattern.ChatBlockPattern;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetListEntrySortable;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WidgetChatBlockPattern extends WidgetListEntrySortable<ChatBlockPattern> {

    @Nullable
    private final ChatBlockPattern entry;

    private final int buttonsStartX;

    private final boolean isOdd;

    public final WidgetListChatBlockPattern parent;

    public WidgetChatBlockPattern(int x, int y, int width, int height, boolean isOdd,
                                  List<ChatBlockPattern> patternList, @Nullable ChatBlockPattern entry, int listIndex, WidgetListChatBlockPattern parent) {
        super(x, y, width, height, entry, listIndex);
        this.entry = entry;
        this.parent = parent;

        int posX = x + width - 2;
        int posY = y + 1;

        // Note: These are placed from right to left

        posX = this.createButtonGeneric(posX, posY, "§4Remove", new IButtonActionListener() {
            @Override
            public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
                parent.removePattern(entry);
            }
        });
        posX = this.createButtonGeneric(posX, posY, "Edit", new IButtonActionListener() {
            @Override
            public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
                GuiBase.openGui(new GuiEditPattern(entry));
            }
        });

        this.isOdd = isOdd;

        this.buttonsStartX = posX;
    }

    public int createButtonGeneric(int xRight, int y, String title, IButtonActionListener listener)
    {
        return this.addButton(new ButtonGeneric(xRight, y, -1, true, title), listener).getX() - 1;
    }

    @Override
    protected int getColumnPosX(int column) {
        return getWidth()/getColumnCount()+2;
    }

    @Override
    protected int getCurrentSortColumn() {
        return 0;
    }

    @Override
    protected boolean getSortInReverse() {
        return false;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, MatrixStack matrixStack) {
        RenderUtils.color(1f, 1f, 1f, 1f);

        // Draw a lighter background for the hovered and the selected entry
        if (selected || this.isMouseOver(mouseX, mouseY)) {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0707070);
        }
        else if (this.isOdd) {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0101010);
        }
        // Draw a slightly lighter background for even entries
        else {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0303030);
        }

        this.drawString(this.x + 4, this.y + 4, 0xFFFFFFFF, this.entry.getTitle(), matrixStack);

        this.drawSubWidgets(mouseX, mouseY, matrixStack);
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, MatrixStack matrixStack) {
        List<String> text = new ArrayList<>();
        /*
        text.add(StringUtils.translate("litematica.gui.label.schematic_placement.schematic_name", this.placement.getSchematic().getMetadata().getName()));
        text.add(StringUtils.translate("litematica.gui.label.schematic_placement.schematic_file", fileName));
        BlockPos o = this.placement.getOrigin();
        String strOrigin = String.format("x: %d, y: %d, z: %d", o.getX(), o.getY(), o.getZ());
        text.add(StringUtils.translate("litematica.gui.label.schematic_placement.origin", strOrigin));
        text.add(StringUtils.translate("litematica.gui.label.schematic_placement.sub_region_count", String.valueOf(this.placement.getSubRegionCount())));

        Vec3i size = this.placement.getSchematic().getTotalSize();
        String strSize = String.format("%d x %d x %d", size.getX(), size.getY(), size.getZ());
        text.add(StringUtils.translate("litematica.gui.label.schematic_placement.enclosing_size", strSize));
        */

        RenderUtils.drawHoverText(mouseX, mouseY, text, matrixStack);
    }
}
