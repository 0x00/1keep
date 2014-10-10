package info.mindland.theoneandonly;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class SpaceInvader extends View {

	int w = 7;
	private String seed = "initial";

	public void setSeed(String seed) {
		if (seed == null || seed.length() == 0)
			seed = "lalala";

		this.seed = seed;
	}

	public SpaceInvader(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	Paint p = new Paint();

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		p.setStyle(Style.FILL);

		Random rnd = new Random(seed);
		int tile = canvas.getWidth() / w;

		int inv[] = new int[w * w];

		for (int x = 0; x < w; x++)
			for (int y = 0; y < w; y++) {

				p.setColor(Color.BLACK);
				int v = rnd.nextInt(2);
				if (x <= w / 2)
					inv[x + y * w] = v;

				if (x > w / 2)
					inv[x + y * w] = inv[(w - 1 - x) + y * w];

				v = inv[x + y * w];

				if (v == 1)
					p.setColor(Color.rgb(0, 200, 255));

				if (v == 2)
					p.setColor(Color.rgb(0, 255, 255));

				canvas.drawRect(x * tile, y * tile, x * tile + tile + 1, y
						* tile + tile + 1, p);

			}

	}
}
