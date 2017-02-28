package com.tangshilong.run;

public class StringLineStream {
	public static void stream(String str, CallBack callBack) {
		if (str != null) {
			String[] split = str.split("\n");
			for (int i = 0; i < split.length; i++) {
				if (callBack.deal(split[i], i, split)) {
					break;
				};
			}
		}
	}

	public static interface CallBack {
		boolean deal(String line, int i, String[] split);
	}
}
