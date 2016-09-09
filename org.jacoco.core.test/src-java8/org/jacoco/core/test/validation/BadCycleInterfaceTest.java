/*******************************************************************************
 * Copyright (c) 2009, 2016 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Evgeny Mandrikov - initial API and implementation
 *
 *******************************************************************************/
package org.jacoco.core.test.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.test.validation.targets.BadCycleInterface;
import org.junit.Test;

/**
 * Test of "bad cycles" with interfaces.
 */
public class BadCycleInterfaceTest extends ValidationTestBase {

	public BadCycleInterfaceTest() throws Exception {
		super("src-java8", BadCycleInterface.class);
	}

	@Test
	public void test() throws Exception {
		// The cycle causes a default method to be called before the static
		// initializer of an interface:
		Matcher m = Pattern.compile("^1.8.0_([0-9]++)$")
				.matcher(System.getProperty("java.version"));
		if (m.matches() && Integer.parseInt(m.group(1)) < 40) {
			// JDK-8062114
			assertLogEvents("baseclinit", "childDefaultMethod", "childclinit",
					"baseDefaultMethod");
		} else {
			assertLogEvents("childclinit", "baseDefaultMethod", "baseclinit",
					"childDefaultMethod");
		}
		assertLine("childclinit", ICounter.FULLY_COVERED);
		assertLine("basedefault", ICounter.FULLY_COVERED);
		assertLine("baseclinit", ICounter.FULLY_COVERED);
		assertLine("childdefault", ICounter.FULLY_COVERED);
	}

}
