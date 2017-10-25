/*
 * Copyright 2015 Sharmarke Aden.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acme.greeter;

import com.fitbur.testify.Cut;
import com.fitbur.testify.Fake;
import com.fitbur.testify.junit.UnitTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(UnitTest.class)
public class GreeterTest {

    @Cut
    Greeter cut;

    @Fake
    Greeting greeting;

    @Test
    public void callToGreetShouldReturnHello() {
        //Arrange
        String phrase = "Hello";
        given(greeting.phrase()).willReturn(phrase);

        //Act
        String result = cut.greet();

        //Assert
        assertThat(result).isEqualTo(phrase);
        verify(greeting).phrase();
        verifyNoMoreInteractions(greeting);
    }

    @Test
    public void callToGreetShouldReturnHaye() {
        //Arrange
        String phrase = "Haye";
        given(greeting.phrase()).willReturn(phrase);

        //Act
        String result = cut.greet();

        //Assert
        assertThat(result).isEqualTo(phrase);
        verify(greeting).phrase();
        verifyNoMoreInteractions(greeting);
    }

}
