public class Czas {


	public static double returnIntensity(double lambda) {

		return exponential(lambda);
	}

	public static  double returnSysOn() {

		return exponential(1/40F);
	}

	public static  double returnSysOff() {

		return exponential(1/35F);
	}

	public static  double getServiceTime() {

	//	return uniform(0.1, 0.15);
    	return exponential(8);
	}

    static double exponential(double lambda) {

        return Math.log(1 - 0.5) / (-lambda);
    }

    static double uniform(double min, double max) {

        return 0.5 * (max - min) + min;
    }

}
