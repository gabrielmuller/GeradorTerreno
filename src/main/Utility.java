package main;

final class Utility {
	
	static int twosComplement (int i) {
		return (i+1)*(-1);
	}
        
        static float changeZero (float value, float zero) {
            value -= zero;
            if (value < 0) {
                value /= (1+zero);
            } else {
                value /= (1-zero);
            }
            return value;
        }
        
        static float clamp(float value, float min, float max) {
        	if (value > max) {
        		value = max;
        	} else if (value < min) {
        		value = min;
        	}
        	return value;
        }
}
