/*
 * Copyright 2016-17 inpwtepydjuf@gmail.com
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
package mmarquee.automation.pattern;

import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;
import mmarquee.automation.AutomationException;
import mmarquee.automation.uiautomation.IUIAutomationElementArray;
import mmarquee.automation.uiautomation.IUIAutomationSelectionPattern;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * Created by Mark Humphreys on 12/01/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectionPatternTest {
    @Mock
    IUIAutomationSelectionPattern rawPattern;

    @Spy
    private Unknown mockUnknown;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected= AutomationException.class)
    public void test_getCurrentSelection_Throws_Exception_When_Pattern_Returns_Error() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {

                return 1;
            }
        }).when(rawPattern).getCurrentSelection(any());

        Selection pattern = new Selection(rawPattern);

        pattern.getCurrentSelection();
    }

    @Test(expected=AutomationException.class)
    @Ignore("Fails after mockito upgrade")
    public void test_That_getPattern_Throws_Exception_When_Pattern_Returns_Error() throws Exception {

        doAnswer(new Answer() {
            @Override
            public WinNT.HRESULT answer(InvocationOnMock invocation) throws Throwable {
                return new WinNT.HRESULT(-1);
            }
        }).when(mockUnknown).QueryInterface(any(Guid.REFIID.class), any(PointerByReference.class));

        Selection pattern = new Selection();

        Selection spyPattern = Mockito.spy(pattern);

        IUIAutomationSelectionPattern mockPattern = Mockito.mock(IUIAutomationSelectionPattern.class);

        doReturn(mockUnknown)
                .when(spyPattern)
                .makeUnknown(any());

        doReturn(mockPattern)
                .when(spyPattern)
                .convertPointerToInterface(any());

        spyPattern.getCurrentSelection();

        verify(spyPattern, atLeastOnce()).getCurrentSelection();
    }

    @Test
    @Ignore("Needs better tests")
    public void test_That_getPattern_Gets_Pattern_When_No_Pattern_Set() throws Exception {
        doAnswer(new Answer() {
            @Override
            public WinNT.HRESULT answer(InvocationOnMock invocation) throws Throwable {
                return new WinNT.HRESULT(0);
            }
        }).when(mockUnknown).QueryInterface(any(Guid.REFIID.class), any(PointerByReference.class));

        Selection pattern = new Selection();

        Selection spyPattern = Mockito.spy(pattern);

        IUIAutomationSelectionPattern mockPattern = Mockito.mock(IUIAutomationSelectionPattern.class);

        IUIAutomationElementArray mockArray = Mockito.mock(IUIAutomationElementArray.class);

        doReturn(mockUnknown)
                .when(spyPattern)
                .makeUnknown(any());

        doReturn(mockPattern)
                .when(spyPattern)
                .convertPointerToInterface(any());

        doReturn(mockArray)
                .when(spyPattern)
                .convertPointerToElementArray(any());

        spyPattern.getCurrentSelection();

        verify(spyPattern, atLeastOnce()).getCurrentSelection();
    }
}
