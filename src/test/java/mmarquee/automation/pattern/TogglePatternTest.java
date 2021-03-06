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
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import mmarquee.automation.AutomationException;
import mmarquee.automation.uiautomation.IUIAutomationTablePattern;
import mmarquee.automation.uiautomation.IUIAutomationTogglePattern;
import mmarquee.automation.uiautomation.ToggleState;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Mark Humphreys on 11/01/2017.
 *
 * Tests of the Toggle pattern
 */
@RunWith(MockitoJUnitRunner.class)
public class TogglePatternTest {
    @Mock
    IUIAutomationTogglePattern rawPattern;

    @Spy
    private Unknown mockUnknown;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToggle_Calls_Toggle_From_Pattern() throws Exception {
        Toggle pattern = new Toggle(rawPattern);

        pattern.toggle();

        verify(rawPattern, atLeastOnce()).toggle();
    }

    @Test
    public void test_getCurrentToggleState_Returns_On_When_COM_Returns_One() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {

                Object[] args = invocation.getArguments();
                IntByReference reference = (IntByReference)args[0];

                reference.setValue(1);

                return 0;
            }
        }).when(rawPattern).getCurrentToggleState(any());

        Toggle pattern = new Toggle(rawPattern);

        ToggleState state = pattern.currentToggleState();

        assertTrue(state == ToggleState.On);
    }

    @Test
    public void test_getCurrentToggleState_Returns_Off_When_COM_Returns_Zero() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {

                Object[] args = invocation.getArguments();
                IntByReference reference = (IntByReference)args[0];

                reference.setValue(0);

                return 0;
            }
        }).when(rawPattern).getCurrentToggleState(any());

        Toggle pattern = new Toggle(rawPattern);

        ToggleState state = pattern.currentToggleState();

        assertTrue(state == ToggleState.Off);
    }

    @Test
    public void test_getCurrentToggleState_Returns_Intermediate_When_COM_Returns_Two() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {

                Object[] args = invocation.getArguments();
                IntByReference reference = (IntByReference)args[0];

                reference.setValue(2);

                return 0;
            }
        }).when(rawPattern).getCurrentToggleState(any());

        Toggle pattern = new Toggle(rawPattern);

        ToggleState state = pattern.currentToggleState();

        assertTrue(state == ToggleState.Indeterminate);
    }

    @Test(expected=AutomationException.class)
    public void testIsExpanded_Throws_Exception_When_COM_Returns_Error() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {

                Object[] args = invocation.getArguments();
                IntByReference reference = (IntByReference)args[0];

                reference.setValue(0);

                return 1;
            }
        }).when(rawPattern).getCurrentToggleState(any());

        Toggle pattern = new Toggle(rawPattern);

        ToggleState state = pattern.currentToggleState();

        assertTrue(state == ToggleState.Indeterminate);
    }

    @Test(expected=AutomationException.class)
    public void test_toggle_Throws_Exception_When_COM_Returns_Error() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {

                Object[] args = invocation.getArguments();
                IntByReference reference = (IntByReference)args[0];

                reference.setValue(2);

                return 1;
            }
        }).when(rawPattern).getCurrentToggleState(any());

        Toggle pattern = new Toggle(rawPattern);

        ToggleState state = pattern.currentToggleState();

        assertTrue(state == ToggleState.Indeterminate);
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

        Toggle pattern = new Toggle();

        Toggle spyPattern = Mockito.spy(new Toggle());

        IUIAutomationTogglePattern mockRange = Mockito.mock(IUIAutomationTogglePattern.class);

        doReturn(mockUnknown)
                .when(spyPattern)
                .makeUnknown(any());

        doReturn(mockRange)
                .when(spyPattern)
                .convertPointerToInterface(any());

        spyPattern.toggle();

        verify(spyPattern, atLeastOnce()).toggle();
    }

    @Test
    public void test_That_getPattern_Gets_Pattern_When_No_Pattern_Set() throws Exception {

        doAnswer(new Answer() {
            @Override
            public WinNT.HRESULT answer(InvocationOnMock invocation) throws Throwable {
                return new WinNT.HRESULT(1);
            }
        }).when(mockUnknown).QueryInterface(any(Guid.REFIID.class), any(PointerByReference.class));

        Toggle pattern = new Toggle();

        Toggle spyPattern = Mockito.spy(new Toggle());

        IUIAutomationTogglePattern mockRange = Mockito.mock(IUIAutomationTogglePattern.class);

        doReturn(mockUnknown)
                .when(spyPattern)
                .makeUnknown(any());

        doReturn(mockRange)
                .when(spyPattern)
                .convertPointerToInterface(any());

        spyPattern.toggle();

        verify(spyPattern, atLeastOnce()).toggle();
    }
}
