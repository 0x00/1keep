package info.mindland.theoneandonly;

public class Random {

	double m = 1.1233456696598231222567567, a = 11.0, c = 17.0;
	private double seed;

	public Random(double seed) {
		this.seed = seed;
	}

	public Random(String string) {
		this.seed = 1.0;
		Glyph gl = new Glyph();
		
		for (char c : string.toCharArray()) {
			this.seed += gl.idx(c);
		}

	}

	public double nextDouble() {
		seed = (a * seed + c) % m;
		return seed / m;
	}

	public int nextInt(int max) {
		return (int) (nextDouble() * max + 0.5);
	}

}