package jp.ind.knhnnh.adbpushex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import android.content.Context;
import android.util.Log;

public class Utils {
	public static final String PREF_NAME = "config";

	public static class Result {
		public String message;
	}

	public static Result rename(Context context, String listFilePath) {
		Result r = new Result();
		// ファイル名変換リストの取得
		File listFile = new File(listFilePath);
		File dir = listFile.getParentFile();
		if (!dir.exists() || !dir.isDirectory()) {
			// 親ディレクトリがない
			r.message = context.getString(R.string.message_notexist_listdir, dir.getAbsolutePath());
			return r;
		}
		if (!listFile.exists() || !listFile.isFile()) {
			// ファイルがない
			r.message = context.getString(R.string.message_notexist_listfile, listFile.getAbsoluteFile());
			return r;
		}
		try {
			// 文字コードの取得
			FileInputStream fis = new FileInputStream(listFile);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);
			String encode = reader.readLine().trim();
			Log.d("debug", encode);
			reader.close();
			isr.close();
			fis.close();
			// 文字コードを反映して読み込み
			fis = new FileInputStream(listFile);
			isr = new InputStreamReader(fis, encode);
			reader = new BufferedReader(isr);
			reader.readLine();
			StringBuffer sb = new StringBuffer();
			File destDir = null;
			String line;
			while ((line = reader.readLine()) != null) {
				Log.d("debug", line);
				//sb.append(line).append('\n');
				if (line.startsWith("dest ")) {
					// コピー先ディレクトリ
					String dest = line.substring(5).trim();
					destDir = new File(dest);
					if (!destDir.exists() || !destDir.isDirectory()) {
						sb.append(context.getString(R.string.message_notexist_destdir, destDir.getAbsolutePath()));
						break;
					}
				} else if (line.startsWith("file")) {
					// ファイル
					int x = line.indexOf(" ");
					String src = line.substring(0, x);
					String dest = line.substring(x + 1).trim();
					File srcFile = new File(dir, src);
					File destFile = new File(destDir, dest);
					if (!srcFile.exists() || !srcFile.isFile()) {
						// 一時ファイルがない
						sb.append(context.getString(R.string.message_notexist_srcFile,
											srcFile.getAbsolutePath(), destFile.getAbsolutePath())).append('\n');
						continue;
					}
					if (destFile.exists()) {
						// コピー先ファイルがあるので削除
						Log.d("AdbPushEx", "delete " + destFile.getAbsolutePath());
						if (!destFile.delete()) {
							sb.append(context.getString(R.string.message_delete_err_destFile,
																	destFile.getAbsolutePath())).append('\n');
							continue;
						}
					}
					if (!srcFile.renameTo(destFile)) {
						// 移動
						sb.append(context.getString(R.string.message_move_err,
											srcFile.getAbsolutePath(), destFile.getAbsolutePath())).append('\n');
						continue;
					}
				}
			}
			r.message = sb.toString();
			reader.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			r.message = e.getMessage();
			return r;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			r.message = e.getMessage();
			return r;
		} catch (IOException e) {
			e.printStackTrace();
			r.message = e.getMessage();
			return r;
		}
		return r;
	}
}
