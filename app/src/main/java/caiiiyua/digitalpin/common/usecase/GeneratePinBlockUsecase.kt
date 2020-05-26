package caiiiyua.digitalpin.common.usecase

interface GeneratePinBlockUsecase {
    fun getPinBlock(pincode: String): String
}

class GeneratePinBlockUsecaseImpl(): GeneratePinBlockUsecase {

    override fun getPinBlock(pincode: String): String {
        return pincode
    }

}

