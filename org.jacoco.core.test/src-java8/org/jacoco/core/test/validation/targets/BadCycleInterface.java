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
package org.jacoco.core.test.validation.targets;

public class BadCycleInterface {

	interface Base {
		static final Object BASE_CONST = new Child() {
			{
				Stubs.logEvent("baseclinit"); // $line-baseclinit$
			}
		}.childDefaultMethod();

		default Object baseDefaultMethod() {
			Stubs.logEvent("baseDefaultMethod"); // $line-basedefault$
			return null;
		}
	}

	interface Child extends Base {
		static final Object CHILD_CONST = new Base() {
			{
				Stubs.logEvent("childclinit"); // $line-childclinit$
			}
		}.baseDefaultMethod();

		default Object childDefaultMethod() {
			Stubs.logEvent("childDefaultMethod"); // $line-childdefault$
			return null;
		}
	}

	public static void main(String[] args) {
		new Child() {
		};
	}

}
