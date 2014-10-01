package info.mindland.theoneandonly;

public class Password {

	public static String pw(String mantra, String site, int number) {
		Glyph glyp = new Glyph();

		Random rnd = new Random(mantra+site+number);
		int length = 15;

		StringBuilder b = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int v = rnd.nextInt(glyp.chars.size() - 1);
			b.append(glyp.chars.get(v));
		}

		return b.toString();
	}
}
