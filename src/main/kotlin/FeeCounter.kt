val MIN_FEE = 35_00//копеек
val VISA_MIR_PERCENT = 0.75//%
val MAESTRO_MC_PERCENT = 0.6//%
val MAESTRO_MC_LIMIT = 75000_00//копеек
val MAESTRO_MC_FEE = 20_00//копеек

enum class CardType {
    Visa, Mastercard, Maestro, MIR, VkPay
}

fun main() {
    val type0: CardType = CardType.Visa
    val currentTransaction0 = 100_00
    println(countFee(type0, currentTransaction = currentTransaction0) / 100)

    val type1: CardType = CardType.Visa
    val currentTransaction1 = 10000_00
    println(countFee(type1, currentTransaction = currentTransaction1) / 100)

    val type2: CardType = CardType.Maestro
    val currentTransaction2 = 10000_00
    val amountOfPreviousTransactions2 = 80000_00
    println(countFee(type2, amountOfPreviousTransactions2, currentTransaction2) / 100)

    val type3: CardType = CardType.Mastercard
    val currentTransaction3 = 10000_00
    val amountOfPreviousTransactions3 = 50000_00
    println(countFee(type3, amountOfPreviousTransactions3, currentTransaction3) / 100)

    val type4: CardType = CardType.MIR
    val currentTransaction4 = 10000_00
    print(countFee(type4, currentTransaction = currentTransaction4) / 100)
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


