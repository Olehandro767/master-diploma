package ua.edu.ontu.service;

import java.util.Arrays;
import java.util.List;

public class ArgsHandler {

	private boolean argsIsExists = false;
	private final String UPDATE_ADMIN = "--update-admin";

	private String[] getArguments() {
		return new String[] { UPDATE_ADMIN, };
	}

	public ArgsHandler handleArgsIfExists(String[] args) {
		List<String> arguments = Arrays.asList(this.getArguments());

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];

			if (arguments.contains(arg)) {
				this.argsIsExists = true;

				if (arg.equals(this.UPDATE_ADMIN)) {
					String login = "";
					String password = "";
				}

				break;
			}
		}

		return this;
	}

	public boolean canContinue() {
		return this.argsIsExists;
	}
}