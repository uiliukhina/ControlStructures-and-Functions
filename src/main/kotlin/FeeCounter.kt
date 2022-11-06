val MIN_FEE = 35_00//копеек
val VISA_MIR_PERCENT = 0.75//%
val MAESTRO_MC_PERCENT = 0.6//%
val MAESTRO_MC_LIMIT = 75000_00//копеек
val MAESTRO_MC_FEE = 20_00//копеек

enum class CardType {
    Visa, Mastercard, Maestro, MIR, VkPay
}

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

fun countFee(type: CardType = CardType.VkPay, amountOfPreviousTransactions: Int = 0, currentTransaction: Int): Int {
    return when {
        (type == CardType.Maestro || type == CardType.Mastercard) -> //оплата Maestro или Mastercard
            if (amountOfPreviousTransactions + currentTransaction <= MAESTRO_MC_LIMIT) 0
            else (currentTransaction * MAESTRO_MC_PERCENT / 100 + MAESTRO_MC_FEE).toInt()

        (type == CardType.Visa || type == CardType.MIR) -> { //оплата Мир или Visa
            if (currentTransaction * VISA_MIR_PERCENT / 100 >= MIN_FEE)
                (currentTransaction * VISA_MIR_PERCENT / 100).toInt()
            else MIN_FEE
        }

        else -> 0//оплата VkPay
    }
}


