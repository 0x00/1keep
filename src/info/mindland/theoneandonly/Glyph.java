package info.mindland.theoneandonly;

import java.util.LinkedList;
import java.util.List;

public class Glyph {
	public List<String> chars = new LinkedList<String>();

	List<String> ascii(int from, int to) {
		List<String> out = new LinkedList<String>();
		for (int i = from; i < to; i++) {
			char c = (char) i;
			String ch = Character.toString(c);
			out.add(ch);
		}

		return out;
	}

	public Glyph() {

		chars.addAll(ascii(48, 58));
		chars.addAll(ascii(65, 91));
		chars.addAll(ascii(97, 123));
	}

	public int idx(char c) {
		return chars.indexOf(c + "");
	}

}
