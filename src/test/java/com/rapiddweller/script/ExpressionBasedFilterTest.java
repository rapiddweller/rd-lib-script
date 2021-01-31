/*
 * Copyright (C) 2011-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rapiddweller.script;

import com.rapiddweller.common.Context;
import com.rapiddweller.common.context.DefaultContext;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link ExpressionBasedFilter}.<br/><br/>
 * Created: 08.03.2011 14:15:33
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class ExpressionBasedFilterTest {

	@Test
	public void test() {
		Context context = new DefaultContext();
		Expression<Boolean> expression = new IsThreeExpression();
		ExpressionBasedFilter<Integer> filter = new ExpressionBasedFilter<>(expression, context);
		assertFalse(filter.accept(null));
		assertFalse(filter.accept(2));
		assertTrue(filter.accept(3));
		assertFalse(filter.accept(4));
	}
	
}
