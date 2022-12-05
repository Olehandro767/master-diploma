package ua.edu.ontu.service.student_assistant_tg_bot.util;

import java.io.File;

import org.telegram.telegrambots.meta.updateshandlers.DownloadFileCallback;

import lombok.RequiredArgsConstructor;

public class AsyncUtil {

	private AsyncUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static <T> DownloadFileCallback<T> async(IAsyncOnResultDownloadFileCallback<T> onResult,
			IAsyncOnExceptionDownloadFileCallback<T> onException) {
		return new Callback<>(onResult, onException);
	}

	@RequiredArgsConstructor
	public static class Callback<T> implements DownloadFileCallback<T> {

		private final IAsyncOnResultDownloadFileCallback<T> onResult;
		private final IAsyncOnExceptionDownloadFileCallback<T> onException;

		@Override
		public void onResult(T t, File output) {
			this.onResult.func(t, output);
		}

		@Override
		public void onException(T t, Exception exception) {
			this.onException.func(t, exception);
		}
	}

	public static interface IAsyncOnResultDownloadFileCallback<T> {
		void func(T t, File file);
	}

	public static interface IAsyncOnExceptionDownloadFileCallback<T> {
		void func(T t, Exception exception);
	}
}