package caiiiyua.digitalpin.common.usecase

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
        assert(!pinBlock.isNullOrEmpty())

        val ipb = pinBlock.toLongOrNull(HEX_RADIX)?.xor(panBlock)?.toString(HEX_RADIX)
        val ipbVersion = ipb?.substring(0, 1)?.toInt()
        assert(ipbVersion == PB_IOS_3_VERSION) { "expected $PB_IOS_3_VERSION, but got $ipbVersion" }

        val pincodeLen = ipb?.substring(1, 2)?.toInt()?: 0
        assert(pincodeLen == pincode.length) { "expected ${pincode.length}, but got $pincodeLen" }

        val pincodeDecode = ipb?.substring(2 until (pincodeLen + 2))
        assert(pincodeDecode == pincode) { "expected $pincode, but got $pincodeDecode" }
    }

    @Test
    fun testCorrectPincodeWithDefaultPan() {
        val pincode = "1234"
        val panBlock = 0x0000222233334444

        val pinBlock = generatePinBlockUsecase.getPinBlock(pincode)
        assert(!pinBlock.isNullOrEmpty())

        val ipb = pinBlock.toLongOrNull(HEX_RADIX)?.xor(panBlock)?.toString(HEX_RADIX)
        val ipbVersion = ipb?.substring(0, 1)?.toInt()
        assert(ipbVersion == PB_IOS_3_VERSION) { "expected $PB_IOS_3_VERSION, but got $ipbVersion" }

        val pincodeLen = ipb?.substring(1, 2)?.toInt()?: 0
        assert(pincodeLen == pincode.length) { "expected ${pincode.length}, but got $pincodeLen" }

        val pincodeDecode = ipb?.substring(2 until (pincodeLen + 2))
        assert(pincodeDecode == pincode) { "expected $pincode, but got $pincodeDecode" }
    }

    @Test
    fun testTooShortPincode() {
        val pincode = ""
        val pinBlock = generatePinBlockUsecase.getPinBlock(pincode)
        assert(pinBlock.isNullOrEmpty())
    }

    @Test
    fun testTooLongPincode() {
        val pincode = "12345678901234567890"
        val pinBlock = generatePinBlockUsecase.getPinBlock(pincode)
        assert(pinBlock.isNullOrEmpty())
    }
}