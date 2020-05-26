package caiiiyua.digitalpin.common.usecase

import caiiiyua.digitalpin.common.usecase.GeneratePinBlockUsecaseImpl.Companion.PAN

interface GeneratePinBlockUsecase {
    fun getPinBlock(pincode: String, pan: String = PAN): String
}

/**
 * The Pin Block implementation is basically on
 * https://www.eftlab.com/knowledge-base/261-complete-list-of-pin-blocks-in-payments/
 * But due to the description of random values for ISO-3 in this doc is not consistent,
 * says the random values from 10 - 15, but in the calculation steps the random values from X'0' - X'F',
 * So double checked at https://www.ibm.com/support/knowledgecenter/linuxonibm/com.ibm.linux.z.wskc.doc/wskc_r_appdiso3.html
 * The implementation below is using the random values from 10 - 15 (X'A' - X'F')
 */
class GeneratePinBlockUsecaseImpl : GeneratePinBlockUsecase {

    companion object {
        const val HEX_RADIX = 16
        const val MIN_PIN_CODE_LENGTH = 4
        const val MAX_PIN_CODE_LENGTH = 12
        const val PB_RANDOM_PADDING_VALUES = "ABCDEF"
        const val PAN = "1111222233334444"
        const val PB_ISO_3_PAN_HEADER = "0000"
        const val PB_ISO_3_PAN_LENGTH = 12
        const val PB_ISO_3_HEADER = 3
        const val PB_ISO_3_LENGTH = 16
    }
    override fun getPinBlock(pincode: String, pan: String): String {
        if (pincode.length < MIN_PIN_CODE_LENGTH || pincode.length > MAX_PIN_CODE_LENGTH) return ""

        val intermediatePinBlock = getIntermediatePinBlock(pincode)
        if (intermediatePinBlock == 0L) return ""

        val panBlock = getPanBlock(pan)
        if (panBlock == 0L) return ""

        return intermediatePinBlock.xor(panBlock).toString(HEX_RADIX).toUpperCase()
    }

    private fun getPanBlock(pan: String): Long {
        val panBlockStr = PB_ISO_3_PAN_HEADER + pan.substring(pan.length - PB_ISO_3_PAN_LENGTH)
        return panBlockStr.toLongOrNull(HEX_RADIX)?: 0
    }

    private fun getIntermediatePinBlock(pincode: String): Long {
        val pincodeLenHexStr = pincode.length.toString(HEX_RADIX).toUpperCase()
        val ipbWithoutRandomPadding = "$PB_ISO_3_HEADER$pincodeLenHexStr$pincode"

        val randomPaddingLen = PB_ISO_3_LENGTH - ipbWithoutRandomPadding.length
        val randomPaddingStringBuffer = StringBuffer()
        for (index in 0 until randomPaddingLen) {
            randomPaddingStringBuffer.append(PB_RANDOM_PADDING_VALUES.random())
        }

        val ipbStr = ipbWithoutRandomPadding + randomPaddingStringBuffer.toString()
        return ipbStr.toLongOrNull(HEX_RADIX)?: 0
    }

}

