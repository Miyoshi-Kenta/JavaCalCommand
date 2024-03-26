package main;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

public class JavaCalCommand {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in, "Shift-JIS");

		while (true) {
			String input = scanner.nextLine().trim();
			if (input.equals("exit")) {
				System.out.println("プログラムを終了します。");
				break;
			}
			if (input.isEmpty() || !input.startsWith("cal")) {
				continue;
			}

			try {
				int[] monthAndYear = validateInput(input);

				// 年のみ指定された場合
				if (monthAndYear.length == 1) {

					printYearCalendar(monthAndYear[0]);

				} else if (monthAndYear.length == 2) { // 月と年が指定された場合
					System.out.println();
					System.out.println("     " + monthAndYear[0] + "月 " + monthAndYear[1]);
					System.out.println(generateMonthCalendar(monthAndYear[1], monthAndYear[0]));
				} else { // 引数なしの場合
					LocalDate currentDate = LocalDate.now();
					System.out.println();
					System.out.println("       " + currentDate.getMonthValue() + "月 " + currentDate.getYear());
					System.out.println(generateMonthCalendar(currentDate.getYear(), currentDate.getMonthValue()));
				}
			} catch (NumberFormatException e) {
				System.out.println(input + ": コマンドが見つかりません");
				continue;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			} catch (RuntimeException e) {
				System.out.println(input + ": コマンドが見つかりません");
			}
		}
	}

	/**
	 * 入力値チェック用メソッド
	 * @param input: 入力された値
	 * @return int[]: calを除いた月と年の値を格納した配列
	 * */
	private static int[] validateInput(String input) throws IllegalArgumentException {

		// "cal"を削除し、先頭の空白をトリムする
		input = input.substring(3).trim();
		String[] parts = input.split("\\s+");

		// 引数なし（現在の月を表示）
		if (input.isEmpty()) {
			return new int[0];
		}

		// 引数が2つ以上(異常)
		if (parts.length > 2) {
			throw new IllegalArgumentException(input + ": コマンドが見つかりません");
		}

		int[] monthAndYear = new int[parts.length];
		for (int i = 0; i < parts.length; i++) {
			monthAndYear[i] = Integer.parseInt(parts[i]);
		}

		// 年のみが指定された場合
		if (parts.length == 1) {
			if (monthAndYear[0] < 1 || monthAndYear[0] > 9999) {
				throw new IllegalArgumentException("cal: 不正な年: 1-9999を使ってください");
			}
		}

		// 月と年が指定された場合
		if (parts.length == 2) {
			int month = monthAndYear[0];
			if (month < 1 || month > 12) {
				throw new IllegalArgumentException("cal: 不正な月: 1-12を使ってください");
			}
			if (monthAndYear[1] < 1 || monthAndYear[1] > 9999) {
				throw new IllegalArgumentException("cal: 不正な年: 1-9999を使ってください");
			}
		}
		return monthAndYear;
	}

	/**
	 * 年のみ指定された際の出力用メソッド
	 * @param year : 入力された年の値 
	 * */
	private static void printYearCalendar(int year) {
		System.out.println("       " + year);
		System.out.println();

		// 各月を縦に表示
		for (int month = 1; month <= 12; month++) {
			System.out.println("        " + month + "月");
			printMonthCalendar(year, month);
			System.out.println();
		}
	}

	/**
	 * 年と月を指定された際の出力用メソッド
	 * @param year : 入力された年の値
	 * @param month : 入力された月の値
	 * */
	private static void printMonthCalendar(int year, int month) {
		String calendar = generateMonthCalendar(year, month);
		System.out.print(calendar);
	}

	/**
	 * 表示したい年と月を指定し、１ヵ月分のカレンダー文字列を生成するメソッド
	 * @param year : 表示したい年の値
	 * @param month : 表示したい月の値
	 * @return 1ヶ月単位のカレンダー文字列
	 */
	private static String generateMonthCalendar(int year, int month) {
		YearMonth yearMonth = YearMonth.of(year, month);
		LocalDate firstDayOfMonth = yearMonth.atDay(1);
		int daysInMonth = yearMonth.lengthOfMonth();
		int dayOfWeekOfFirstDay = firstDayOfMonth.getDayOfWeek().getValue() % 7;

		StringBuilder calendar = new StringBuilder();
		calendar.append("日 月 火 水 木 金 土\n");
		for (int i = 0; i < dayOfWeekOfFirstDay; i++) {
			calendar.append("   ");
		}
		for (int day = 1; day <= daysInMonth; day++) {
			calendar.append(String.format("%2d ", day));
			if ((day + dayOfWeekOfFirstDay) % 7 == 0 || day == daysInMonth) {
				calendar.append("\n");
			}
		}

		// 最終行のパディングと月末の行数調整
		int lastLineLength = (daysInMonth + dayOfWeekOfFirstDay - 1) % 7;
		if (lastLineLength != 0) {
			for (int i = lastLineLength; i < 7; i++) {
				// 空の日付をパディング
				calendar.append("   ");
			}
			calendar.append("\n");
		}

		// 月末の空行を追加してすべての月の高さを揃える
		int totalLines = (daysInMonth + dayOfWeekOfFirstDay - 1) / 7 + 1;
		for (int i = totalLines; i < 6; i++) { // 最大6週間分の行数を確保
			// 20文字のスペースで埋める
			calendar.append("                    \n");
		}

		return calendar.toString();
	}
}
