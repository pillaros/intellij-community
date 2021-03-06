/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.siyeh.ig.fixes.migration;

import com.siyeh.InspectionGadgetsBundle;
import com.siyeh.ig.IGQuickFixesTestCase;
import com.siyeh.ig.migration.TryFinallyCanBeTryWithResourcesInspection;

public class TryFinallyCanBeTryWithResourcesFixTest extends IGQuickFixesTestCase {

  public void testComments() { doTest(); }
  public void testMultiple() { doTest(); }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    myFixture.enableInspections(new TryFinallyCanBeTryWithResourcesInspection());
    myRelativePath = "migration/try_finally_can_be_try_with_resources";
    myDefaultHint = InspectionGadgetsBundle.message("try.finally.can.be.try.with.resources.quickfix");
    myFixture.addClass("package test;" +
                       "public class MyBufferedInputStream implements java.io.Closeable {" +
                       "  public BufferedInputStream(InputStream in) {}" +
                       "}");
  }
}
