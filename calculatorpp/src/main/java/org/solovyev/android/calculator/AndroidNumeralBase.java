package org.solovyev.android.calculator;

import android.app.Activity;
import jscl.NumeralBase;
import org.jetbrains.annotations.NotNull;
import org.solovyev.android.calculator.units.CalculatorNumeralBase;
import org.solovyev.android.view.drag.DirectionDragButton;
import org.solovyev.android.view.drag.DragDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: serso
 * Date: 4/21/12
 * Time: 8:00 PM
 */
public enum AndroidNumeralBase {

    bin(CalculatorNumeralBase.bin) {
        @NotNull
        @Override
        public List<Integer> getButtonIds() {
            return Arrays.asList(R.id.zeroDigitButton, R.id.oneDigitButton);
        }
    },

    oct(CalculatorNumeralBase.oct) {
        @NotNull
        @Override
        public List<Integer> getButtonIds() {
            final List<Integer> result = new ArrayList<Integer>(bin.getButtonIds());
            result.addAll(Arrays.asList(R.id.twoDigitButton, R.id.threeDigitButton, R.id.fourDigitButton, R.id.fiveDigitButton, R.id.sixDigitButton, R.id.sevenDigitButton));
            return result;
        }
    },

    dec(CalculatorNumeralBase.dec) {
        @NotNull
        @Override
        public List<Integer> getButtonIds() {
            final List<Integer> result = new ArrayList<Integer>(oct.getButtonIds());
            result.addAll(Arrays.asList(R.id.eightDigitButton, R.id.nineDigitButton));
            return result;
        }
    },

    hex(CalculatorNumeralBase.hex) {

        @NotNull
        private List<Integer> specialHexButtonIds = Arrays.asList(R.id.oneDigitButton, R.id.twoDigitButton, R.id.threeDigitButton, R.id.fourDigitButton, R.id.fiveDigitButton, R.id.sixDigitButton);

        @NotNull
        @Override
        public List<Integer> getButtonIds() {
            return dec.getButtonIds();
        }

        @Override
        protected void toggleButton(boolean show, @NotNull DirectionDragButton button) {
            super.toggleButton(show, button);
            if (specialHexButtonIds.contains(button.getId())) {
                button.showDirectionText(show, DragDirection.left);
                button.invalidate();
            }
        }
    };

    @NotNull
    private final CalculatorNumeralBase calculatorNumeralBase;

    private AndroidNumeralBase(@NotNull CalculatorNumeralBase calculatorNumeralBase) {
        this.calculatorNumeralBase = calculatorNumeralBase;
    }

    @NotNull
    public abstract List<Integer> getButtonIds();

    public void toggleButtons(boolean show, @NotNull Activity activity) {
        for (Integer buttonId : getButtonIds()) {
            final DirectionDragButton button = (DirectionDragButton) activity.findViewById(buttonId);
            if (button != null) {
                toggleButton(show, button);
            }
        }
    }

    protected void toggleButton(boolean show, @NotNull DirectionDragButton button) {
        button.setShowText(show);
    }

    @NotNull
    public NumeralBase getNumeralBase() {
        return calculatorNumeralBase.getNumeralBase();
    }

    @NotNull
    public static AndroidNumeralBase valueOf(@NotNull NumeralBase nb) {
        for (AndroidNumeralBase androidNumeralBase : values()) {
            if (androidNumeralBase.calculatorNumeralBase.getNumeralBase() == nb) {
                return androidNumeralBase;
            }
        }

        throw new IllegalArgumentException(nb + " is not supported numeral base!");
    }
}