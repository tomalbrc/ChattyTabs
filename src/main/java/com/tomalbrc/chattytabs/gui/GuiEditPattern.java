package com.tomalbrc.chattytabs.gui;

import com.tomalbrc.chattytabs.helper.Data;
import com.tomalbrc.chattytabs.pattern.ChatBlockPattern;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.ButtonOnOff;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ITextFieldListener;
import fi.dy.masa.malilib.gui.wrappers.TextFieldWrapper;
import net.minecraft.text.Text;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class GuiEditPattern extends GuiBase implements ITextFieldListener<GuiTextFieldGeneric>, IButtonActionListener
{
    private GuiTextFieldGeneric nameTextField;
    private GuiTextFieldGeneric patternTextField;

    private ButtonOnOff autoExclusiveButton;
    private boolean autoExclusive;

    private ChatBlockPattern existingPattern;

    private ButtonGeneric saveButton;

    public GuiEditPattern()
    {
        this.title = "Edit Pattern";
    }

    public GuiEditPattern(ChatBlockPattern existingPattern)
    {
        this.title = "Edit Pattern";
        this.existingPattern = existingPattern;
    }

    @Override
    public void initGui() {
        super.initGui();

        int x = 12;
        int y = 100;
        int width = 100;

        nameTextField = new GuiTextFieldGeneric(x, y, 100, 22, this.textRenderer);
        nameTextField.setText("title");
        y += 26;

        patternTextField = new GuiTextFieldGeneric(x, y, 200, 22, this.textRenderer);
        patternTextField.setText(".*");
        y += 26;

        if (existingPattern != null) {
            autoExclusive = existingPattern.isAutoExclusive();
            nameTextField.setText(existingPattern.getTitle());
            patternTextField.setText(existingPattern.getPattern().toString());
        }

        autoExclusiveButton = new ButtonOnOff(x, y, width, false, "Auto Exclusive", autoExclusive);
        autoExclusiveButton.setDisplayString((autoExclusive ? "§a" : "§c") + "Auto Exclusive: " + (autoExclusive ? "On" : "Off"));
        this.addButton(autoExclusiveButton, this);
        y += 26;

        y += 50;

        ButtonGeneric cancelButton = new ButtonGeneric(x, y, width, 20, "Cancel");
        this.addButton(cancelButton, this);

        saveButton = new ButtonGeneric(x+width+4, y, width, 20, "Save");
        this.addButton(saveButton, this);

        this.addTextField(nameTextField, this);
        this.addTextField(patternTextField, this);
    }

    private void validateInput() {
        try {
            saveButton.setEnabled(Pattern.compile(patternTextField.getText()) != null && !nameTextField.getText().isEmpty());
        }
        catch (PatternSyntaxException exception) {
            saveButton.setEnabled(false);
        }
    }

    @Override
    public boolean onTextChange(GuiTextFieldGeneric textField) {
        validateInput();
        return true;
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
        if (button == saveButton) {
            if (existingPattern != null) {
                existingPattern.setTitle(nameTextField.getText());
                existingPattern.setPattern(Pattern.compile(patternTextField.getText()));
                existingPattern.setAutoExclusive(autoExclusive);
            }
            else {
                // New entry
                Data.add(new ChatBlockPattern(nameTextField.getText(), Pattern.compile(patternTextField.getText()), autoExclusive, true));
            }

            Data.save();
            GuiBase.openGui(new GuiPatternList());
        }
        else if (button == autoExclusiveButton) {
            autoExclusive = !autoExclusive;
            button.setDisplayString((autoExclusive ? "§a" : "§c") + "Auto Exclusive: " + (autoExclusive ? "On" : "Off"));
        }
        else { // Cancel btn
            GuiBase.openGui(new GuiPatternList());
        }
    }
}
