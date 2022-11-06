import org.junit.Test

import org.junit.Assert.*

import CardType.*

class FeeCounterKtTest {
    /**
     * Параметры:
     * тип карты/счёта (по умолчанию VK Pay);
     * сумму предыдущих переводов в этом месяце (по умолчанию 0 рублей);
     * сумму совершаемого перевода.
     * Условия:
     * Mastercard, Maestro - до 75 000руб не взимается, иначе 0.6% + 20 руб
     * Visa, Мир - 0.75%, минимум 35
     * VkPay - не взимается
     */
    @Test
    fun countFeeDefault() {
        val currentTransaction = 100_00
        val result = countFee(currentTransaction = currentTransaction) / 100
        assertEquals(0, result)
    }

    @Test
    fun countFeeMaestroWithoutFee() {
        val type: CardType = Mastercard
        val currentTransaction = 100_00
        val result = countFee(type, currentTransaction = currentTransaction) / 100
        assertEquals(0, result)
    }

    @Test
    fun countFeeMaestroWithFee() {
        val type: CardType = Maestro
        val amountOfPreviousTransactions = 66000_00
        val currentTransaction = 10000_00
        val result = countFee(type, amountOfPreviousTransactions, currentTransaction) / 100
        assertEquals(80, result)
    }

    @Test
    fun countFeeVisaWithMin() {
        val type: CardType = Visa
        val currentTransaction = 100_00
        val result = countFee(type, currentTransaction = currentTransaction) / 100
        assertEquals(35, result)
    }

    @Test
    fun countFeeVisa() {
        val type: CardType = MIR
        val currentTransaction = 10000_00
        val result = countFee(type, currentTransaction = currentTransaction) / 100
        assertEquals(75, result)
    }
}