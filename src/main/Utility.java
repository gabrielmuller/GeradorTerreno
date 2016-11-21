package main;

public final class Utility {
	
	public static int twosComplement (int i) {
		return (i+1)*(-1);
	}
        
        public static float changeZero (float value, float zero) {
            value -= zero;
            if (value < 0) {
                value /= (1+zero);
            } else {
                value /= (1-zero);
            }
            return value;
        }
}
