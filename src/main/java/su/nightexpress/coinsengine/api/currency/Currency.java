package su.nightexpress.coinsengine.api.currency;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nexmedia.engine.api.placeholder.Placeholder;
import su.nexmedia.engine.utils.NumberUtil;
import su.nightexpress.coinsengine.Placeholders;
import su.nightexpress.coinsengine.config.Perms;

import java.util.Map;

public interface Currency extends Placeholder {

    default boolean isUnlimited() {
        return this.getMaxValue() <= 0D;
    }

    default boolean isLimited() {
        return !this.isUnlimited();
    }

    default boolean isInteger() {
        return !this.isDecimal();
    }

    default double fine(double amount) {
        return Math.max(0, this.isDecimal() ? amount : Math.floor(amount));
    }

    default double limit(double amount) {
        return this.isLimited() ? Math.min(amount, this.getMaxValue()) : amount;
    }

    default double fineAndLimit(double amount) {
        return this.fine(this.limit(amount));
    }

    default double getExchangeRate(@NotNull Currency currency) {
        return this.getExchangeRate(currency.getId());
    }

    default double getExchangeRate(@NotNull String id) {
        return this.getExchangeRates().getOrDefault(id.toLowerCase(), 0D);
    }

    @NotNull
    default String getPermission() {
        return Perms.PREFIX_CURRENCY + this.getId();
    }

    @NotNull
    default String formatValue(double balance) {
        return NumberUtil.format(this.fine(balance));
    }

    @NotNull
    default String format(double balance) {
        return this.replacePlaceholders().apply(this.getFormat()).replace(Placeholders.GENERIC_AMOUNT, this.formatValue(balance));
    }

    @NotNull String getId();

    @NotNull String getName();

    void setName(@NotNull String name);

    @NotNull String getSymbol();

    void setSymbol(@NotNull String symbol);

    @NotNull String getFormat();

    void setFormat(@NotNull String format);

    @NotNull String[] getCommandAliases();

    void setCommandAliases(@NotNull String... aliases);

    @NotNull ItemStack getIcon();

    void setIcon(@NotNull ItemStack icon);

    boolean isDecimal();

    void setDecimal(boolean decimal);

    boolean isPermissionRequired();

    void setPermissionRequired(boolean permissionRequired);

    boolean isTransferAllowed();

    void setTransferAllowed(boolean transferAllowed);

    double getMinTransferAmount();

    void setMinTransferAmount(double amount);

    double getStartValue();

    void setStartValue(double startValue);

    double getMaxValue();

    void setMaxValue(double maxValue);

    boolean isVaultEconomy();

    void setVaultEconomy(boolean vaultEconomy);

    boolean isExchangeAllowed();

    void setExchangeAllowed(boolean exchangeAllowed);

    @NotNull Map<String, Double> getExchangeRates();
}
