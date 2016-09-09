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

import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.test.validation.targets.BadCycleInterfaceJDK8;
import org.junit.Test;

/**
 * Test of "bad cycles" with interfaces caused by incorrect interpretation of
 * JVMS in JDK 8.
 */
public class BadCycleInterfaceJDK8Test extends ValidationTestBase {

	public BadCycleInterfaceJDK8Test() throws Exception {
		super("src-java8", BadCycleInterfaceJDK8.class);
	}

	@Test
	public void test() throws Exception {
		if (!System.getProperty("java.version").startsWith("9-ea")) {
			// Incorrect interpetation of JVMS 5.5 in JDK 8 causes a default
			// method to be called before the static initializer of an interface
			// (see JDK-8098557 and JDK-9042842):
			assertLogEvents("baseclinit", "childdefaultmethod", "childclinit",
					"childstaticmethod");

			assertLine("baseclinit", ICounter.FULLY_COVERED);
			assertLine("childdefault", ICounter.FULLY_COVERED);
		} else {
			assertLogEvents("childclinit", "childstaticmethod");

			assertLine("baseclinit", ICounter.EMPTY);
			assertLine("childdefault", ICounter.NOT_COVERED);
		}
		assertLine("childclinit", ICounter.FULLY_COVERED);
		assertLine("childstatic", ICounter.FULLY_COVERED);
	}

}
