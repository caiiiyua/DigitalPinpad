package caiiiyua.digitalpin.common.usecase

import junit.framework.TestCase.assertEquals
import org.junit.Test

class GeneratePinBlockUsecaseTest {

    private val generatePinBlockUsecase: GeneratePinBlockUsecase = GeneratePinBlockUsecaseImpl()

    companion object {
        const val HEX_RADIX = 16
        const val PB_IOS_3_VERSION = 3
    }

    @Test
    fun testCorrectPincodeWithPan() {
        val pincode = "123456"
        val pan = "111222333444555"
        val panBlock = 0x0000222333444555

        val pinBlock = generatePinBlockUsecase.getPinBlock(pincode, pan)
        assertEquals(false, pinBlock.isNullOrEmpty())

        val ipb = pinBlock.toLongOrNull(HEX_RADIX)?.xor(panBlock)?.toString(HEX_RADIX)
        val ipbVersion = ipb?.substring(0, 1)?.toInt()
        assertEquals(PB_IOS_3_VERSION, ipbVersion)

        val pincodeLen = ipb?.substring(1, 2)?.toInt()?: 0
        assertEquals(pincode.length, pincodeLen)

        val pincodeDecode = ipb?.substring(2 until (pincodeLen + 2))
        assertEquals(pincode, pincodeDecode)
    }

    @Test
    fun testCorrectPincodeWithDefaultPan() {
        val pincode = "1234"
        val panBlock = 0x0000222233334444

        val pinBlock = generatePinBlockUsecase.getPinBlock(pincode)
        assertEquals(false, pinBlock.isNullOrEmpty())

        val ipb = pinBlock.toLongOrNull(HEX_RADIX)?.xor(panBlock)?.toString(HEX_RADIX)
        val ipbVersion = ipb?.substring(0, 1)?.toInt()
        assertEquals(PB_IOS_3_VERSION, ipbVersion)

        val pincodeLen = ipb?.substring(1, 2)?.toInt()?: 0
        assertEquals(pincode.length, pincodeLen)

        val pincodeDecode = ipb?.substring(2 until (pincodeLen + 2))
        assertEquals(pincode, pincodeDecode)
    }

    @Test
    fun testTooShortPincode() {
        val pincode = ""
        val pinBlock = generatePinBlockUsecase.getPinBlock(pincode)
        assertEquals(true, pinBlock.isNullOrEmpty())
    }

    @Test
    fun testTooLongPincode() {
        val pincode = "12345678901234567890"
        val pinBlock = generatePinBlockUsecase.getPinBlock(pincode)
        assertEquals(true, pinBlock.isNullOrEmpty())
    }
}