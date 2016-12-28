/*
 * Copyright 2016 inpwtepydjuf@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmarquee.automation.controls;

import mmarquee.automation.AutomationElement;
import mmarquee.automation.BaseAutomationTest;
import mmarquee.automation.pattern.Selection;
import mmarquee.automation.pattern.Toggle;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Mark Humphreys on 01/12/2016.
 */
public class AutomationListTest extends BaseAutomationTest {
    protected Logger logger = Logger.getLogger(AutomationListTest.class.getName());

    @Test
    public void testName_Gets_Value_From_Element() throws Exception {
        AutomationElement element = Mockito.mock(AutomationElement.class);
        Selection selection = Mockito.mock(Selection.class);

        when(element.getName()).thenReturn("NAME");

        AutomationList list = new AutomationList(element, selection);

        String name = list.name();

        assertTrue(name.equals("NAME"));
    }

    @Test
    public void testGetItems() throws Exception {
        loadApplication("apps\\Project1.exe", "Form1");

        try {
            AutomationList li1 = window.getListItem(0);

            List<AutomationListItem> items = li1.getItems();

            logger.info(items.size());

            assertTrue(items.size() == 5);
        } finally {
            closeApplication();
        }
    }
}
