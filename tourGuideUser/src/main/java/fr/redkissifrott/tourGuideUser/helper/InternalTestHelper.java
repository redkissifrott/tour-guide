package fr.redkissifrott.tourGuideUser.helper;

public class InternalTestHelper {

	// TODO CAREFUL NUMBER USERS
	// Set this default up to 100,000 for testing
	private static int internalUserNumber = 50;

	public static void setInternalUserNumber(int internalUserNumber) {
		InternalTestHelper.internalUserNumber = internalUserNumber;
	}

	public static int getInternalUserNumber() {
		return internalUserNumber;
	}
}
