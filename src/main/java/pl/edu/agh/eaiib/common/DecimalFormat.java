package pl.edu.agh.eaiib.common;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.*;


public class DecimalFormat {

    private static final java.text.DecimalFormat DEFAULT_FORMAT = new java.text.DecimalFormat("#,###.00");

    private BigDecimal decimal;

    public DecimalFormat(BigDecimal decimal) {
        this.decimal = checkNotNull(decimal);
    }

    public String printDefault() {
        return DEFAULT_FORMAT.format(decimal);
    }
}
